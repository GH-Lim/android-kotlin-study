package com.example.sqlite1

import android.content.ContentValues
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
//            var sql = "insert into TestTable (textData, intData, floatData, dateData) values (?, ?, ?, ?)"
//
//            var sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//            var date = sdf.format(Date())
//
//            var arg1 = arrayOf("문자열1", "100", "11.11", date)
//            var arg2 = arrayOf("문자열2", "200", "22.22", date)
//
//            db.execSQL(sql, arg1)
//            db.execSQL(sql, arg2)

            var sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            var date = sdf.format(Date())

            var cv1 = ContentValues() // 어떤 컬럼에 어떤 값을 대응하겠다는 것을 지정해주는 클래스
            cv1.put("textData", "문자열1")
            cv1.put("intData", 100)
            cv1.put("floatData", 11.11)
            cv1.put("dateData", date)

            db.insert("TestTable", null, cv1) // 지정되지 않은 value 에 null

            var cv2 = ContentValues()
            cv2.put("textData", "문자열2")
            cv2.put("intData", 200)
            cv2.put("floatData", 22.22)
            cv2.put("dateData", date)

            db.insert("TestTable", null, cv2)

            db.close()

            textView.text = "저장완료"
        }

        button2.setOnClickListener { view ->
            var helper:DBHelper = DBHelper(this)
            var db:SQLiteDatabase = helper.writableDatabase

//            var sql = "select * from TestTable"
//
//            var c: Cursor = db.rawQuery(sql, null)
//            // 커서 클래스타입의 객체는 셀렉트해서 가져온 데이터에 접근해서 가져올수 있다

            var c = db.query("TestTable", null, null, null, null, null, null)
            // db.query(
            //     테이블 이름,
            //     가져올 컬럼 이름의 문자열 배열,
            //     조건절(값이 들어가는 부분 물음표 a=?),
            //     조건절의 ? 에 세팅될 값의 문자열 배열,
            //     정렬 기준 groupBy,
            //     having,
            //     orderBy
            // )

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

//            var sql = "update TestTable set textData=? where idx=?"
//            var args = arrayOf("문자열3", "1")
//            db.execSQL(sql, args)

            var cv = ContentValues()
            cv.put("textData", "문자열3")
            var where = "idx=?"
            var args = arrayOf("1")

            db.update("TestTable", cv, where, args)

            db.close()

            textView.text = "수정완료"
        }

        button4.setOnClickListener { view->
            var helper = DBHelper(this)
            var db = helper.writableDatabase

//            var sql = "delete from TestTable where idx=?"
//            var args = arrayOf("1") // 하나여도 array
//
//            db.execSQL(sql, args)

            var where = "idx=?"
            var args = arrayOf("1")

            db.delete("TestTable", where, args)

            db.close()

            textView.text = "삭제완료"
        }
    }
}
