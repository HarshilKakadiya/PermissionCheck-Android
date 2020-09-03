# PermissionCheck-Android
This library is used to ask permission from user in short way. This project has been written in Kotlin language.


Add this to your project's `build.gradle`

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

And add this to your module's `build.gradle` 

```groovy
dependencies {
	implementation 'com.github.HarshilKakadiya:PermissionCheck-Android:1.0'
}
```


## Usage

Full example?, please refer to `app` module


### To start for ask permission

### Kotlin

```java
PermissionCheck.with(this@MainActivity)
            //Pass all the permission which you want to access here
            .setPermissions(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO
            )
            // If you don't want to check user has given permission or not then add this
            // otherwise don't add. Default it will check user has granted permission or not
            .skipDenyAndForceDeny()
            // when user accept all permission
            // it return which permission is granted by user
            .onAccepted {
                Toast.makeText(this@MainActivity, "Set all the permission", Toast.LENGTH_SHORT).show()
                Log.d(javaClass.simpleName, "onAccepted: $it ")
            }
            // when user deny for any one permission among all
            // it return which permission is denied by user
            .onDenied {
                Toast.makeText(this@MainActivity,"You just denied for the permission",Toast.LENGTH_SHORT).show()
                Log.d(javaClass.simpleName, "onDenied: $it")
            }
            // when user force deny for one permission among all
            // it will return which permission is force denied by user
            .onForeverDenied {
                Toast.makeText(this@MainActivity,"You just force denied for the permission",Toast.LENGTH_SHORT).show()
                Log.d(javaClass.simpleName, "onForeverDenied: $it")
            }
            // Don't forgot to call ask() for asking permission
            .ask()
```


### Java

```java
PermissionCheck.with(MainActivity.this)
                //Pass all the permission which you want to access here
                .setPermissions(
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.RECORD_AUDIO
                )
                // If you don't want to check user has given permission or not then add this
                // otherwise don't add. Default it will check user has granted permission or not
                .skipDenyAndForceDeny()
                // when user accept all permission
                // it return which permission is granted by user
                .onAccepted(new PermissionStatus() {
                    @Override
                    public void onResult(@Nullable List<String> list) {
                        Toast.makeText(MainActivity.this, "Set all the permission", Toast.LENGTH_SHORT).show();
                        Log.d(getClass().getSimpleName(), "onAccepted: " + list);
                    }
                })
                // when user deny for any one permission among all
                // it return which permission is denied by user
                .onDenied(new PermissionStatus() {
                    @Override
                    public void onResult(@Nullable List<String> list) {
                        Toast.makeText(MainActivity.this, "You just denied for the permission", Toast.LENGTH_SHORT).show();
                        Log.d(getClass().getSimpleName(), "onDenied: " + list);
                    }
                })
                // when user force deny for one permission among all
                // it will return which permission is force denied by user
                .onForeverDenied(new PermissionStatus() {
                    @Override
                    public void onResult(@Nullable List<String> list) {
                        Toast.makeText(MainActivity.this, "You just force denied for the permission", Toast.LENGTH_SHORT).show();
                        Log.d(getClass().getSimpleName(), "onForeverDenied: " + list);
                    }
                })
                // Don't forgot to call ask() for asking permission
                .ask();
```



License
--------
Copyright 2020

Licensed under the Apache License, Version 2.0 (the "License") and GNU General Public License v2.0;

you may not use this file except in compliance with the Licenses.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0 and https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

