### Content Provider

- 안드로이드 4대 구성 요소 중 하나로 애플리케이션이 저장한 데이터를 다른 애플리케이션이 사용할 수 있도록 제공하는 개념이다.

  B 라는 데이터가 A 에 접근해서 가져오는 것이 아닌

  A 라는 데이터가 제공한 데이터를 B 가 가져다 쓰는 개념 (OS가 중간에서 Content Provider 에 접근해서 전달해준다.)


#### Permission

contentProvider 를 통해 db를 읽거나 수정하려면 권한이 필요하다.

제공하는 app의 AndroidManifest.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.sqlite1">
    <permission android:name="com.example.sqlite1.READ_DATABASE" android:protectionLevel="normal" />
    <permission android:name="com.example.sqlite1.WRITE_DATABASE" android:protectionLevel="normal" />

    <application
        ...>
        <provider
            android:name=".TestProvider"
            android:authorities="com.example.dbprovider"
            android:readPermission="com.example.sqlite1.READ_DATABASE"
            android:writePermission="com.example.sqlite1.WRITE_DATABASE"
            android:enabled="true"
            android:exported="true" />

        ...
    </application>

</manifest>
```



제공받는 app의 AndroidManifest.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.contentprovider">
    <uses-permission android:name="com.example.sqlite1.READ_DATABASE" />
    <uses-permission android:name="com.example.sqlite1.WRITE_DATABASE" />

    ...
</manifest>
```



#### ContentProvider

SQLite1 파일에서 덮어씀

```kotlin
package com.example.sqlite1

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import java.lang.UnsupportedOperationException

class TestProvider : ContentProvider() {

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        var helper = context?.let { DBHelper(it) }
        var db = helper!!.writableDatabase

        return db.delete("TestTable", selection, selectionArgs)
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        var helper = context?.let { DBHelper(it) }
        var db = helper!!.writableDatabase

        db.insert("TestTable", null, values)

        return uri
    }

    override fun onCreate(): Boolean {
        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        var helper = context?.let { DBHelper(it) }
        var db = helper!!.writableDatabase

        return db.query("TestTable", projection, selection, selectionArgs, null, null, sortOrder)

    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        var helper = context?.let { DBHelper(it) }
        var db = helper!!.writableDatabase

        return db.update("TestTable", values, selection, selectionArgs)
    }
}

```



#### 사용하는 앱

```kotlin
package com.example.contentprovider

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { view ->
            var uri = Uri.parse("content://com.example.dbprovider")

            var c = contentResolver.query(uri, null, null, null, null)

            textView.text = ""

            while(c!!.moveToNext()){
                //현재 row 에서 다음 row 로 가면서 더 이상 없으면 false 반환
                var idx_pos = c.getColumnIndex("idx")
                var textData_pos = c.getColumnIndex("textData")
                var intData_pos = c.getColumnIndex("intData")
                var floatData_pos = c.getColumnIndex("floatData")
                var dateData_pos = c.getColumnIndex("dateData")

                var idx = c.getInt(idx_pos)
                var textData = c.getString(textData_pos)
                var intData = c.getInt(intData_pos)
                var floatData = c.getDouble(floatData_pos)
                var dateData = c.getString(dateData_pos)

                textView.append("idx : ${idx}\n")
                textView.append("textData : ${textData}\n")
                textView.append("intData : ${intData}\n")
                textView.append("floatData : ${floatData}\n")
                textView.append("dateData : ${dateData}\n\n")
            }
//            c.close()
        }
        button2.setOnClickListener { view ->
            var sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            var date = sdf.format(Date())

            var cv1 = ContentValues() // 어떤 컬럼에 어떤 값을 대응하겠다는 것을 지정해주는 클래스
            cv1.put("textData", "문자열2")
            cv1.put("intData", 300)
            cv1.put("floatData", 33.33)
            cv1.put("dateData", date)

            var cv2 = ContentValues()
            cv2.put("textData", "문자열4")
            cv2.put("intData", 400)
            cv2.put("floatData", 44.44)
            cv2.put("dateData", date)

            var uri = Uri.parse("content://com.example.dbprovider")

            contentResolver.insert(uri, cv1)
            contentResolver.insert(uri, cv2)

            textView.text = "저장완료"
        }

        button3.setOnClickListener { view ->
            var cv = ContentValues()
            cv.put("textData", "문자열100")
            var where = "idx=?"
            var args = arrayOf("3") // idx 가 3인 값을 수정

            var uri = Uri.parse("content://com.example.dbprovider")

            contentResolver.update(uri, cv, where, args)

            textView.text = "수정완료"
        }

        button4.setOnClickListener { view ->
            var where = "idx=?"
            var args = arrayOf("3")

            var uri = Uri.parse("content://com.example.dbprovider")

            contentResolver.delete(uri, where, args)

            textView.text = "삭제완료"
        }
    }
}

```



