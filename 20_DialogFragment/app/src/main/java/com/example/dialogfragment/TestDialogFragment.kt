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
