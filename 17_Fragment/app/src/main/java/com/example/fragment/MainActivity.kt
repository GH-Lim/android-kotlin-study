package com.example.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var first = FirstFragment()
    var second = SecondFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button6.setOnClickListener { view ->
            // 프래그먼트를 띄울 때는 프래그먼트 매니저를 이용
            // 안드로이드 5.0 이하 버전에서도 하려면 서포트 프래그먼트 매니저를 사용
            var tran = supportFragmentManager.beginTransaction()
            // tran.add(R.id.container, first) // 레이아웃 id, 매치할 프래그먼트
            tran.replace(R.id.container, first)
            tran.addToBackStack(null) // 문자열로 이름 아무거나 넣거나 null 값
            tran.commit()
        }

        button7.setOnClickListener { view ->
            var tran = supportFragmentManager.beginTransaction()
            // tran.add(R.id.container, second) // 레이아웃 id, 매치할 프래그먼트
            tran.replace(R.id.container, second)
            tran.addToBackStack(null)
            tran.commit()
        }
    }
}
