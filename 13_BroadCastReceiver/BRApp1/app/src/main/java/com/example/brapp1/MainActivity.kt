package com.example.brapp1

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var brapp1:TestReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addReceiver()

        button.setOnClickListener { view ->
            var intent = Intent(this, TestReceiver::class.java)
            sendBroadcast(intent)
        }
    }

    fun addReceiver(){
        // 암시적을 쓰려면 반드시 코드를 통해서 인텐트를 등록했다가 제거해야함
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            return
        }
        brapp1 = TestReceiver()
        var filter = IntentFilter("com.test.brapp1")
        registerReceiver(brapp1, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        if(brapp1 != null){
            unregisterReceiver(brapp1)
            brapp1 = null
        }
    }
}
