package com.example.fernandowonder.walale;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conection extends SQLiteOpenHelper{

    public Conection(Context context) {
        super(context, "banco", null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS tbletra(_id integer primary key AUTOINCREMENT,idMusica long,path text, titulo text,autor text,texto text)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS tbfavoritos(_id integer primary key AUTOINCREMENT,idMusica long)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tbletra");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tbFavoritos");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS tbletra(_id integer primary key AUTOINCREMENT,idMusica long,path text, titulo text,autor text,texto text)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS tbfavoritos(_id integer primary key AUTOINCREMENT,idMusica long)");

    }
}
