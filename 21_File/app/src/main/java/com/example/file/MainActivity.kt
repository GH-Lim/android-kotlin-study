package com.example.file

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import kotlin.Exception

class MainActivity : AppCompatActivity() {

    var permission_list = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    var path : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        path = Environment.getExternalStorageDirectory().absolutePath + "/android/data/" + packageName
        checkPermission()

        var file = File(path)
        if(file.exists() == false){
            file.mkdir()
        }

        button.setOnClickListener { view ->
            try{
                var output = openFileOutput("myFile.dat", Context.MODE_PRIVATE)
                var dos = DataOutputStream(output)
                dos.writeInt(100)
                dos.writeDouble(33.33)
                dos.writeUTF("안녕하세요")
                dos.flush()
                dos.close()
                textView.text = "저장완료"
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        button2.setOnClickListener { view ->
            try{
                var input = openFileInput("myFile.dat")
                var dis = DataInputStream(input)
                var value1 = dis.readInt()
                var value2 = dis.readDouble()
                var value3 = dis.readUTF()
                dis.close()

                textView.text = "value1 : ${value1}\n"
                textView.append("value2 : ${value2}\n")
                textView.append("value3 : ${value3}\n")
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        button3.setOnClickListener { view ->
            try{
                var output = FileOutputStream("$path/sdFile.dat")
                var dos = DataOutputStream(output)
                dos.writeInt(200)
                dos.writeDouble(55.55)
                dos.writeUTF("반갑습니다.")
                dos.flush()
                dos.close()
                textView.text = "저장완료2"
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        button4.setOnClickListener { view ->
            try{
                var input = FileInputStream("$path/sdFile.dat")
                var dis = DataInputStream(input)
                var value1 = dis.readInt()
                var value2 = dis.readDouble()
                var value3 = dis.readUTF()
                dis.close()
                
                textView.text = "value1 : ${value1}\n"
                textView.append("value2 : ${value2}\n")
                textView.append("value3 : ${value3}\n")
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun checkPermission(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return
        }
        for(permission : String in permission_list){
            var chk = checkCallingOrSelfPermission(permission)
            if(chk == PackageManager.PERMISSION_DENIED){
                requestPermissions(permission_list, 0)
                break
            }
        }
    }
}
