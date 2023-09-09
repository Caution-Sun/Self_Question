package org.techtown.qself;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);

        EditText editTextTitle = findViewById(R.id.editTextTitle);
        EditText editTextQuestion = findViewById(R.id.editTextQuestion);
        EditText editTextAnswer = findViewById(R.id.editTextAnswer);

        Button buttonMakeQuestion = findViewById(R.id.buttonMakeQuestion);

        buttonMakeQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String question = editTextQuestion.getText().toString();
                String answer = editTextAnswer.getText().toString();

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}