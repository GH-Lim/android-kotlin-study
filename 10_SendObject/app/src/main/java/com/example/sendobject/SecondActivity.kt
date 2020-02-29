package com.example.sendobject

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var t1 = intent.getParcelableExtra<TestClass>("test1")

        textView2.text = "t1.data10 : ${t1.data10}\n"
        textView2.append("t1.data20 : ${t1.data20}")

        button2.setOnClickListener { view ->
            var t2 = TestClass()
            t2.data10 = 200
            t2.data20 = "문자열2"

            var intent2 = Intent()
            intent2.putExtra("test2", t2)
            setResult(Activity.RESULT_OK, intent2)
            finish()
        }
    }
}
