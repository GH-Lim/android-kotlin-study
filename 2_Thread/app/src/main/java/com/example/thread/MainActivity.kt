package com.example.thread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            var now = System.currentTimeMillis()
            textView.text = "버튼 클릭 : ${now}"
        }
        /*
        while(true){
            var now = System.currentTimeMillis()
            textView2.text = "무한 루프 : ${now}"
        } // 안드로이드 os가 쓰래드를 발생 (메인 쓰래드) 이부분을 처리하느라 화면을 처리할 수 없음
        */

        isRunning = true
        var thread = ThreadClass1()
        thread.start() // #1 Activity 가 종료되어도 우리가 발생시킨 쓰래드는 종료되지 않는다.
    }

    // 쓰래드 운영 ( 개발자가 만들어서 발생시키는 쓰래드 )
    inner class  ThreadClass1 : Thread(){
        override fun run() {
            while(isRunning){  // #1 따라서 boolean 값을 false 로 바꿔줘야 함
                SystemClock.sleep(100) // 보통 Thread.sleep 을 많이 사용하지만 안드로이드에선 시스템클락
                var now = System.currentTimeMillis()
                Log.d("test1", "쓰래드 : ${now}")

                // 화면에 관련된 작업
                textView2.text = "쓰래드 : ${now}"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }
}
