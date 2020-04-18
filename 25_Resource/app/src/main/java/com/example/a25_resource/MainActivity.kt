package com.example.a25_resource

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener { view ->
//            var str1 = resources.getString(R.string.str2);
//            textView.text = str1
            textView.setText(R.string.str2) // 텍스트 뷰는 많이 쓰는 거라 제공.
        }
    }
}
