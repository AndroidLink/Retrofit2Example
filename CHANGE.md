# Upgrade
===============

## 1. Android Studio, in root build.gradle
from

    classpath 'com.android.tools.build:gradle:1.1.0'

#### to

    classpath 'com.android.tools.build:gradle:2.3.0'

## 2. gradle, in gradle-wrapper.properties
from

    distributionUrl=https\://services.gradle.org/distributions/gradle-2.2.1-all.zip

#### to
    distributionUrl=https\://services.gradle.org/distributions/gradle-3.3-all.zip

## 3. Android Support Library in app/build.gradle

from

    android {
        compileSdkVersion 22
        buildToolsVersion "22.0.1"

#### to
    android {
        compileSdkVersion 25
        buildToolsVersion "25.0.2"

from

    defaultConfig {
        targetSdkVersion 22

#### to
    defaultConfig {
        targetSdkVersion 25

## 4. [Retrofit library](http://square.github.io/retrofit/) and its dependencies

## 5. Discard crouton and replace Crouton with Toast

#### in app/build.gradle from
    compile 'de.keyboardsurfer.android.widget:crouton:1.8.5'
#### to
    //compile 'de.keyboardsurfer.android.widget:crouton:1.8.5'

#### in MainActivity.Java from
    Crouton.makeText(MainActivity.this, error.getMessage(), Style.ALERT).show();
#### to
    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();


## 6. Butterknife upgrade to 8.5.1

#### in app/build.gradle from

    compile 'com.jakewharton:butterknife:6.1.0'

#### to
    compile 'com.jakewharton:butterknife:8.5.1'
    annotationProcessor "com.jakewharton:butterknife-compiler:8.5.1"

#### in MainActivity.java from
    replace @InjectView with @BindView
    replace Butterknife.inject with Butterknife.bind

#### to


## 7. Others dependencies in build.gradle

#### parcler from

    compile 'org.parceler:parceler:0.2.16'

#### to
    compile 'org.parceler:parceler-api:1.1.6'
    annotationProcessor 'org.parceler:parceler:1.1.6'
