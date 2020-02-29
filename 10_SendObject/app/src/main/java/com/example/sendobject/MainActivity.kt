package com.example.sendobject

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val SECOND_ACTIVITY = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            var t1 = TestClass()
            t1.data10 = 100
            t1.data20 = "문자열1"

            var intent = Intent(this, SecondActivity::class.java)

            intent.putExtra("test1", t1)
            startActivityForResult(intent, SECOND_ACTIVITY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            var t2 = data?.getParcelableExtra<TestClass>("test2")
            textView.text = "t2.data10 : ${t2?.data10}\n"
            textView.append("t2.data20 : ${t2?.data20}")
        }
    }
}
