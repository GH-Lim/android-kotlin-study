package com.example.contentprovider

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            var uri = Uri.parse("content://kr.co.softcampus.dbprovider")

            var c = contentResolver.query(uri, null, null, null, null)

            textView.text = ""

            if (c != null) {
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
            }
        }
    }
}
