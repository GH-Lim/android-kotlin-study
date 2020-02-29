package com.example.senddata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var data1 = intent.getIntExtra("data1", 0) // 값이 없으면 디폴드 값
        var data2 = intent.getDoubleExtra("data2", 0.0)
        var data3 = intent.getBooleanExtra("data3", false)
        var data4 = intent.getStringExtra("data4")  // string or 객체 호출은 디폴트는 null 로 고정

        textView2.text = "data1 : ${data1}\n"
        textView2.append("data2 : ${data2}\n")
        textView2.append("data3 : ${data3}\n")
        textView2.append("data4 : ${data4}\n")


        button2.setOnClickListener { view ->

            var intent2 = Intent()
            intent2.putExtra("value1", 200)
            intent2.putExtra("value2", 22.22)
            intent2.putExtra("value3", true)
            intent2.putExtra("value4", "문자열2")

            setResult(RESULT_OK, intent2)

            finish()
        }
    }
}
