package com.example.a25_resource

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
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
        button2.setOnClickListener { view ->
            textView.text = ""
            var str_array = resources.getStringArray(R.array.data_array)
            for (str1 : String in str_array){
                textView.append("${str1}\n")
            }
        }
        button3.setOnClickListener { view ->
//            textView.setTextColor(Color.YELLOW)
//            var color = Color.rgb(26, 106, 129)
//            var color = Color.argb(50, 26,106,129)
            var color = ContextCompat.getColor(this, R.color.color1)
            textView.setTextColor(color)
        }
        button4.setOnClickListener { view ->
            var px = resources.getDimension(R.dimen.px)
            var dp = resources.getDimension(R.dimen.dp)
            var sp = resources.getDimension(R.dimen.sp)
            var inch = resources.getDimension(R.dimen.inch)
            var mm = resources.getDimension(R.dimen.mm)
            var pt = resources.getDimension(R.dimen.pt)

            textView.text = "px : ${px}\n"
            textView.append("dp : ${dp}\n")
            textView.append("sp : ${sp}\n")
            textView.append("inch : ${inch}\n")
            textView.append("mm : ${mm}\n")
            textView.append("pt : ${pt}\n")

            textView.setTextSize(20 * dp)
        }
    }
}
