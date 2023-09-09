package org.techtown.qself;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ListQActivity extends AppCompatActivity {
    TextView title;
    TextView question;
    TextView answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_q);

        title = findViewById(R.id.textTitle);
        question = findViewById(R.id.textQuestion);
        answer = findViewById(R.id.textAnswer);

        Intent intent = getIntent();

        if(intent != null){
            Bundle bundle = intent.getExtras();
            Question data = bundle.getParcelable("data");

            title.setText(data.getTitle());
            question.setText(data.getQuestion());
            answer.setText(data.getAnswer());
        }
    }
}