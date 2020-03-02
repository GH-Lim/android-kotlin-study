## 다른 애플리케이션의 액티비티 실행하기

### Intent Filter

- 안드로이드의 4대 구성요소는 모두 AndroidManifest.xml 파일에 기록되어야 한다.
- 이 때 다른 애플리케이션이 실행할 수 있도록 하고자 한다면 Intent Filter를 이용해 이름을 설정해주면 된다.



#### 같은 어플의 다른 액티비티 실행과 거의 비슷하다!

```kotlin
/*
	ActivityApp1\MainActivity.kt
*/
package com.example.acitivityapp1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val SECOND_ACTIVITY = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            var intent = Intent("com.test.second")
            // 안드로이드 OS 는 자기 단말기에 설치되어있는 어플들의
            // 모든 4대 구성 요소들을 목록화해서 가지고 있다.
            // 같은 이름이 여러 개라면 고르는 창이 뜬다.

            intent.putExtra("data1", 100)
            intent.putExtra("data2", 11.11)

            //startActivity(intent)
            startActivityForResult(intent, SECOND_ACTIVITY)
            // 안드로이드 4대 구성요소들은 각각 독립된 실행단위이기 때문에
            // 다른 어플들이 가지고 있는 액티비티를 실행할 수 있다.
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            var value1 = data?.getIntExtra("value1", 0)
            var value2 = data?.getDoubleExtra("value2", 0.0)
            textView.text = "value1 : ${value1}\n"
            textView.append("value2 : ${value2}")
        }
    }
}

```

```kotlin
/*
	ActivityApp2\SecondActivity.kt
*/
package com.example.activityapp2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var data1 = intent.getIntExtra("data1", 0)
        var data2 = intent.getDoubleExtra("data2", 0.0)
        textView.text = "data1 : ${data1}\n"
        textView.append("data2 : ${data2}")

        button.setOnClickListener { view ->
            var intent2 = Intent()
            intent2.putExtra("value1", 200)
            intent2.putExtra("value2", 22.22)
            setResult(Activity.RESULT_OK, intent2)
            finish()
        }
    }
}

```

```xml
<!-- 
	ActivityApp2\AndroidManifest.xml
-->
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.activityapp2">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        
    <!-- #### Intent Filter에 name 등록 #### -->
        <activity android:name=".SecondActivity">
            <intent-filter>
                <action android:name="com.test.second" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </activity>
    <!-- ################################### -->
        
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

```xml
<!-- 
	ActivityApp3\AndroidManifest.xml
-->
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.activityapp3">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
    <!-- #### Intent Filter에 name을 같은 이름으로 등록 #### -->
        <activity android:name=".TestActivity">
            <intent-filter>
                <action android:name="com.test.second" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </activity>
    <!-- => 만약 같은 이름이 여러개라면 어떤 액티비티를 실행할 것인지 물어본다! -->
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

