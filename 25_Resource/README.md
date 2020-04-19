### 리소스 관리

- 안드로이드는 애플리케이션에서 사용하는 다양한 값을 코드에 직접 작성하지 않고 xml을 통해 관리할 수 있도록 제공된다.
- xml을 통해 사용할 수 있기 때문에 단말기의 상태(크기, 해상도, 언어 등)에 따라 다양하게 대응할 수 있고 유지 보수를 용이하게 할 수 있다.



### 문자열 관리

- 문자열, 문자열 배열을 xml에 등록해서 사용할 수 있다.
- 다국어 지원을 위해 사용한다.

문자열

```xml
<resources>
    <string name="str1">안녕하세요</string>
    <string name="str2">반갑습니다</string>
</resources>
```

```kotlin
button.setOnClickListener { view ->
	// var str1 = resources.getString(R.string.str2);
	// textView.text = str1
	textView.setText(R.string.str2) // 텍스트 뷰는 많이 쓰는 거라 제공.
}
```



문자열 배열

```xml
<resources>
    <string-array name="data_array">
        <item>항목1</item>
        <item>항목2</item>
        <item>항목3</item>
        <item>항목4</item>
        <item>항목5</item>
        <item>항목6</item>
        <item>항목7</item>
        <item>항목8</item>
        <item>항목9</item>
        <item>항목10</item>
    </string-array>
</resources>
```

```kotlin
button2.setOnClickListener { view ->
	textView.text = ""
	var str_array = resources.getStringArray(R.array.data_array)
	for (str1 : String in str_array){
		textView.append("${str1}\n")
	}
}
```



### 색상 관리

- 색상을 xml에 등록해서 사용할 수 있다.
- 안드로이드는 단말기 제조사가 자신의 단말기에 안드로이드 OS를 넣게 된다.
- 이 때, 단말기마다 지원하는 색상, 처리 방식 등이 달라 우너하는 색상이 나오지 않을 수도 있는데 이 처리를 안드로이드 OS가 해줄 수 있게 된다.

```xml
<resources>
    <color name="color1">#F00</color>
    <color name="color2">#5F00</color>
    <color name="color3">#00FF00</color>
    <color name="color4">#5500FF00</color>
</resources>
```

```kotlin
button3.setOnClickListener { view ->
	// textView.setTextColor(Color.YELLOW)
	// var color = Color.rgb(26, 106, 129)
	// var color = Color.argb(50, 26,106,129)
	var color = ContextCompat.getColor(this, R.color.color1)
	textView.setTextColor(color)
}
```



### 크기 관리

- 크기를 xml에 등록해서 사용할 수 있다.
- 모든 디스플레이 장비는 px 이라는 단위로 크기 등을 결정하게 된다.
- 안드로이드는 다양한 단말기 때문에 px을 사용하면 크기가 다르게 나타날 수 있다.
- 안드로이드는 가변형 단위들을 제공한다.

#### 단위

- px: 실제 사용할 픽셀의 개수

- dp: 160ppi 액정에서 1dp = 1px

- sp: 단말기에 설정되어 있는 글자 크기에 따라 가변

  ​		기본 크기에서 160ppi 액정에서 1sp = 1px

- mm: 밀리미터

- in: 인치

- pt: 1pt = 1/72인치

```xml
<resources>
    <dimen name="px">1px</dimen>
    <dimen name="inch">1in</dimen>
    <dimen name="mm">1mm</dimen>
    <dimen name="pt">1pt</dimen>
    <dimen name="dp">1dp</dimen>
    <dimen name="sp">1sp</dimen>
</resources>
```

```kotlin
button4.setOnClickListener { view ->
	var px = resources.getDimension(R.dimen.px)
    var dp = resources.getDimension(R.dimen.dp)
    var sp = resources.getDimension(R.dimen.sp)
    var inch = resources.getDimension(R.dimen.inch)
    var mm = resources.getDimension(R.dimen.mm)
    var pt = resources.getDimension(R.dimen.pt)

    textView.text = "px : ${px}\n"
    textView.append("dp : ${dp}\n")
    textView.append("sp : ${sp}\n")
    textView.append("inch : ${inch}\n")
    textView.append("mm : ${mm}\n")
    textView.append("pt : ${pt}\n")

    // textView.setTextSize(20 * dp)
}
```

