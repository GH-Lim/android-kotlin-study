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
