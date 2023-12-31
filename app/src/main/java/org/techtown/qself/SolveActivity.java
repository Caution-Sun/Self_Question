package org.techtown.qself;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SolveActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_SOLVE = 102;

    RecyclerView recyclerView;
    QuestionAdapter adapter;

    int start, end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);

        recyclerView = findViewById(R.id.RecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new QuestionAdapter();

        // 리사이클러뷰에 문제 추가
        loadQuestionListData();

        adapter.setOnItemClickListener(new OnQuestionClickListener() {
            @Override
            public void onItemClick(QuestionAdapter.ViewHolder holder, View view, int position) {
                Question item = adapter.getItem(position);

                Intent intent = new Intent(getApplicationContext(), SolveQActivity.class);

                intent.putExtra("id", item.getNum());

                intent.putExtra("start", start);
                intent.putExtra("end", end);

                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    public void loadQuestionListData() {
        int recordCount = 0;

        String sql = "select _id, TITLE, QUESTION, ANSWER FROM " + QuestionDatabase.TABLE_QUESTION + " order by _id ASC";

        QuestionDatabase database = QuestionDatabase.getInstance(this);
        if(database != null){
            Cursor cursor = database.rawQuery(sql);

            recordCount = cursor.getCount();

            for(int i = 0; i < recordCount; i++){
                cursor.moveToNext();

                int _id = cursor.getInt(0);
                if(i == 0)
                    start = _id;
                if(i == recordCount -1)
                    end = _id;

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