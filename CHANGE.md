# Upgrade v1 - update for use retrofit 1.x
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

#### the 'appid' for api to openweathermap.org becomes a MUST param
    sign in https://home.openweathermap.org/
    under 'API keys' create one and copy to project for later using.
    a few minutes later, verify the api request via browser, replace <APP_ID> with yours
    http://api.openweathermap.org//data/2.5/weather?q=London&appid=<APP_ID>
    the sucessful response looks like this
    {"coord":{"lon":-0.13,"lat":51.51},"weather":[{"id":721,"main":"Haze","description":"haze","icon":"50n"},{"id":701,"main":"Mist","description":"mist","icon":"50n"},{"id":741,"main":"Fog","description":"fog","icon":"50n"}],"base":"stations","main":{"temp":278.75,"pressure":1023,"humidity":93,"temp_min":277.15,"temp_max":280.15},"visibility":3200,"wind":{"speed":3.6,"deg":60},"clouds":{"all":90},"dt":1490584800,"sys":{"type":1,"id":5091,"message":0.0123,"country":"GB","sunrise":1490593597,"sunset":1490639154},"id":2643743,"name":"London","cod":200}

#### in WeatherService.Java from
    void getWeather(@Query("q") String strCity, Callback<ApiResponse> callback);
#### to
    void getWeather(@Query("q") String strCity, @Query("appid") String appid, Callback<ApiResponse> callback);

#### in MainActivity.java from
    getWeatherService().getWeather(searchEditText.getText().toString(), new Callback<ApiResponse>()
#### to
    String appid = <Your APP_ID>; // replace <Your App_Id> with yours
    getWeatherService().getWeather(searchEditText.getText().toString(), appid, new Callback<ApiResponse>()

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
