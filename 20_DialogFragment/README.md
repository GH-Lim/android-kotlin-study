### DialogFragment

- AlertDialog를 Fragment로 만들어서 사용할 수 있도록 제공되는 Fragment이다.
- AlertDialog와 큰 차이는 없다.
- 현업에서 애플리케이션을 개발하다 보면 AlertDialog보다 DialogFragment를 사용하는 것이 편할 때가 종종 있다.



`MainActivity.kt`

```kotlin
package com.example.dialogfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            var dialog = TestDialogFragment()
            dialog.show(supportFragmentManager, "tag") // tag: 대표하는 이름.

        }
    }
}

```

`TestDialogFragment.kt`

```kotlin
package com.example.dialogfragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A simple [Fragment] subclass.
 */
class TestDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var builder = AlertDialog.Builder(activity)
        builder.setTitle("타이틀 입니다.")
        builder.setMessage("메시지 입니다.")

        var listenr = DialogListener()

        builder.setPositiveButton("positive", listenr)
        builder.setNeutralButton("neutral", listenr)
        builder.setNegativeButton("negative", listenr)

        var alert = builder.create()

        return alert
    }

    inner class DialogListener: DialogInterface.OnClickListener{
        override fun onClick(dialog: DialogInterface?, which: Int) {
            var main_activity = activity as MainActivity

            when(which){
                DialogInterface.BUTTON_POSITIVE -> {
                    main_activity.textView.text = "positive"
                }
                DialogInterface.BUTTON_NEUTRAL -> {
                    main_activity.textView.text = "nuetral"
                }
                DialogInterface.BUTTON_NEGATIVE -> {
                    main_activity.textView.text = "negative"
                }
            }
        }
    }

}

```

