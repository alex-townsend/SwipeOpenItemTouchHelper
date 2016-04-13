SwipeOpenItemTouchHelper
========================
Library to easily add swipe-to-open functionality to any RecyclerView.

How To
------
See [sample](https://github.com/alex-townsend/SwipeOpenItemTouchHelper/tree/master/sample) for examples on how to implement on vertical or horizontal `RecyclerView`.


Build
-----

Add through Gradle:

- Add jitpack to your project's root `build.gradle` 
```Groovy
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```
- Add the dependency
```Groovy
compile 'com.github.alex-townsend:SwipeOpenItemTouchHelper:0.1.0'
```

Or, add through Maven:

- Add jitack repository
```xml
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```
- Add dependency
```xml
<dependency>
    <groupId>com.github.alex-townsend</groupId>
    <artifactId>SwipeOpenItemTouchHelper</artifactId>
    <version>0.1.0</version>
</dependency>
```

License
-------

    Copyright 2016 Alex Townsend

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


License for ItemTouchHelper (that this library was adapted from)
-------

    Copyright 2015 The Android Open Source Project
    https://github.com/android/platform_frameworks_support/blob/master/v7/recyclerview/src/android/support/v7/widget/helper/ItemTouchHelper.java

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
