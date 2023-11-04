package org.techtown.qself;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ListQActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_EDIT = 301;

    int num;
    TextView title;
    TextView question;
    TextView answer;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_EDIT){
            if(resultCode == RESULT_OK){

                Question dataFromEdit = data.getParcelableExtra("dataFromEdit");

                num = dataFromEdit.getNum();
                title.setText(dataFromEdit.getTitle());
                question.setText(dataFromEdit.getQuestion());
                answer.setText(dataFromEdit.getAnswer());


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

        Button buttonDelete = findViewById(R.id.buttonDelete);
        Button buttonEdit = findViewById(R.id.buttonEdit);

        Intent intent = getIntent();

        if(intent != null){
            Bundle bundle = intent.getExtras();
            Question data = bundle.getParcelable("data");

            num = data.getNum();
            title.setText(data.getTitle());
            question.setText(data.getQuestion());
            answer.setText(data.getAnswer());
        }

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteMessage();
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Question item = new Question(num, title.getText().toString(), question.getText().toString(), answer.getText().toString());

                Intent intent = new Intent(getApplicationContext(),EditActivity.class);
                intent.putExtra("data", item);
                startActivityForResult(intent, REQUEST_CODE_EDIT);

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
}