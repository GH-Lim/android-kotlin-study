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
