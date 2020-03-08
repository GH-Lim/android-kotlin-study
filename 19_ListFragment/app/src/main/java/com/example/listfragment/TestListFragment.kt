package com.example.listfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.ListFragment

/**
 * A simple [Fragment] subclass.
 */
class TestListFragment : ListFragment() {

    var textView: TextView? = null
    var list = arrayOf("항목1", "항목2", "항목3", "항목4", "항목5")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_test_list, container, false)

        textView = view.findViewById(R.id.textView)

        // 강의에선 이렇게 했는데 안됨..
//        var adapter = ArrayAdapter<String>(
//            activity, android.R.layout.simple_list_item1, list
//        )

        var adapter = activity?.let {
            ArrayAdapter<String>(
                it, android.R.layout.simple_list_item_1, list
            )
        }

        listAdapter = adapter

        return view
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)

        var str = list[position]
        textView?.text = str
    }

}
