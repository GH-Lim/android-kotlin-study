### 안드로이드 4대 구성요소

하나의 애플리케이션은 독립된 실행단위인 4대 구성요소로 구성된다.

- `Activity` : 화면을 관리하는 실행단위

  눈에 보이는 화면에 있는 ui 요소들을 통제하고 관리

- `Service` : 백그라운드에서 실행되는 실행단위

  액티비티와 비슷하지만 서비스는 관리할 화면 x

- `Broad Cast Receiver` : OS가 전달하는 메시지를 전달 받아 실행되는 실행단위

  시스템 발생 이벤트 감시하고 처리 (ex. low battery)

- `Contents Provider` :  저장된 데이터를 제공하기 위한 실행 단위



### Intent

- 안드로이드의 4대 구성 요소를 샐행하기 위해서는 Intent라는 객체가 필요하다.
- 안드로이드의 4대 구성 요소를 실행하기 위해서는 개발자가 직접 실행하는 것이 아니라 OS에게실행을 요청하게 된다.
- 실행 요청을 전달 받은 OS는 해당 실행 요소를 실행하게 되는데 실행요청을 위한 정보를 담는 객체가 Intent 이다.

### Activity 실행 및 종료

- `startActivity` : 지정된 Intent의 담긴 정보를 토대로 Activity를 실행한다.
- `finish` : 현재 실행되어 있는 Activity를 종료한다.



### Back Stack

- Activity에서 다른 Activity를 실행하면 이전 Activity는 BackStack에 담겨 정지 상태가 되고 새로 실행된 Activty가 활동하게 된다.
- 새로 실행된 Activity가 제거 되면 Back Stack에 있던 Activity가 다시 활동하게 된다.



#### AndroidManifest.xml

안드로이드 OS에게 4대 구성요소가 어떤 것이 있는지 알려주는 파일.



```kotlin
package com.example.runactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            // 액티비티를 실행할 때는 intent 객체를 만들어야함
            var intent = Intent(this, SecondActivity::class.java)
            // Java 에서는 SecondActivity.java
            startActivity(intent)
        }

        button3.setOnClickListener { view ->
            finish() // 백스테이트에 아무것도 없다면 앱 종료
        }
    }
}

```

```kotlin
package com.example.runactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        button2.setOnClickListener { view ->
            finish()
        }
    }
}

```

