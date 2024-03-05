package org.techtown.qself;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    EditText editTextTitle;
    EditText editTextQuestion;
    EditText editTextAnswer;
    EditText editTextTag;
    int num;

    private long backpressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextQuestion = findViewById(R.id.editTextQuestion);
        editTextAnswer = findViewById(R.id.editTextAnswer);
        editTextTag = findViewById(R.id.editTextTag);

        Button button = findViewById(R.id.buttonEditQuestion);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editQuestion();

                Question item = new Question(num, editTextTitle.getText().toString(), editTextQuestion.getText().toString(), editTextAnswer.getText().toString(), editTextTag.getText().toString());

                Intent intent = new Intent();
                intent.putExtra("dataFromEdit",item);
                setResult(RESULT_OK, intent);
                finish();

            }
        });


        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            Question data = bundle.getParcelable("data");

            num = data.getNum();
            editTextTitle.setText(data.getTitle());
            editTextQuestion.setText(data.getQuestion());
            editTextAnswer.setText(data.getAnswer());
            editTextTag.setText(data.getTag());
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backpressedTime + 2000) {
            backpressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() <= backpressedTime + 2000) {
            finish();
        }
    }

    private void editQuestion(){
        String title = editTextTitle.getText().toString();
        String question = editTextQuestion.getText().toString();
        String answer = editTextAnswer.getText().toString();
        String tag = editTextTag.getText().toString();

        if(tag.equals("") || tag.equals("태그 없음") || tag.equals("태그없음")){
            tag = "태그없음";
        }

        String sql = "UPDATE " + QuestionDatabase.TABLE_QUESTION + " SET " +
                "TITLE = '" + title + "', " +
                "QUESTION = '" + question + "', " +
                "ANSWER = '" + answer + "', " +
                "TAG = '" + tag + "' " +
                "WHERE _id = " + num;

        QuestionDatabase database = QuestionDatabase.getInstance(this);
        database.execSQL(sql);
    }
}