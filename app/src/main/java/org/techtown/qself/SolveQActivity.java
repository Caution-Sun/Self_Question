package org.techtown.qself;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SolveQActivity extends AppCompatActivity {
    TextView title;
    TextView question;
    TextView tag;
    String answer;
    int id;

    int start, end;

    String tagNow;

    Cursor cursor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_q);

        title = findViewById(R.id.textTitle);
        question = findViewById(R.id.textQuestion);
        tag = findViewById(R.id.textTag);

        Button buttonAnswer = findViewById(R.id.buttonShowAnswer);
        Button buttonBefore = findViewById(R.id.buttonBefore);
        Button buttonAfter = findViewById(R.id.buttonNext);

        Intent intent = getIntent();

        if(intent != null){
            Bundle bundle = intent.getExtras();
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


        buttonAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer();
            }
        });

        // 뒤로 버튼이 눌리면 커서를 하나 뒤로 이동시킨 후 문제 설정
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

        // 앞으로 버튼이 눌리면 커서를 하나 다음으로 이동시킨 후 문제 설정
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(cursor != null){
            cursor.close();
            cursor = null;
        }

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

    private void setQuestion(){
        id = cursor.getInt(0);
        title.setText(cursor.getString(1));
        question.setText(cursor.getString(2));
        answer = cursor.getString(3);
        tag.setText(cursor.getString(4));
    }

    private void showAnswer(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("정답");
        builder.setMessage(answer);

        builder.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}