# AMPermission
Use annotation processor to easy use runtime permission for Android M +

## 引入

project's build.gradle

```
buildscript {
    dependencies {
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.4'
    }
}
```

module's buid.gradle

```
apply plugin: 'com.neenbedankt.android-apt'

dependencies {
    apt 'cc.tonv:permission-compiler:1.0.2'
    compile 'cc.tonv:permission-core:1.0.2'
}
```

## 使用

* 申请权限

```java
MPermissions.requestPermissions(MainActivity.this, REQUECT_CODE_SDCARD, Manifest.permission.WRITE_EXTERNAL_STORAGE);
```

* 处理权限回调

```java
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
{
	MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
	super.onRequestPermissionsResult(requestCode, permissions, grantResults);
}
```

* 是否需要弹出解释

```
if (!MPermissions.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUECT_CODE_SDCARD))
{
    MPermissions.requestPermissions(MainActivity.this, REQUECT_CODE_SDCARD, Manifest.permission.WRITE_EXTERNAL_STORAGE);
}
```

如果需要解释，会自动执行使用`@ShowRequestPermissionRationale`注解的方法。

授权成功以及失败调用的分支方法通过注解`@PermissionGrant`和`@PermissionDenied`进行标识，详细参考下面的例子或者sample。

## 例子

* in Activity:

```java
public class MainActivity extends AppCompatActivity
{

	private Button mBtnSdcard;
	private static final int REQUECT_CODE_SDCARD = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);

	    mBtnSdcard = (Button) findViewById(R.id.id_btn_sdcard);
	    mBtnSdcard.setOnClickListener(new View.OnClickListener()
	    {
	        @Override
	        public void onClick(View v)
	        {
	            MPermissions.requestPermissions(MainActivity.this, REQUECT_CODE_SDCARD, Manifest.permission.WRITE_EXTERNAL_STORAGE);
	        }
	    });
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
	    MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
	    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}


	@PermissionGrant(REQUECT_CODE_SDCARD)
	public void requestSdcardSuccess()
	{
	    Toast.makeText(this, "GRANT ACCESS SDCARD!", Toast.LENGTH_SHORT).show();
	}

	@PermissionDenied(REQUECT_CODE_SDCARD)
	public void requestSdcardFailed()
	{
	    Toast.makeText(this, "DENY ACCESS SDCARD!", Toast.LENGTH_SHORT).show();
	}
}
```

* in Fragment:

```java

public class TestFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        view.findViewById(R.id.id_btn_contact).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MPermissions.requestPermissions(TestFragment.this, 4, Manifest.permission.WRITE_CONTACTS);
            }
        });

    }

    @PermissionGrant(4)
    public void requestContactSuccess()
    {
        Toast.makeText(getActivity(), "GRANT ACCESS CONTACTS!", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied(4)
    public void requestContactFailed()
    {
        Toast.makeText(getActivity(), "DENY ACCESS CONTACTS!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

```

## 混淆

```
-dontwarn cc.tonv.android.**
-keep class cc.tonv.android.** {*;}
-keep interface cc.tonv.android.** { *; }
-keep class **$$PermissionProxy { *; }