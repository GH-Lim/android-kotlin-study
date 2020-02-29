### Parcelable

- Intent 를 통해 객체를 전달할 때는 Pacleable 인터페이스를 구현한 객체만 가능하다.

  안드로이드에서 다른 액티비티로 객체를 전달할 때 객체 자체를 전달하는 것이 아니라 객체가 가지고 있는 값을 전달해서 똑같아 보이는 객체를 만든다.

- Parcelable 인터페이스는 전달 받은 쪽에서 객체를 복원할 때 필요한 정보를 가진 부분을 의미한다.

  이렇게 작동하는 이유 : 안드로이드는 4대 구성요소가 전부 독립적인 실행단위로 따로따로 동작

  다른 어플리케이션이 갖고 있는 4대 구성요소를 실행하는 것도 가능하기 때문에

  객체를 직접 전달하는 것이 아닌 객체가 가지고 있는 값을 복원하는 방식으로 작동



```kotlin
package com.example.sendobject

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
            var t1 = TestClass()
            t1.data10 = 100
            t1.data20 = "문자열1"

            var intent = Intent(this, SecondActivity::class.java)

            intent.putExtra("test1", t1)
            startActivityForResult(intent, SECOND_ACTIVITY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            var t2 = data?.getParcelableExtra<TestClass>("test2")
            textView.text = "t2.data10 : ${t2?.data10}\n"
            textView.append("t2.data20 : ${t2?.data20}")
        }
    }
}

```

```kotlin
package com.example.sendobject

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var t1 = intent.getParcelableExtra<TestClass>("test1")

        textView2.text = "t1.data10 : ${t1.data10}\n"
        textView2.append("t1.data20 : ${t1.data20}")

        button2.setOnClickListener { view ->
            var t2 = TestClass()
            t2.data10 = 200
            t2.data20 = "문자열2"

            var intent2 = Intent()
            intent2.putExtra("test2", t2)
            setResult(Activity.RESULT_OK, intent2)
            finish()
        }
    }
}

```

