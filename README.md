# PermissionCheck-Android
This library is used to ask permission from user in short way. This project has been written in Kotlin language.

## Using `build.gradle`

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
	implementation 'com.github.HarshilKakadiya:PermissionCheck-Android:1.1'
}
```


## Using `maven`

Step 1 :

```groovy
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>
```

Step 2 : Add dependency

```groovy
<dependency>
	<groupId>com.github.HarshilKakadiya</groupId>
	<artifactId>PermissionCheck-Android</artifactId>
	<version>1.0</version>
</dependency>
```

## Usage

Full example?, please refer to `app` module


### To start for ask permission

## Kotlin

```java
PermissionCheck.with(this@MainActivity)
            .setPermissions(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO
            ) // You can pass N number of permission
            .skipDenyAndForceDeny() // By adding skipDenyAndForceDeny you can allow user to access further process without accepting permission
            .onAccepted { list -> }
            .onDenied { list -> }
            .onForeverDenied { list -> }
            .ask() // It is use to start the process of permission
```


## Java

```java
PermissionCheck.with(MainActivity.this)
                .setPermissions(
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.RECORD_AUDIO
                ) // You can pass N number of permission
                .skipDenyAndForceDeny() // By adding skipDenyAndForceDeny you can allow user to access further process without accepting permission
                .onAccepted(new PermissionStatus() {
                    @Override
                    public void onResult(@Nullable List<String> list) {
                    }
                })
                .onDenied(new PermissionStatus() {
                    @Override
                    public void onResult(@Nullable List<String> list) {
                    }
                })
                .onForeverDenied(new PermissionStatus() {
                    @Override
                    public void onResult(@Nullable List<String> list) {
                    }
                })
                .ask(); // It is use to start the process of permission
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

