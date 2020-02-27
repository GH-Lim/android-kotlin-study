package com.example.onactivityresult

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val SECOND_ACTIVITY = 1
    val THIRD_ACTIVITY = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button2.setOnClickListener { view ->
            var intent = Intent(this, SecondActivity::class.java)
            // startActivity(intent)
            startActivityForResult(intent, SECOND_ACTIVITY) // requestCode: onActivityResult 의 매개변수.
        }
        button4.setOnClickListener { view ->
            var intent = Intent(this, ThirdActivity::class.java)
            startActivityForResult(intent, THIRD_ACTIVITY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            SECOND_ACTIVITY -> {
                textView2.text = "Return From Second Activity\n"
                when(resultCode){
                    Activity.RESULT_OK -> {
                        textView2.append("RESULT_OK")
                    }
                    Activity.RESULT_CANCELED -> {
                        textView2.append("RESULT_CANCELED")
                    }
                    Activity.RESULT_FIRST_USER -> {
                        textView2.append("RESULT_FIRST_USER")
                    }
                }
            }
            THIRD_ACTIVITY -> {
                textView2.text = "Return From Third Acticity"
            }
        }
    }
}
