### Broad Cast Receiver

사건마다 이름이 정해져 있다.

### 명시적 인텐트

- 안드로이드의 4대 구성요소 중 하나를 동자 시키기 위해 동작 시키고자 하는 구성 요소의 클래스명을 직접 기술하여 동작시키는 것을 의미한다.
- 한번에 하나만 실행이 가능하다.



### 암시적 인텐트

- 안드로이드 4대 구성 요소 중 원하는 구성요소를 실행하기 위해 Intent Filter 를 통해 설정한 이름을 이용한 것을 의미한다.
- 동일명의 이름이 여러 개 있을 경우에는 Activity의 경우에는 선택, 그 외의 경우에는 모두 실행된다.



### 안드로이드 8.0 이후 제약사항

- 안드로이드는 백그라운드 실행이 자유롭다는 점에서 개발의 자율성을 가지지만 높은 하드웨어 사양을 요구한다는 단점을 가지고 있다.
- 이에 안드로이드 8.0 이후에는 일부를 제외한 모든 Broad Cast Receiver는 암시적 인텐트로 실행이 불가해졌다.



#### BRApp1

```kotlin
package com.example.brapp1

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var brapp1:TestReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addReceiver()

        button.setOnClickListener { view ->
            var intent = Intent(this, TestReceiver::class.java)
            sendBroadcast(intent)
        }
    }

    fun addReceiver(){
        // 암시적을 쓰려면 반드시 코드를 통해서 인텐트를 등록했다가 제거해야함
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            return
        }
        brapp1 = TestReceiver()
        var filter = IntentFilter("com.test.brapp1")
        registerReceiver(brapp1, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        if(brapp1 != null){
            unregisterReceiver(brapp1)
            brapp1 = null
        }
    }
}

```

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.brapp1">

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
                <action android:name="com.test.brapp1" />
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

```kotlin
package com.example.brapp1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class TestReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        // 브로드캐스트리시버는 화면을 갖고 있지 않기 때문에 notification 이나 toast 로 처리
        var data1 = intent.getIntExtra("data1", 0)
        var data2 = intent.getDoubleExtra("data2", 0.0)
        var str = "data1 : ${data1}\ndata2 : ${data2}"
        var t1 = Toast.makeText(context, str, Toast.LENGTH_SHORT)
        t1.show()
    }
}
```

#### BRApp2

```kotlin
package com.example.brapp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
//            var intent = Intent()
//            intent.setClassName("com.example.brapp1", "com.example.brapp1.TestReceiver")  // 명시적
            // 패키지 이름, 그 패키지의 클래스 이름
            var intent = Intent("com.test.brapp1")  // 암시적
            // 같은 이름을 모두 실행한다.
            intent.putExtra("data1", 100)
            intent.putExtra("data2", 11.11)
            sendBroadcast(intent)
        }
    }
}

```



