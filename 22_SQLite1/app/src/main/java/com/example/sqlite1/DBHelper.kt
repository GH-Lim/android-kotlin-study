package com.example.sqlite1

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context) : SQLiteOpenHelper(context, "Test.db", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        // DataBase 가 있는지 확인하고 없다면 만들어 주는 역할 table 생성
        Log.d("msg", "on create")

        var sql = "create table TestTable (" +
                "idx integer primary key autoincrement, " +
                "textData text not null, " +
                "intData integer not null, " +
                "floatData real not null, " +
                "dateData date not null" +
                ")"

        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //
    }
}