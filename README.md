# AMPermission
Use annotation processor to easy use runtime permission for Android M +

## Dependencies

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

## Use

* Request for access

```java
MPermissions.requestPermissions(MainActivity.this, REQUECT_CODE_SDCARD, Manifest.permission.WRITE_EXTERNAL_STORAGE);
```

* Handles the privilege callback

```java
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
	MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
	super.onRequestPermissionsResult(requestCode, permissions, grantResults);
}
```

* Whether the pop-up explanation is needed

```
if (!MPermissions.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUECT_CODE_SDCARD)) {
    MPermissions.requestPermissions(MainActivity.this, REQUECT_CODE_SDCARD, Manifest.permission.WRITE_EXTERNAL_STORAGE);
}
```

If need explanation, it will be automatically used `@ShowRequestPermissionRationale` annotation method .

Authorization grant and denied via use `@PermissionGrant` and `@PermissionDenied` annotations, below example or sample in project for more details.

## Example

* in Activity:

```java
public class MainActivity extends AppCompatActivity {

	private Button mBtnSdcard;
	private static final int REQUECT_CODE_SDCARD = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);

	    mBtnSdcard = (Button) findViewById(R.id.id_btn_sdcard);
	    mBtnSdcard.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            MPermissions.requestPermissions(MainActivity.this, REQUECT_CODE_SDCARD, Manifest.permission.WRITE_EXTERNAL_STORAGE);
	        }
	    });
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
	    MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
	    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}


	@PermissionGrant(REQUECT_CODE_SDCARD)
	public void requestSdcardSuccess() {
	    Toast.makeText(this, "GRANT ACCESS SDCARD!", Toast.LENGTH_SHORT).show();
	}

	@PermissionDenied(REQUECT_CODE_SDCARD)
	public void requestSdcardFailed() {
	    Toast.makeText(this, "DENY ACCESS SDCARD!", Toast.LENGTH_SHORT).show();
	}
}
```

* in Fragment:

```java

public class TestFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.id_btn_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MPermissions.requestPermissions(TestFragment.this, 2, Manifest.permission.WRITE_CONTACTS);
            }
        });

    }

    @PermissionGrant(2)
    public void requestContactSuccess() {
        Toast.makeText(getActivity(), "GRANT ACCESS CONTACTS!", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied(2)
    public void requestContactFailed() {
        Toast.makeText(getActivity(), "DENY ACCESS CONTACTS!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

```

## Confused

```
-dontwarn cc.tonv.android.**
-keep class cc.tonv.android.** {*;}
-keep interface cc.tonv.android.** { *; }
-keep class **$$PermissionProxy { *; }
```

## License
```
Copyright (C)  Tonvchong, AMPermission Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
