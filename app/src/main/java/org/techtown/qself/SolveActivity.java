package org.techtown.qself;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SolveActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_SOLVE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);

        RecyclerView recyclerView = findViewById(R.id.RecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        QuestionAdapter adapter = new QuestionAdapter();

        adapter.addItem(new Question(1, "제목1제목1제목1", "내용1내용1내용1", "정답1정답1정답1"));
        adapter.addItem(new Question(1, "제목2제목2제목2", "내용2내용2내용2", "정답2정답2정답2"));

        adapter.setOnItemClickListener(new OnQuestionClickListener() {
            @Override
            public void onItemClick(QuestionAdapter.ViewHolder holder, View view, int position) {
                Question item = adapter.getItem(position);

                Intent intent = new Intent(getApplicationContext(), SolveQActivity.class);
                intent.putExtra("data", item);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
    }
}