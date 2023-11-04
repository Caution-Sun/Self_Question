package org.techtown.qself;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    EditText editTextTitle;
    EditText editTextQuestion;
    EditText editTextAnswer;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextQuestion = findViewById(R.id.editTextQuestion);
        editTextAnswer = findViewById(R.id.editTextAnswer);

        Button button = findViewById(R.id.buttonEditQuestion);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editQuestion();

                Question item = new Question(num, editTextTitle.getText().toString(), editTextQuestion.getText().toString(), editTextAnswer.getText().toString());

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
        }
    }

    private void editQuestion(){
        String title = editTextTitle.getText().toString();
        String question = editTextQuestion.getText().toString();
        String answer = editTextAnswer.getText().toString();

        String sql = "UPDATE " + QuestionDatabase.TABLE_QUESTION + " SET " +
                "TITLE = '" + title + "', " +
                "QUESTION = '" + question + "', " +
                "ANSWER = '" + answer + "' " +
                "WHERE _id = " + num;

        QuestionDatabase database = QuestionDatabase.getInstance(this);
        database.execSQL(sql);
    }
}