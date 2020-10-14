- 在AndroidManifest.xml中 添加QQID和微信ID

```
<meta-data
    android:name="l_qq_id"
    android:value="112123213" />

<meta-data
    android:name="l_qq_id"
    android:value="112123213" />

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

```