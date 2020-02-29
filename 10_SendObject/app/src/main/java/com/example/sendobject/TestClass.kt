package com.example.sendobject

import android.os.Parcel
import android.os.Parcelable

class TestClass : Parcelable{  // Parcel 타입의 객체,

    var data10 : Int = 0
    var data20 : String? = null

    // Kotlin 에서는 static 타입의 객체 참조 변수를 선언할 때는 companion object 라는 블럭 사용
    companion object {
        // jvm 에서 직접 사용할 때는
        @JvmField

        val CREATOR : Parcelable.Creator<TestClass> = object  : Parcelable.Creator<TestClass>{
            override fun createFromParcel(source: Parcel?): TestClass {
                // 객체를 복원하는 메서드
                val test = TestClass()  // 객체 하나만 집어넣어서 넘겼을 때는 이 메서드 호출
                test.data10 = source?.readInt()!!
                test.data20 = source.readString()

                return  test
            }

            override fun newArray(size: Int): Array<TestClass?> {  // 객체 하나가 아니라 배열을 담아서 넘겼을 때는
                // 이 메서드를 호출해서 배열이 먼저 만들어지고 각각의 멤버를 복원하기 위해서 이 매서드가 호출
                return arrayOfNulls<TestClass>(size) // 배열의 갯수
            }
        }
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(data10)
        dest?.writeString(data20) // 나중에 가져올 때 이 순서대로 가져와야 한다.
    }

    override fun describeContents(): Int {
        return 0
    }
}