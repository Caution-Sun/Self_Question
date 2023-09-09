package org.techtown.qself;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AnswerDialog extends AppCompatActivity {
    TextView textAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_dialog);

        textAnswer = findViewById(R.id.textAnswer);
        Button buttonBack = findViewById(R.id.buttonBack);

        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            String answer = bundle.getString("answer");
            textAnswer.setText(answer);
        }

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                finish();
            }
        });
    }
}