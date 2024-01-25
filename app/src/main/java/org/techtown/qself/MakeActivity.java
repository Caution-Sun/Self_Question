package org.techtown.qself;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MakeActivity extends AppCompatActivity {

    EditText editTextTitle;
    EditText editTextQuestion;
    EditText editTextAnswer;
    EditText editTextTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextQuestion = findViewById(R.id.editTextQuestion);
        editTextAnswer = findViewById(R.id.editTextAnswer);
        editTextTag = findViewById(R.id.editTextTag);

        Button buttonMakeQuestion = findViewById(R.id.buttonEditQuestion);

        buttonMakeQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 데이터베이스에 문제 추가
                addQuestion();

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    // 데이터베이스에 문제 추가 메소드
    private void addQuestion() {
        String title = editTextTitle.getText().toString();
        String question = editTextQuestion.getText().toString();
        String answer = editTextAnswer.getText().toString();
        String tag = editTextTag.getText().toString();

        if(tag.equals("") || tag.equals("태그 없음") || tag.equals("태그없음")){
            tag = "태그없음";
        }

        String sql = "insert into " + QuestionDatabase.TABLE_QUESTION +
                "(TITLE, QUESTION, ANSWER, TAG) values(" +
                "'" + title + "', " +
                "'" + question + "', " +
                "'" + answer + "', " +
                "'" + tag + "')";

        QuestionDatabase database = QuestionDatabase.getInstance(this);
        database.execSQL(sql);
    }
}