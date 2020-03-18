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
        // 서비스 중 유지보수를 위해 table 구조를 변경했다면
        // 새롭게 설치하는 유저들은 onCreate 를 통해 가능하지만
        // 기존 유저들은 테이블을 새로 만든다면 데이터가 날아간다!
        // DBHelper 의 버전 정보를 통해 바꾼다.
        Log.d("msg", "oldVersion : ${oldVersion}, newVersion : ${newVersion}")
    }
}