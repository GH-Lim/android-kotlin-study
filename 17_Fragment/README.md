### Fragment

- 여러 화면을 가지고 있는 애플리케이션을 여러 Activity를 가지고 있는 애플리케이션을 의미한다.

- Activity는 독립된 실행단위로 메모리를 많이 소모하는데 독립된 실행단위가 아닌 화면만 필요한 경우 Activity보단 Fragment를 사용하는 것이 효율적이다.

- Fragment는 Activity 내의 작은 화면의 조각으로 Activity의 화면을 여러 영역으로 나누어 관리하고자 하는 목적으로 사용한다.

  web개발의 div 태그와 비슷한 개념

- Fragment는 안드로이드 5.0에서 추가되었지만 하위 버전에서도 사용할 수 있도록 설계되어 있다.

- add : Fragment를 지정된 레이아웃에 추가한다.

- replace : 지정된 레이아웃에 설정되어 있는 Fragment를 제거하고 새로운 Fragment를 추가한다.



### AddToBackStack

- 안드로이드에서 BackButton은 현재 액티비티를 종료한다.
- Fragment는 Activity가 아니므로 Back Button으로 제거할 수 없는데 AddToBackStack 메서드를 통해 Back stack에 포함할 경우 Back Button으로 제거할 수 있다.
- 이를 통해 마치 이전 화면으로 돌아가는 듯한 효과를 줄 수 있다.



Fragment는 Activity 내의 작은 화면 조각이다.

안드로이드는 현재 Activity 보단 Fragment 를 사용하는 것을 권장한다.



```kotlin
package com.example.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var first = FirstFragment()
    var second = SecondFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button6.setOnClickListener { view ->
            // 프래그먼트를 띄울 때는 프래그먼트 매니저를 이용
            // 안드로이드 5.0 이하 버전에서도 하려면 서포트 프래그먼트 매니저를 사용
            var tran = supportFragmentManager.beginTransaction()
            // tran.add(R.id.container, first) // 레이아웃 id, 매치할 프래그먼트
            tran.replace(R.id.container, first)
            tran.addToBackStack(null) // 문자열로 이름 아무거나 넣거나 null 값
            tran.commit()
        }

        button7.setOnClickListener { view ->
            var tran = supportFragmentManager.beginTransaction()
            // tran.add(R.id.container, second) // 레이아웃 id, 매치할 프래그먼트
            tran.replace(R.id.container, second)
            tran.addToBackStack(null)
            tran.commit()
        }
    }
}

```

