package com.example.ipc

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var ipc_service : IPCService? = null
    // IPC 서비스 클래스의 객체 주소값을 받아오는 목적

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var intent = Intent(this, IPCService::class.java)
        if(isServiceRunning("com.example.ipc.IPCService") == false){
            startService(intent)
        }
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)

        button.setOnClickListener { view ->
            var value = ipc_service?.getNumber()
            textView.text = "value : ${value}"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(mConnection)
    }

    fun isServiceRunning(name: String) : Boolean{
        var manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for(service : ActivityManager.RunningServiceInfo in manager.getRunningServices(Int.MAX_VALUE)){
            // 현재 실행 중인 서비스에 관련된 모든 정보를 가져와서 정보만큼 반복문을 돌린 후
            // 서비스의 이름을 가지고 온다.
            if(service.service.className.equals(name)){
                return true
            }
        }
        return false
    }
    // 서비스 커넥션 : 액티비티에서 서비스에 접속/해제 하게 되면 자동으로 호출되는 메서드

    private val mConnection = object  : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // onBind 메서드가 반환하는 객체의 주소값이 service 로 들어온다.
            val binder = service as IPCService.LocalBinder
            ipc_service = binder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            ipc_service = null
        }
    }
}
