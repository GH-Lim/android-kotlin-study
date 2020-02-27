package com.example.runactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            // 액티비티를 실행할 때는 intent 객체를 만들어야함
            var intent = Intent(this, SecondActivity::class.java)
            // Java 에서는 SecondActivity.java
            startActivity(intent)
        }

        button3.setOnClickListener { view ->
            finish() // 백스테이트에 아무것도 없다면 앱 종료
        }
    }
}
