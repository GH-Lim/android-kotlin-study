### AsyncTask

- AsyncTask 는 비동기 처리를 위해 제공되는 클래스이다.
- 개발자가 발생시키는 쓰래드와 핸들러의 조힙으로 쓰래드 운영 중 화면 처리가 가능했던 구조를 클래스로 제공하는 것이다.



### 메서드

- `onPreExcute` : doInBackground 메서드가 호출되기 전에 호출되는 메서드. Main Thread가 처리한다.

  5초 이상 x, 화면처리 o

- `doInBackground` : 일반 쓰래드에서 처리한다.

  새로운 쓰래드 발생(일반 쓰래드), 5초 이상 o, 화면처리 x

- `onProgressUpdate` : doInBackground 메서드에서 publishProgress 메서드 내에서 화면 처리가 필요할 때 사용한다.

- `onPostExcute` : doInBackground 메서드 수행완료 후 호출. Main Thread가 처리한다.



```kotlin
package com.example.asynctask

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            var time = System.currentTimeMillis()
            textView.text = "버튼 클릭 : ${time}"
        }

        var sync = AsyncTaskClass()
        sync.execute(10, 20)  // doInBackground 의 매서드의 매개변수로 넘어간다.
    }

    inner class AsyncTaskClass : AsyncTask<Int, Long, String>(){
        // 제너릭 타입 3개 정의 <
        // excute (doInBackground 의 매개변수 타입),
        // publishProgress 의 매개변수 타입,
        // doInBackground 의 반환타입>

        override fun onPreExecute() {
            super.onPreExecute()
            textView2.text = "AsyncTask 가동"
        }

        override fun doInBackground(vararg params: Int?): String { // ?는 null 을 허용하는 변수  // 일반 쓰래드에서 처리
            var a1 = params[0]!! // !!는 null 을 허용하는 변수의 값을 null 을 허용하지 않는 변수에 집어넣을 때
            var a2 = params[1]!!

            for(idx in 0..9){
                SystemClock.sleep(100)

                a1++
                a2++

                Log.d("test1", "${idx} : ${a1}, ${a2}")
                // textView.text = "${idx} : ${a1}, ${a2}"

                var time = System.currentTimeMillis()
                publishProgress(time)  // 일반 쓰래드에서 처리하는 매서드
            }
            return "수행이 완료되었습니다"  // onPostExecute 의 매개변수
        }

        override fun onProgressUpdate(vararg values: Long?) {
            super.onProgressUpdate(*values)

            textView2.text = "Async : ${values[0]}" // 메인 쓰래드가 처리
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            textView2.text = result
        }
    }
}

```

