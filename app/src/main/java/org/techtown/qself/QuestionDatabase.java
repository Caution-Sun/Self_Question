package org.techtown.qself;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class QuestionDatabase {
    private static final String TAG = "QuestionDatabase";

    // 싱글톤 인스턴스
    private static QuestionDatabase database;

    // 테이블 이름
    public static String TABLE_QUESTION = "QUESTION";

    // 데이터베이스 이름
    public static String DATABSE_NAME = "question.db";

    // 데이터베이스 버전
    public static int DATABASE_VERSION = 1;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;

    private QuestionDatabase(Context context){
        this.context = context;
    }

    // 싱글톤 인스턴스
    public static QuestionDatabase getInstance(Context context){
        if(database == null)
            database = new QuestionDatabase(context);

        return database;
    }

    // 데이터베이스 열기
    public boolean open(){
        println("Database Open.");

        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        return true;
    }

    // 데이터베이스 닫기
    public void close(){
        println("Database Close.");

        db.close();
        database = null;
    }

    // 쿼리문 실행
    public Cursor rawQuery(String SQL){
        Cursor cursor = null;
        try{
            cursor = db.rawQuery(SQL, null);
            println("Execute Query.");
        }catch (Exception ex){
            Log.e(TAG, "Exception in executeQuery", ex);
        }

        return cursor;
    }

    // SQL문 실행
    public boolean execSQL(String SQL){
        try{
            Log.d(TAG,"SQL : " + SQL);
            db.execSQL(SQL);
            println("Execute SQL");
        }catch (Exception ex){
            Log.e(TAG,"Exception in executeQuery", ex);
            return false;
        }

        return true;
    }


    // 데이터베이스 헬퍼 클래스
    private class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context){
            super(context, DATABSE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            // 이미 존재하는 테이블 DROP
            String DROP_SQL = "drop table [" + TABLE_QUESTION + "]";
            try{
                db.execSQL(DROP_SQL);
                println("Drop Table.");
            }catch (Exception ex){
                Log.e(TAG, "Exception in DROP_SQL", ex);
            }

            // 테이블 생성
            String CREAT_SQL = "create table " + TABLE_QUESTION + "("
                    +" _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    +" TITLE TEXT DEFAULT '', "
                    +" QUESTION TEXT DEFAULT '', "
                    +" ANSWER TEXT DEFAULT '' "
                    +")";
            try{
                db.execSQL(CREAT_SQL);
                println("Create Table.");
            }catch (Exception ex){
                Log.e(TAG,"Exception in CREATE_SQL", ex);
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            println("Upgrading Database from version : " + oldVersion + "to " + newVersion + ".");
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            println("opened Database : " + DATABSE_NAME + ".");
        }
    }

    private void println(String msg){
        Log.d(TAG, msg);
    }
}
