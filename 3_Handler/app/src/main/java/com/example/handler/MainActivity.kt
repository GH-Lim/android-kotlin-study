package com.example.handler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    var handler : Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            var time = System.currentTimeMillis()
            textView.text = "버튼 클릭 : ${time}"
        }

        handler = Handler()

//        while(true){
//            SystemClock.sleep(100)
//            var time = System.currentTimeMillis()
//            textView2.text = "while : ${time}"
//        } // 화면을 출력할 여유가 없다.

        // main thread 가 한가할 때 작업을 처리해주세요
        var thread = ThreadClass()  // 쓰래드를 상속받은 클래스의 객체 생성
        // handler?.post(thread)  // post 메서드를 호출하면 안드로이드 os가 한가할 때 이 메서드를 바로 처리
        handler?.postDelayed(thread, 100)
    }

    // 5초 이상 걸리지 않는 작업
    // thread 를 상속받는 클래스
    inner class ThreadClass : Thread(){
        override fun run() {  // 새로 생성한 일반 thread 가 아니라 main thread 가 관리 중이기 때문에 무한루프 x
            var time = System.currentTimeMillis()
            textView2.text = "Handler : ${time}"

            // handler?.post(this)  // 따라서 다시 자기 자신을 요청
            handler?.postDelayed(this, 100)
        }
    }
}
