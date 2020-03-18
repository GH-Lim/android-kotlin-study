### SQLite 데이터 베이스

- 안드로이드에서 사용하는 내장 데이터 베이스로 표준 SQL문을 사용하는 관계형 데이터 베이스이다.
- MySQL과 유사한 문법을 사용하고 있으며 일반적인 관계형 데이터 베이스가 가지고 있는 기능을 가지고 있다.
- 표준 sql 문법을 숙지해야 한다.

```kotlin
package com.example.sqlite1

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            var helper = DBHelper(this)
            var db = helper.writableDatabase // 여기까지가 database 오픈

            // 표준 sql 문을 통해 집어넣는다.
            var sql = "insert into TestTable (textData, intData, floatData, dateData) values (?, ?, ?, ?)"

            var sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            var date = sdf.format(Date())

            var arg1 = arrayOf("문자열1", "100", "11.11", date)
            var arg2 = arrayOf("문자열2", "200", "22.22", date)

            db.execSQL(sql, arg1)
            db.execSQL(sql, arg2)

            db.close()

            textView.text = "저장완료"
        }

        button2.setOnClickListener { view ->
            var helper:DBHelper = DBHelper(this)
            var db:SQLiteDatabase = helper.writableDatabase

            var sql = "select * from TestTable"

            var c: Cursor = db.rawQuery(sql, null)
            // 커서 클래스타입의 객체는 셀렉트해서 가져온 데이터에 접근해서 가져올수 있다

            textView.text = ""

            while(c.moveToNext()){
                //현재 row 에서 다음 row 로 가면서 더 이상 없으면 false 반환
                var idx_pos = c.getColumnIndex("idx")
                var textData_pos = c.getColumnIndex("textData")
                var intData_pos = c.getColumnIndex("intData")
                var floatData_pos = c.getColumnIndex("floatData")
                var dateData_pos = c.getColumnIndex("dateData")

                var idx = c.getInt(idx_pos)
                var textData = c.getString(textData_pos)
                var intData = c.getInt(intData_pos)
                var floatData = c.getDouble(floatData_pos)
                var dateData = c.getString(dateData_pos)

                textView.append("idx : ${idx}\n")
                textView.append("textData : ${textData}\n")
                textView.append("intData : ${intData}\n")
                textView.append("floatData : ${floatData}\n")
                textView.append("dateData : ${dateData}\n\n")
            }

            db.close()
        }

        button3.setOnClickListener { view->
            var helper = DBHelper(this)
            var db = helper.writableDatabase

            var sql = "update TestTable set textData=? where idx=?"
            var args = arrayOf("문자열3", "1")
            db.execSQL(sql, args)

            db.close()

            textView.text = "수정완료"
        }

        button4.setOnClickListener { view->
            var helper = DBHelper(this)
            var db = helper.writableDatabase

            var sql = "delete from TestTable where idx=?"
            var args = arrayOf("1") // 하나여도 array

            db.execSQL(sql, args)

            db.close()

            textView.text = "삭제완료"
        }
    }
}

```

```kotlin
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
```

