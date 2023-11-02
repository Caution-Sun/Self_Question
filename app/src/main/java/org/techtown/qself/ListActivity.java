package org.techtown.qself;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    QuestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.RecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new QuestionAdapter();

        // 리사이클러뷰에 문제 추가
        loadQuestionListData();

        /*
        adapter.addItem(new Question(1, "제목1제목1제목1", "내용1내용1내용1", "정답1정답1정답1"));
        adapter.addItem(new Question(1, "제목2제목2제목2", "내용2내용2내용2", "정답2정답2정답2"));
         */

        adapter.setOnItemClickListener(new OnQuestionClickListener() {
            @Override
            public void onItemClick(QuestionAdapter.ViewHolder holder, View view, int position) {
                Question item = adapter.getItem(position);

                Intent intent = new Intent(getApplicationContext(), ListQActivity.class);
                intent.putExtra("data", item);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    // 리사이클러뷰에 문제 추가 메소드
    public void loadQuestionListData() {
        int recordCount = 0;

        String sql = "select _id, TITLE, QUESTION, ANSWER FROM " + QuestionDatabase.TABLE_QUESTION + " order by _id ASC";

        QuestionDatabase database =QuestionDatabase.getInstance(this);
        if(database != null){
            Cursor cursor = database.rawQuery(sql);

            recordCount = cursor.getCount();

            for(int i = 0; i < recordCount; i++){
                cursor.moveToNext();

                int _id = cursor.getInt(0);
                String Title = cursor.getString(1);
                String Question = cursor.getString(2);
                String Answer = cursor.getString(3);

                adapter.addItem(new Question(_id, Title, Question, Answer));
            }

            cursor.close();
            adapter.notifyDataSetChanged();
        }

    }
}