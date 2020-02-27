package com.example.threadhandler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.SystemClock
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var isRunning: Boolean = false
    var handler: DisplayHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            var time = System.currentTimeMillis()
            textView.text = "버튼 클릭 : ${time}"
        }

        handler = DisplayHandler()  // 쓰래드 가동 전 객체 생성

        isRunning = true
        var thread = ThreadClass()
        thread.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    inner class ThreadClass : Thread(){
        override fun run() {

            var a1 = 10
            var a2 = 20

            while(isRunning){
                SystemClock.sleep(100)
                var time = System.currentTimeMillis()
                Log.d("test1", "쓰래드 : ${time}")
                // textView2.text = "쓰래드 : ${time}"
                // handler?.sendEmptyMessage(0)
//                var msg = Message()
//                msg.what = 0
//                msg.obj = time  // 객체를 넘길 수 있다. => 아무 타입이나 가능
//                handler?.sendMessage(msg)

                var msg2 = Message()
                msg2.what = 1
                msg2.arg1 = ++a1    // Int
                msg2.arg2 = ++a2    // Int
                msg2.obj = "안녕하세요"  // 객체
                handler?.sendMessage(msg2)
            }
        }
    }

    inner class DisplayHandler : Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            if(msg?.what == 0) {
                // var time = System.currentTimeMillis()
                textView2.text = "Handler : ${msg?.obj}"
            } else if (msg?.what == 1){
                textView2.text = "${msg?.arg1}, ${msg?.arg2}, ${msg?.obj}"
            }
        }
    }
}
