package com.example.permission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var permission_list = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS,
        Manifest.permission.SEND_SMS,
        Manifest.permission.RECEIVE_SMS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()
    }

    fun checkPermission(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return;
        }

        for(permission : String in permission_list){

            var chk = checkCallingOrSelfPermission(permission)

            if(chk == PackageManager.PERMISSION_DENIED){
                requestPermissions(permission_list, 0);
                break;
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>, // 체크한 권한들의 목록 배열
        grantResults: IntArray // 해당 권한이 허용되어있는지 안되어있는지
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var idx = 0;

        textView.text = ""

        for(idx in grantResults.indices){  // .indices 는 인덱스 번호가 들어온다
            if(grantResults[idx] == PackageManager.PERMISSION_GRANTED){
                textView.append("${permission_list[idx]} : 허용함\n")
            } else {
                textView.append("${permission_list[idx]} : 허용하지 않음 \n}")
            }
        }
    }
}
