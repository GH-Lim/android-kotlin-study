### IPC

- Activity에서 실행중인 서비스의 데이터를 사용하고자 할 때 사용하는 개념이다.
- 현재 실행중인 서비스에 접속하고 서비스가 가지고 있는 메서드를 호출함으로써 값을 가져와 사용할 수 있다.

```kotlin
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

```



```kotlin
package com.example.ipc

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.SystemClock
import android.util.Log

class IPCService : Service() {

    var value = 0
    var thread: ThreadClass? = null
    var binder : IBinder = LocalBinder()

    override fun onBind(intent: Intent): IBinder {
        // TODO("Return the communication channel to the service.")
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        thread = ThreadClass()
        thread?.start()
        // 서비스에 접속을 하려고 시도했을 떄 접속하고자 하는 쓰래드가 동작 중이 아니라고 한다면
        // 동작을 시키고 접속할 수 있다.
        // 만약에 이렇게 하게 된다면 접속이 해제가 되면 현재 운영되고 있는 쓰래드가 중단이 되기 때문에
        // 다음번에 다시 접속할 수 가 없게 된다.?


        return super.onStartCommand(intent, flags, startId)
    }

    inner class ThreadClass : Thread(){
        override fun run() {
            while(true){
                SystemClock.sleep(1000)
                Log.d("test1", "value : ${value}")
                value++
            }
        }
    }

    inner class LocalBinder : Binder(){

        fun getService(): IPCService{
            return this@IPCService
        }
    }

    fun getNumber() : Int{
        return value
    }
}

```

