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
