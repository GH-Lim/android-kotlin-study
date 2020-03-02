package com.example.acitivityapp1

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
            var intent = Intent("com.test.second")
            // 안드로이드 OS 는 자기 단말기에 설치되어있는 어플들의
            // 모든 4대 구성 요소들을 목록화해서 가지고 있다.
            // 같은 이름이 여러 개라면 고르는 창이 뜬다.

            intent.putExtra("data1", 100)
            intent.putExtra("data2", 11.11)

            //startActivity(intent)
            startActivityForResult(intent, SECOND_ACTIVITY)
            // 안드로이드 4대 구성요소들은 각각 독립된 실행단위이기 때문에
            // 다른 어플들이 가지고 있는 액티비티를 실행할 수 있다.
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            var value1 = data?.getIntExtra("value1", 0)
            var value2 = data?.getDoubleExtra("value2", 0.0)
            textView.text = "value1 : ${value1}\n"
            textView.append("value2 : ${value2}")
        }
    }
}
