package com.example.activityaction

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var permission_list = arrayOf(
            Manifest.permission.CALL_PHONE
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()

        button.setOnClickListener { view ->
            var uri = Uri.parse("geo:37.243243,131.861601")
            var intent = Intent(Intent.ACTION_VIEW, uri)
            // 위도와 경도 => 지도앱
            // 사이트 도메인 => 웹브라우저
            // 이미지경로 => 이미지앱
            startActivity(intent)
        }

        button2.setOnClickListener { view ->
            var uri = Uri.parse("http://developer.android.com")
            var intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        button3.setOnClickListener { view ->
            var uri = Uri.parse("tel:000000000")
            var intent = Intent(Intent.ACTION_DIAL, uri)
            startActivity(intent)
        }

        button4.setOnClickListener { view ->
            // 전화 거는 것은 요금이 들어가기 때문에 권한 필요
            var uri = Uri.parse("tel:00000000000")
            var intent = Intent(Intent.ACTION_CALL, uri)
            startActivity(intent)
        }
    }

    fun checkPermission(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return
        }
        for(permission : String in permission_list){
            var chk = checkCallingOrSelfPermission(permission)

            if (chk == PackageManager.PERMISSION_DENIED){
                requestPermissions(permission_list, 0)
                break
            }
        }
    }
}
