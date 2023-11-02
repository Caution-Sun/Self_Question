package org.techtown.qself;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static final int REQUEST_CODE_MAKE = 101;

    // 데이터베이스 인스턴스
    public static QuestionDatabase mDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 데이터베이스 열기
        openDatabase();

        Button makeButton = findViewById(R.id.makeButton);
        Button listButton = findViewById(R.id.listButton);
        Button solveButton = findViewById(R.id.solveButton);

        makeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MakeActivity.class);
                startActivityForResult(intent, REQUEST_CODE_MAKE);
            }
        });

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });

        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SolveActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_MAKE){
            if(resultCode == RESULT_OK){
                Toast.makeText(getApplicationContext(),"문제가 만들어졌습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    // 종료 시 데이터베이스 닫기.
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mDatabase != null){
            mDatabase.close();
            mDatabase = null;
        }
    }

    // 데이터베이스를 여는 함수
    public void openDatabase() {
        if(mDatabase != null){
            mDatabase.close();
            mDatabase = null;
        }

        mDatabase = QuestionDatabase.getInstance(this);
        boolean isOpen = mDatabase.open();
        if(isOpen){
            Log.d(TAG, "Question database is open.");
        }else{
            Log.d(TAG, "Question database is not open.");
        }
    }
}