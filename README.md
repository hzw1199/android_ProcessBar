# Process Bar
[![](https://jitpack.io/v/hzw1199/android_ProcessBar.svg)](https://jitpack.io/#hzw1199/android_ProcessBar)

[中文看这里](/READMEcn.md)  

![](/media/anglerMOB30Rwuzongheng08212016140333.gif)

# Download
Add it in your build.gradle at the end of repositories:  

```
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```

Add the dependency in the form:  

```
dependencies {
    compile 'com.github.hzw1199:android_ProcessBar:v1.1'
}
```
# Usage

* work with ViewPager  

```java
processBar.setupWithViewPager(viewPager);
```
* support xml attributes  

```xml
<declare-styleable name="ProcessBar">
   <attr name="processbar_titles" format="reference"></attr>
   <attr name="processbar_background" format="integer"></attr>
   <attr name="processbar_selected_color" format="color"></attr>
   <attr name="processbar_current_drawable" format="integer"></attr>
   <attr name="processbar_selected_text_size" format="dimension"></attr>
   <attr name="processbar_unselected_text_size" format="dimension"></attr>
</declare-styleable>
```
 Example：

```xml
<com.adam.processbar.ProcessBar
   android:id="@+id/processbar"
   android:layout_width="match_parent"
   android:layout_height="48dp"
   app:processbar_titles="@array/process_bar_elements"
   app:processbar_background="@drawable/shape_plan_tab_background"
   app:processbar_selected_color="@color/processColor"
   app:processbar_current_drawable="@drawable/plan_arrow"
   app:processbar_selected_text_size="14sp"
   app:processbar_unselected_text_size="14sp"/>
```

* If this project helps you, please star me.  

