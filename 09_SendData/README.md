### Intent 에 데이터 셋팅하기

- Activity를 실행하기 위해 사용하는 Intent 객체에 putExtra 메서드를 이용하여 데이터를 셋팅하면 실행되는 Activity에서 데이터를 전달 받을 수 있다.
- putExtra 메서드는 자료형 별로 메서드가 제공되기 떄문에 타입을 가리지 않는다.



### Intent 에서 데이터 가져오기

- Intent에 셋팅된 데이터는 getXXXExtra 메서드를 이용해 가져오면 된다.
- 자료형 별로 메서드가 제공되므로 가져오고자 하는 데이터 타입에 맞는 메서드를 이용하여 가져온다.



```kotlin
package com.example.senddata

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val SECOND_ACTIVITY = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            var intent = Intent(this, SecondActivity::class.java)

            intent.putExtra("data1", 100)  // 자료형 별로 오버로딩.
            intent.putExtra("data2", 11.11)
            intent.putExtra("data3", true)
            intent.putExtra("data4", "문자열1")

            //startActivity(intent)
            startActivityForResult(intent, SECOND_ACTIVITY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            var value1 = data?.getIntExtra("value1", 0)
            var value2 = data?.getDoubleExtra("value2", 0.0)
            var value3 = data?.getBooleanExtra("value3", false)
            var value4 = data?.getStringExtra("value4")

            textView.text = "value1 : ${value1}\n"
            textView.append("value2 : ${value2}\n")
            textView.append("value3 : ${value3}\n")
            textView.append("value4 : ${value4}\n")
        }
    }
}

```

```kotlin
package com.example.senddata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var data1 = intent.getIntExtra("data1", 0) // 값이 없으면 디폴드 값
        var data2 = intent.getDoubleExtra("data2", 0.0)
        var data3 = intent.getBooleanExtra("data3", false)
        var data4 = intent.getStringExtra("data4")  // string or 객체 호출은 디폴트는 null 로 고정

        textView2.text = "data1 : ${data1}\n"
        textView2.append("data2 : ${data2}\n")
        textView2.append("data3 : ${data3}\n")
        textView2.append("data4 : ${data4}\n")


        button2.setOnClickListener { view ->

            var intent2 = Intent()
            intent2.putExtra("value1", 200)
            intent2.putExtra("value2", 22.22)
            intent2.putExtra("value3", true)
            intent2.putExtra("value4", "문자열2")

            setResult(RESULT_OK, intent2)

            finish()
        }
    }
}

```

