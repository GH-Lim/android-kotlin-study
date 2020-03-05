### 서비스

- 안드로이드 4대 구성요소 중 하나로 백그라운드 처리를 위해 제공되는 요소이다.

- Activity는 화면을 가지고맀러 화면이 보이는 동안 동작하지만 Service는 화면을 가지고 있지 않아 보이지 않는 동안에도 동작하는 것을 의미한다.

  ```kotlin
  class ServiceClass1 : Service() {
  
      override fun onBind(intent: Intent): IBinder {
          TODO("Return the communication channel to the service.")
      }
  
      override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
          var thread = ThreadClass()
          thread.start()
          return super.onStartCommand(intent, flags, startId)
      }
  
      override fun onDestroy() {
          super.onDestroy()
          Log.d("test1", "서비스 실행 종료")
      }
  
      inner class ThreadClass : Thread(){
          override fun run() {
              var idx = 0
              while(idx < 10){
                  SystemClock.sleep(1000)
                  var time = System.currentTimeMillis()
                  Log.d("test1", "Service Running : ${time}")
                  idx++
              }
          }
      }
  }
  ```

  

### Intent Service

- IntentService는 Service 내부에서 Thread를 운영하고자 할 때 사용하는 서비스이다.

- IntentService

  ```kotlin
  class ServiceClass2 : IntentService("ServiceClass2") {
  
      override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
          return super.onStartCommand(intent, flags, startId)
      }
  
      override fun onHandleIntent(intent: Intent?) {
          var idx = 0
          while(idx < 10){
              SystemClock.sleep(1000)
              var time = System.currentTimeMillis()
              Log.d("test1", "Intent Service Running : ${time}")
              idx++
          }
      }
  }
  ```

  

### Foreground Service

- 서비스는 기본적으로 백그라운드에서 운영되는 실행요소로써 메모리가 부족해지거나 하면 안드로이드 OS에 의해 제거된다.

- 이를 방지하고자 할 때는 Foreground Service로 만들어 사용하면 된다.

  끝까지 동작하는 것을 보장받을 수 있다!

- 안드로이드 8.0 이상부터는 포그라운드 서비스 이용을 권장

- 안드로이드 9(API 레벨 28) 이상에서 포그라운드 서비스를 사용하는 앱은 `FOREGROUND_SERVICE` 권한을 요청해야 한다.

  `<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />`

```kotlin
package com.example.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat

class ServiceClass3 : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        var builder : NotificationCompat.Builder? = null

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            var channel = NotificationChannel("test1", "Service", NotificationManager.IMPORTANCE_HIGH)
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            manager.createNotificationChannel(channel)

            builder = NotificationCompat.Builder(this, "test1")
        } else {
            builder = NotificationCompat.Builder(this)
        }

        builder?.setSmallIcon(android.R.drawable.ic_menu_search)
        builder?.setContentTitle("서비스 가동")
        builder?.setContentText("서비스가 가동 중입니다.")
        var notification = builder?.build()

        startForeground(10, notification)

        var thread = ThreadClass()
        thread.start()
        return super.onStartCommand(intent, flags, startId)
    }

    inner class ThreadClass : Thread(){
        override fun run() {
            var idx = 0
            while(idx < 10){
                SystemClock.sleep(1000)
                var time = System.currentTimeMillis()
                Log.d("test1", "Foreground Service Running: ${time}")
                idx++
            }
        }
    }
}

```

```kotlin
/*
	MainActivity.kt
*/
package com.example.service

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var service_intent : Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            service_intent = Intent(this, ServiceClass1::class.java)
            startService(service_intent)
            finish()
        }

        button2.setOnClickListener { view ->
            stopService(service_intent)
        }

        button3.setOnClickListener { view ->
            service_intent = Intent(this, ServiceClass2::class.java)
            startService(service_intent)
            finish()
        }

        button4.setOnClickListener { view ->
            service_intent = Intent(this, ServiceClass3::class.java)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                // 8.0 이상 부터는 notification 메시지를 반드시 띄워줘야 서비스가 중단되지 않고 계속 실행
                // 5초 안에 띄워주지 않으면 서비스 중단 강제 종료
                startForegroundService(service_intent)
            } else {
                startService(service_intent)
            }
            finish()
        }
    }
}

```

