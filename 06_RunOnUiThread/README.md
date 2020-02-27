### RunOnUiThread

- RunOnUiThread 메서드는 개발자가 발생시킨 일반쓰래드에서 코드 일부를 Main Thread 가 처리하도록 하는 메서드이다.

- Java 에서는 메서드로 제공되나 Kotlin 에서는 람다식으로 제공되고 있으므로 작성하는 것이 간편하다.

  5초 이상걸리는 작업 or 네트워크 관련 작업에서 사용



```kotlin
package com.example.runonuithread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var isRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            var time = System.currentTimeMillis()
            textView.text = "버튼 클릭 : ${time}"
        }

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
            while(isRunning){
                SystemClock.sleep(100)
                var time = System.currentTimeMillis()
                Log.d("test1", "쓰래드 : ${time}")
                // textView2.text = "쓰래드 :${time}"  // 7.1 이하에서는 에러

                runOnUiThread {  // 이 부분은 mainThread 가 처리
                    textView2.text = "쓰래드 :${time}"
                }
            }
        }
    }
}

```

