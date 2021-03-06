package com.example.android.newskart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private String TAG = "DatabaseHandler";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "newsManager.db";


    private static final String TABLE_GLOBAL_NEWS = "news";
    private static final String TABLE_SPORTS_NEWS = "sports";
    private static final String TABLE_BUSNINESS_NEWS = "business";
    private static final String TABLE_HEALTH_NEWS = "health";
    private static final String TABLE_FUN_NEWS = "fun";
    private static final String TABLE_GAMING_NEWS = "gaming";
    private static final String TABLE_TECH_NEWS = "tech";


    private static final String NEWS_TITLE = "title";
    private static final String NEWS_DISCRIPTION = "description";
    private static final String NEWS_DATE = "date";
    private static final String NEWS_BROWSER_URL = "url";
    private static final String NEWS_CONTENT = "content";
    private static final String NEWS_IMAGE_URL = "imageurl";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GLOBAL_NEWS_TABLE = "CREATE TABLE " + TABLE_GLOBAL_NEWS + " (" + NEWS_TITLE + " TEXT," + NEWS_DISCRIPTION + " TEXT," +
                NEWS_DATE + " INTEGER," + NEWS_CONTENT + " TEXT,"+ NEWS_BROWSER_URL + " TEXT," + NEWS_IMAGE_URL + " TEXT" + ")";
        db.execSQL(CREATE_GLOBAL_NEWS_TABLE);

        String CREATE_SPORTS_NEWS_TABLE = "CREATE TABLE " + TABLE_SPORTS_NEWS + " (" + NEWS_TITLE + " TEXT," + NEWS_DISCRIPTION + " TEXT," +
                NEWS_DATE + " INTEGER," + NEWS_CONTENT + " TEXT,"+ NEWS_BROWSER_URL + " TEXT," + NEWS_IMAGE_URL + " TEXT" + ")";
        db.execSQL(CREATE_SPORTS_NEWS_TABLE);

        String CREATE_BUSINESS_NEWS_TABLE = "CREATE TABLE " + TABLE_BUSNINESS_NEWS + " (" + NEWS_TITLE + " TEXT," + NEWS_DISCRIPTION + " TEXT," +
                NEWS_DATE + " INTEGER," + NEWS_CONTENT + " TEXT,"+ NEWS_BROWSER_URL + " TEXT," + NEWS_IMAGE_URL + " TEXT" + ")";
        db.execSQL(CREATE_BUSINESS_NEWS_TABLE);

        String CREATE_HEALTH_NEWS_TABLE = "CREATE TABLE " + TABLE_HEALTH_NEWS + " (" + NEWS_TITLE + " TEXT," + NEWS_DISCRIPTION + " TEXT," +
                NEWS_DATE + " INTEGER," + NEWS_CONTENT + " TEXT,"+ NEWS_BROWSER_URL + " TEXT," + NEWS_IMAGE_URL + " TEXT" + ")";
        db.execSQL(CREATE_HEALTH_NEWS_TABLE);

        String CREATE_FUN_NEWS_TABLE = "CREATE TABLE " + TABLE_FUN_NEWS + " (" + NEWS_TITLE + " TEXT," + NEWS_DISCRIPTION + " TEXT," +
                NEWS_DATE + " INTEGER," + NEWS_CONTENT + " TEXT,"+ NEWS_BROWSER_URL + " TEXT," + NEWS_IMAGE_URL + " TEXT" + ")";
        db.execSQL(CREATE_FUN_NEWS_TABLE);

        String CREATE_GAME_NEWS_TABLE = "CREATE TABLE " + TABLE_GAMING_NEWS + " (" + NEWS_TITLE + " TEXT," + NEWS_DISCRIPTION + " TEXT," +
                NEWS_DATE + " INTEGER," + NEWS_CONTENT + " TEXT,"+ NEWS_BROWSER_URL + " TEXT," + NEWS_IMAGE_URL + " TEXT" + ")";
        db.execSQL(CREATE_GAME_NEWS_TABLE);

        String CREATE_TECH_NEWS_TABLE = "CREATE TABLE " + TABLE_TECH_NEWS + " (" + NEWS_TITLE + " TEXT," + NEWS_DISCRIPTION + " TEXT," +
                NEWS_DATE + " INTEGER," + NEWS_CONTENT + " TEXT,"+ NEWS_BROWSER_URL + " TEXT," + NEWS_IMAGE_URL + " TEXT" + ")";
        db.execSQL(CREATE_TECH_NEWS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GLOBAL_NEWS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPORTS_NEWS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUSNINESS_NEWS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEALTH_NEWS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FUN_NEWS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMING_NEWS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TECH_NEWS);


        onCreate(db);
    }


    void addNews(NewsItem newsItem, String tablename) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NEWS_TITLE, newsItem.getTitle());
        values.put(NEWS_DISCRIPTION, newsItem.getDiscription());
        values.put(NEWS_DATE, newsItem.getEpochTime());
        values.put(NEWS_CONTENT, newsItem.getContent());
        values.put(NEWS_BROWSER_URL, newsItem.getBrowserUrl());
        values.put(NEWS_IMAGE_URL,newsItem.getImageUrl());

        db.insert(tablename, null, values);
        Log.d(TAG, "News added: database: count::" + getNewsCount(tablename));
        db.close();
    }


    public ArrayList<NewsItem> getAllNews(String tablename) {
        ArrayList<NewsItem> newsList = new ArrayList<NewsItem>();

        String selectQuery = "SELECT  * FROM " + tablename;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery + " ORDER BY "+ NEWS_DATE + " DESC", new String[] {});


        if (cursor.moveToFirst() && cursor!=null) {
            do {
                NewsItem newsItem = new NewsItem();
                newsItem.setTitle(cursor.getString(0));
                newsItem.setDiscription(cursor.getString(1));
                newsItem.setEpochTime(Long.parseLong(cursor.getString(2)));
                newsItem.setContent(cursor.getString(3));
                newsItem.setBrowserUrl(cursor.getString(4));
                newsItem.setImageUrl(cursor.getString(5));
                newsList.add(newsItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return newsList;

    }

    public void emptyTableNews(String tablename) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tablename,null,null);
    }


    public int getNewsCount(String tablename) {
        int count = 0;
        String countQuery = "SELECT  * FROM " + tablename;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

    public void deleteThisNewsItem( String url, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, NEWS_BROWSER_URL + " = ?", new String[]{url});
        db.close();
        Log.d(TAG, "deleteThisNewsItem: count:: " + getNewsCount(tableName));
    }


    public  boolean isNewsItemPresent(String url, String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + tableName +  " todo WHERE " + NEWS_BROWSER_URL + " ='"  + url+"'";

        Cursor  cursor = db.rawQuery(query,null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public void deleteTableNews(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + tableName);
    }


}
