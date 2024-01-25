package org.techtown.qself;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class SolveActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_SOLVE = 102;

    RecyclerView recyclerView;
    QuestionAdapter adapter;
    Spinner spinner;
    String tag;

    int start, end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);

        spinner = findViewById(R.id.spinner);
        ArrayList<String> items = new ArrayList<>();
        items.add("ALL");

        String sql = "select DISTINCT TAG FROM " + QuestionDatabase.TABLE_QUESTION;
        QuestionDatabase database = QuestionDatabase.getInstance(this);
        if(database != null){
            Cursor cursor = database.rawQuery(sql);

            int recordCount = cursor.getCount();
            for(int i = 0; i < recordCount; i++){
                cursor.moveToNext();

                String tag = cursor.getString(0);
                items.add(tag);
            }

            cursor.close();
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tag = items.get(position);

                if(tag.equals("ALL"))
                    loadQuestionListData();
                else
                    loadQuestionListDataByTag(tag);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        recyclerView = findViewById(R.id.RecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new QuestionAdapter();

        adapter.setOnItemClickListener(new OnQuestionClickListener() {
            @Override
            public void onItemClick(QuestionAdapter.ViewHolder holder, View view, int position) {
                Question item = adapter.getItem(position);

                Intent intent = new Intent(getApplicationContext(), SolveQActivity.class);

                intent.putExtra("id", item.getNum());
                intent.putExtra("tag", tag);

                intent.putExtra("start", start);
                intent.putExtra("end", end);

                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    public void loadQuestionListData() {
        adapter.deleteAllItem();
        adapter.notifyDataSetChanged();

        int recordCount = 0;

        String sql = "select _id, TITLE, QUESTION, ANSWER, TAG FROM " + QuestionDatabase.TABLE_QUESTION + " order by _id ASC";

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
                String Tag = cursor.getString(4);

                adapter.addItem(new Question(_id, Title, Question, Answer, Tag));
            }

            cursor.close();
            adapter.notifyDataSetChanged();
        }

    }

    public void loadQuestionListDataByTag(String tag) {
        adapter.deleteAllItem();
        adapter.notifyDataSetChanged();

        int recordCount = 0;

        String sql = "select _id, TITLE, QUESTION, ANSWER, TAG FROM " + QuestionDatabase.TABLE_QUESTION + " where TAG = '" + tag + "' order by _id ASC";

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
                String Tag = cursor.getString(4);

                adapter.addItem(new Question(_id, Title, Question, Answer, Tag));
            }

            cursor.close();
            adapter.notifyDataSetChanged();
        }

    }
}