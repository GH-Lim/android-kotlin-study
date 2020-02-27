## Handler 를 활용한 반복작업

### Main Thread 에서의 반복

- Main Thread 에서 처리하는 코드(Activity 내의 코드) 중에 일정 작업을 계속 반복하여 처리해야하는 경우가 있다.
- 이때 무한 루프를 쓰면 화면처리가 불가능하다.
- Handler 를 통하면 원하는 코드를 반복해서 작업하는 것이 가능하다.
- Main Thread 가 쉬는 동안에 안드로이드 os 가 화면 처리를 할수있다.

### Handler

- Handler 는 개발자가 안드로이드 OS에게 작업 수행을 요청하는 역할을 한다.
- 개발자가 작업을 요청하면 안드로이드 OS는 작업을 하지 않을 때 개발자가 요청한 작업을 처리하게 된다.
- 이 처리는 Main Thread 에서 처리한다.
- 5초 이상 걸리는 작업 불가 / 화면 처리 가능
- 대부분 오래걸리는 것은 일반 쓰래드에서 처리하므로 Main Thread 는 대체로 여유가 있다.



```kotlin
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
```

