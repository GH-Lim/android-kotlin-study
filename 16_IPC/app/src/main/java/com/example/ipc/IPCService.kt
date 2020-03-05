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
