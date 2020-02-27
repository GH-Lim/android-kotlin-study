### startActivityForResult

- Activity 에서 다른 Activity를 실행하고 다시 돌아왔을 때 어떤 처리가 필요하다면 Activity를 실행할 때 startActivity가 아닌 startActivityForResult 메서드를 사용한다.

### onActivityResult

- startActivityForResult 메서드를 이용해 Activity를 실행하고 돌아오면 자동으로 onActivityResult 메서드가 호출된다. 여기에서 필요한 작업을 처리하면 된다.
  - `requestCode` : 어떤 액티비티에 갔다왔는지 구분
  - resultCode : 실행된 새로운 액티비티에서 수행된 작업의 결과 (back 버튼 누르면 RESULT_CANCELED)

```kotlin
package com.example.onactivityresult

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val SECOND_ACTIVITY = 1
    val THIRD_ACTIVITY = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button2.setOnClickListener { view ->
            var intent = Intent(this, SecondActivity::class.java)
            // startActivity(intent)
            startActivityForResult(intent, SECOND_ACTIVITY) // requestCode: onActivityResult 의 매개변수.
        }
        button4.setOnClickListener { view ->
            var intent = Intent(this, ThirdActivity::class.java)
            startActivityForResult(intent, THIRD_ACTIVITY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            SECOND_ACTIVITY -> {
                textView2.text = "Return From Second Activity\n"
                when(resultCode){
                    Activity.RESULT_OK -> {
                        textView2.append("RESULT_OK")
                    }
                    Activity.RESULT_CANCELED -> {
                        textView2.append("RESULT_CANCELED")
                    }
                    Activity.RESULT_FIRST_USER -> {
                        textView2.append("RESULT_FIRST_USER")
                    }
                }
            }
            THIRD_ACTIVITY -> {
                textView2.text = "Return From Third Acticity"
            }
        }
    }
}

```

```kotlin
package com.example.onactivityresult

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        button.setOnClickListener { view ->
            setResult(Activity.RESULT_OK)
            finish()
        }
        button5.setOnClickListener { view ->
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
        button6.setOnClickListener { view ->
            setResult(Activity.RESULT_FIRST_USER)
            finish()
        }
    }
}

```

```kotlin
package com.example.onactivityresult

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_third.*

class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        button3.setOnClickListener { view ->
            finish()
        }
    }
}

```

