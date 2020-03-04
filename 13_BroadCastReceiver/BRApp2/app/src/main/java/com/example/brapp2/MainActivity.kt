package com.example.brapp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
//            var intent = Intent()
//            intent.setClassName("com.example.brapp1", "com.example.brapp1.TestReceiver")  // 명시적
            // 패키지 이름, 그 패키지의 클래스 이름
            var intent = Intent("com.test.brapp1")  // 암시적
            // 같은 이름을 모두 실행한다.
            intent.putExtra("data1", 100)
            intent.putExtra("data2", 11.11)
            sendBroadcast(intent)
        }
    }
}
