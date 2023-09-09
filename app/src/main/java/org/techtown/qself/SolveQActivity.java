package org.techtown.qself;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SolveQActivity extends AppCompatActivity {
    TextView title;
    TextView question;
    String answer;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_q);

        title = findViewById(R.id.textTitle);
        question = findViewById(R.id.textQuestion);
        Button buttonAnswer = findViewById(R.id.buttonShowAnswer);
        Button buttonBefore = findViewById(R.id.buttonBefore);
        Button buttonAfter = findViewById(R.id.buttonNext);

        Intent intent = getIntent();

        if(intent != null){
            Bundle bundle = intent.getExtras();
            Question data = bundle.getParcelable("data");

            title.setText(data.getTitle());
            question.setText(data.getQuestion());
            answer = data.getAnswer();
            num = data.getNum();
        }

        buttonAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AnswerDialog.class);
                intent.putExtra("answer", answer);
                startActivity(intent);
            }
        });
    }
}