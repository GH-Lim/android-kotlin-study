### Controller

- 웹, 모바일 등 애플리케이션 개발 시 눈에 보이는 화면들을 관리하는 요소를 Controller라고 부른다.
- 만약 눈에 보이는 모든 화면을 Fragment로 만들어 사용할 경우 Fragment를 관리하는 Activity가 Controller의 역할을 한다.

### Activity의 역할

- 각 Fragment를 교환하고 관리하는 역할을 한다.
- Fragment들이 사용할 데이터를 관리하는 역할을 한다.



```kotlin
/*
	MainActivity.kt
*/
package com.example.activitycontroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    var input_fragment = InputFragment()
    var result_fragment = ResultFragment()

    var value1: String? = null
    var value2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFragment("input")
    }

    fun setFragment(name: String){
        var tran = supportFragmentManager.beginTransaction()
        when(name){
            "input" -> {
                tran.replace(R.id.container, input_fragment)
            }
            "result" -> {
                tran.replace(R.id.container, result_fragment)
                tran.addToBackStack(null)
            }
        }
        tran.commit()
    }
}
```

```kotlin
/*
	InputFragment.kt
*/
package com.example.activitycontroller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

/**
 * A simple [Fragment] subclass.
 */
class InputFragment : Fragment() {

    var button: Button? = null
    var edit1: EditText? = null
    var edit2: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_input, container, false)

        button = view.findViewById<Button>(R.id.button)
        edit1 = view.findViewById<EditText>(R.id.editText)
        edit2 = view.findViewById<EditText>(R.id.editText2)

        button?.setOnClickListener { view ->
            // activity 프로퍼티 => 자기 자신을 관리하고 있는 액티비티의 주소값을 반환
            var main_activity = activity as MainActivity // 코틀린 형변환

            main_activity.value1 = edit1?.text.toString()
            main_activity.value2 = edit2?.text.toString()

            main_activity.setFragment("result")
        }

        return view
    }

}
```

```kotlin
/*
	ResultFragment.kt
*/
package com.example.activitycontroller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

/**
 * A simple [Fragment] subclass.
 */
class ResultFragment : Fragment() {

    var button2: Button? = null
    var textView1: TextView? = null
    var textView2: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_result, container, false)

        button2 = view.findViewById(R.id.button2)
        textView1 = view.findViewById(R.id.textView)
        textView2 = view.findViewById(R.id.textView2)

        var main_actvity = activity as MainActivity

        textView1?.text = main_actvity.value1
        textView2?.text = main_actvity.value2

        button2?.setOnClickListener { view ->
            main_actvity.supportFragmentManager.popBackStack()
        }
        return view
    }

}
```



