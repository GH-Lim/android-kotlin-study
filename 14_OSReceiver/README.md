### 시스템 메시지

- 안드로이드에서는 단말기에서 사건이 발생했을 경우 각 사건마다 정해놓은 이름으로 시스템 메시지를 발생시킨다.
- 이 메시지와 동일한 이름으로 설정되어 있는 Broad Cast Receiver 를 찾아 개발자가 만든 코드를 동작시킬수 있다.
- 안드로이드 8.0 부터는 사용할 수 있는 시스템 메시지의 수가 줄어들었다,

- https://developer.android.com/guide/components/broadcast-exceptions.html

```kotlin
/*
	MainActivity.kt
*/
package com.example.osreceiver

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    var permission_list = arrayOf(
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.RECEIVE_SMS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()
    }

    fun checkPermission(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return
        }
        for(permission : String in permission_list){
            var chk = checkCallingOrSelfPermission(permission)
            if(chk == PackageManager.PERMISSION_DENIED){
                requestPermissions(permission_list, 0)
                break
            }
        }
    }
}

```

```kotlin
/*
	TestReceiver.kt
*/
package com.example.osreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import android.widget.Toast

class TestReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.

        when(intent.action){
            //  "android.intent.action.BOOT_COMPLETED" -> {
            Intent.ACTION_BOOT_COMPLETED -> {
                var t1 = Toast.makeText(context, "부팅완료", Toast.LENGTH_SHORT)
                t1.show()
            }
            // "android.provider.Telephony.SMS_RECEIVED" -> {
            Telephony.Sms.Intents.SMS_RECEIVED_ACTION -> {
                var str = ""
                var bundle = intent.extras
                if(bundle != null){
                    var obj = bundle.get("pdus") as Array<Any>
                    var msg = arrayOfNulls<SmsMessage>(obj.size)

                    for(i in obj.indices){
                        // 문자 메세지 객체를 만들어서 담는다.
                        msg[i] = SmsMessage.createFromPdu(obj[i] as ByteArray)
                    }
                    for(i in msg.indices){
                        str = msg[i]?.originatingAddress + " : " + msg[i]?.messageBody
                        //              전화번호 : 문자메세지
                        var t2 = Toast.makeText(context, str, Toast.LENGTH_SHORT)
                        t2.show()
                    }
                }

            }
        }
    }
}

```

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.osreceiver">

    <!-- 재부팅 권한 -->
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />

    <!-- 사용자에게 권한 허가 받아야 함 -->
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.RECEIVE_SMS" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <receiver
            android:name=".TestReceiver"
            android:enabled="true"
            android:exported="true">
            <intent-filter>
                <!--    암시적 인텐트지만 OS 에서 정의한 이름은 동작 가능    -->
                <action android:name="android.intent.action.BOOT_COMPLETED" />
            </intent-filter>
            <intent-filter>
                <action android:name="android.provider.Telephony.SMS_RECEIVED" />
            </intent-filter>
        </receiver>

        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

