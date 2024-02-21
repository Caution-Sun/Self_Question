package org.techtown.qself;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ListQActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_EDIT = 301;

    int id;
    TextView title;
    TextView question;
    TextView answer;
    TextView tag;

    int start, end;

    String tagNow;

    Cursor cursor = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_EDIT){
            if(resultCode == RESULT_OK){

                Question dataFromEdit = data.getParcelableExtra("dataFromEdit");

                id = dataFromEdit.getNum();
                title.setText(dataFromEdit.getTitle());
                question.setText(dataFromEdit.getQuestion());
                answer.setText(dataFromEdit.getAnswer());
                tag.setText(dataFromEdit.getTag());

                Toast.makeText(getApplicationContext(),"문제가 수정되었습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_q);

        title = findViewById(R.id.textTitle);
        question = findViewById(R.id.textQuestion);
        answer = findViewById(R.id.textAnswer);
        tag = findViewById(R.id.textTag);

        answer.setMovementMethod(new ScrollingMovementMethod());

        Button buttonDelete = findViewById(R.id.buttonDelete);
        Button buttonEdit = findViewById(R.id.buttonEdit);
        Button buttonBefore = findViewById(R.id.buttonBeforeList);
        Button buttonAfter = findViewById(R.id.buttonAfterList);

        Intent intent = getIntent();

        if(intent != null){
            Bundle bundle = intent.getExtras();
            /*
            Question data = bundle.getParcelable("data");

            id = data.getNum();
            title.setText(data.getTitle());
            question.setText(data.getQuestion());
            answer.setText(data.getAnswer());
            tag.setText(data.getTag());

             */

            id = bundle.getInt("id");

            start = bundle.getInt("start");
            end = bundle.getInt("end");

            tagNow = bundle.getString("tag");
        }

        // 데이터베이스와 커서를 설정하고, 처음 선택된 문제로 커서를 이동한 후 문제 설정
        initQuestion();

        if(id == start)
            buttonBefore.setEnabled(false);
        if(id == end)
            buttonAfter.setEnabled(false);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteMessage();
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Question item = new Question(id, title.getText().toString(), question.getText().toString(), answer.getText().toString(), tag.getText().toString());

                Intent intent = new Intent(getApplicationContext(),EditActivity.class);
                intent.putExtra("data", item);
                startActivityForResult(intent, REQUEST_CODE_EDIT);

            }
        });

        buttonBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor.moveToPrevious();
                setQuestion();

                if(id == start){
                    buttonBefore.setEnabled(false);
                }else{
                    buttonBefore.setEnabled(true);
                }

                if(id == end){
                    buttonAfter.setEnabled(false);
                }else{
                    buttonAfter.setEnabled(true);
                }
            }
        });

        buttonAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor.moveToNext();
                setQuestion();

                if(id == start){
                    buttonBefore.setEnabled(false);
                }else{
                    buttonBefore.setEnabled(true);
                }

                if(id == end){
                    buttonAfter.setEnabled(false);
                }else{
                    buttonAfter.setEnabled(true);
                }
            }
        });
    }

    private void showDeleteMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("문제 삭제");
        builder.setMessage("정말로 삭제하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 삭제 버튼이 눌렸을 때 이벤트
                deleteQuestion();
                finish();
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 아니오 버튼이 눌렸을 때 이벤트
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteQuestion(){
        QuestionDatabase database = QuestionDatabase.getInstance(this);

        String sql = "DELETE FROM " + QuestionDatabase.TABLE_QUESTION + " WHERE _id = " + id;
        database.execSQL(sql);

        /*
        sql = "ALTER TABLE " + QuestionDatabase.TABLE_QUESTION + " AUTO_INCREMENT = " + num;
        database.execSQL(sql);
         */
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(cursor != null){
            cursor.close();
            cursor = null;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_list_q);

        title = findViewById(R.id.textTitle);
        question = findViewById(R.id.textQuestion);
        answer = findViewById(R.id.textAnswer);
        tag = findViewById(R.id.textTag);

        answer.setMovementMethod(new ScrollingMovementMethod());

        Button buttonDelete = findViewById(R.id.buttonDelete);
        Button buttonEdit = findViewById(R.id.buttonEdit);
        Button buttonBefore = findViewById(R.id.buttonBeforeList);
        Button buttonAfter = findViewById(R.id.buttonAfterList);

        Intent intent = getIntent();

        if(intent != null){
            Bundle bundle = intent.getExtras();
            /*
            Question data = bundle.getParcelable("data");

            id = data.getNum();
            title.setText(data.getTitle());
            question.setText(data.getQuestion());
            answer.setText(data.getAnswer());
            tag.setText(data.getTag());

             */

            id = bundle.getInt("id");

            start = bundle.getInt("start");
            end = bundle.getInt("end");

            tagNow = bundle.getString("tag");
        }

        // 데이터베이스와 커서를 설정하고, 처음 선택된 문제로 커서를 이동한 후 문제 설정
        initQuestion();

        if(id == start)
            buttonBefore.setEnabled(false);
        if(id == end)
            buttonAfter.setEnabled(false);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteMessage();
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Question item = new Question(id, title.getText().toString(), question.getText().toString(), answer.getText().toString(), tag.getText().toString());

                Intent intent = new Intent(getApplicationContext(),EditActivity.class);
                intent.putExtra("data", item);
                startActivityForResult(intent, REQUEST_CODE_EDIT);

            }
        });

        buttonBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor.moveToPrevious();
                setQuestion();

                if(id == start){
                    buttonBefore.setEnabled(false);
                }else{
                    buttonBefore.setEnabled(true);
                }

                if(id == end){
                    buttonAfter.setEnabled(false);
                }else{
                    buttonAfter.setEnabled(true);
                }
            }
        });

        buttonAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor.moveToNext();
                setQuestion();

                if(id == start){
                    buttonBefore.setEnabled(false);
                }else{
                    buttonBefore.setEnabled(true);
                }

                if(id == end){
                    buttonAfter.setEnabled(false);
                }else{
                    buttonAfter.setEnabled(true);
                }
            }
        });
    }

    private void setQuestion(){
        id = cursor.getInt(0);
        title.setText(cursor.getString(1));
        question.setText(cursor.getString(2));
        answer.setText(cursor.getString(3));
        tag.setText(cursor.getString(4));
    }

    private void initQuestion() {

        QuestionDatabase database = null;

        if(cursor != null){
            cursor.close();
            cursor = null;
        }

        database = QuestionDatabase.getInstance(this);

        String sql;

        if(tagNow.equals("ALL"))
            sql = "select _id, TITLE, QUESTION, ANSWER, TAG FROM " + QuestionDatabase.TABLE_QUESTION + " order by _id ASC";
        else
            sql = "select _id, TITLE, QUESTION, ANSWER, TAG FROM " + QuestionDatabase.TABLE_QUESTION + " where TAG = '" + tagNow + "' order by _id ASC";

        cursor = database.rawQuery(sql);

        int recordCount = cursor.getCount();

        for(int i = 0; i < recordCount; i++){
            cursor.moveToNext();

            int _id = cursor.getInt(0);

            if(_id == id){
                setQuestion();
                break;
            }
        }
    }
}