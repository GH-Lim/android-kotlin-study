package com.example.senddata

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
            var intent = Intent(this, SecondActivity::class.java)

            intent.putExtra("data1", 100)  // 자료형 별로 오버로딩.
            intent.putExtra("data2", 11.11)
            intent.putExtra("data3", true)
            intent.putExtra("data4", "문자열1")

            //startActivity(intent)
            startActivityForResult(intent, SECOND_ACTIVITY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            var value1 = data?.getIntExtra("value1", 0)
            var value2 = data?.getDoubleExtra("value2", 0.0)
            var value3 = data?.getBooleanExtra("value3", false)
            var value4 = data?.getStringExtra("value4")

            textView.text = "value1 : ${value1}\n"
            textView.append("value2 : ${value2}\n")
            textView.append("value3 : ${value3}\n")
            textView.append("value4 : ${value4}\n")
        }
    }
}
