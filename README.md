### 概况

- 展示分享弹窗（微信，朋友圈，QQ，QQZone）
- 支持自定义 点击事件
- 支持隐藏指定分享
- 支持截屏分享

### 使用

- 在AndroidManifest.xml中 添加QQID和微信ID

```xml
<meta-data android:name="l_qq_id" android:value="112123213" />
<meta-data android:name="l_wx_id" android:value="112123213" />

<!-- QQ -->
<activity 
   android:name="com.tencent.tauth.AuthActivity"
   android:launchMode="singleTask"
   android:noHistory="true">
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <data android:scheme="tencent1106752618" />
    </intent-filter>
</activity>
<activity 
   android:name="com.tencent.connect.common.AssistActivity"
   android:exported="false"
   android:screenOrientation="portrait"
   android:theme="@android:style/Theme.Translucent.NoTitleBar" />

<!-- 微信页面 -->
 <activity
    android:name=".wxapi.WXPayEntryActivity"
    android:exported="true"
    android:launchMode="singleTop"
    android:screenOrientation="portrait"
    android:theme="@android:style/Theme.Translucent" />
<activity
    android:name=".wxapi.WXEntryActivity"
    android:exported="true"
    android:launchMode="singleTask"
    android:screenOrientation="portrait"
    android:taskAffinity="com.android.weici.senior.student"
    android:theme="@android:style/Theme.Translucent" />

```

- 添加权限

```xml
<uses-permission android:name="android.permission.INTERNET" />

<!-- for mta statistics, not necessary-->
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>

<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

- 使用

1. 分享网页

```java

LShareDialog shareDialog = new LShareDialog(activity);
WebShareData textShareData = new WebShareData();
textShareData.title = "Hello";
textShareData.description = "这是一个测试";
textShareData.url = "http://www.baidu.com";
shareDialog.setShareData(textShareData);
shareDialog.show();
```

2. 分享图片

```java
LShareDialog shareDialog = new LShareDialog(activity);
BitmapShareData bitmapShareData = new BitmapShareData(bitmap);
shareDialog.setShareData(bitmapShareData);
shareDialog.show();
```

3. 截屏图片分享

```java
LShareDialog shareDialog = new LShareDialog(activity);
shareDialog.setViewCacheShareData(view);
shareDialog.show();
```

- 混淆

```xml
-keep class com.tencent.mm.opensdk.** { *;}
-keep class com.tencent.wxop.** {*;}
-keep class com.tencent.mm.sdk.** {*;}
```



### 注意事项

- 页面继承 ShareActivity/ShareHomeActivity 或者 重写方法(主要用于QQ分享回调)

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      new QQShareHelper(this).onActivityResult(requestCode, resultCode, data);
}
```

