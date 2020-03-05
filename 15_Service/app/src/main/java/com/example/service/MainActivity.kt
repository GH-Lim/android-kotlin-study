package com.example.service

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var service_intent : Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            service_intent = Intent(this, ServiceClass1::class.java)
            startService(service_intent)
            finish()
        }

        button2.setOnClickListener { view ->
            stopService(service_intent)
        }

        button3.setOnClickListener { view ->
            service_intent = Intent(this, ServiceClass2::class.java)
            startService(service_intent)
            finish()
        }

        button4.setOnClickListener { view ->
            service_intent = Intent(this, ServiceClass3::class.java)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                // 8.0 이상 부터는 notification 메시지를 반드시 띄워줘야 서비스가 중단되지 않고 계속 실행
                // 5초 안에 띄워주지 않으면 서비스 중단 강제 종료
                startForegroundService(service_intent)
            } else {
                startService(service_intent)
            }
            finish()
        }
    }
}
