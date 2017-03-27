# Upgrade v2.2 - update with rxjava2
===============

# Upgrade v2.1 - common request parameters in interceptor
===============

# Upgrade v2.0 - retrofit upgrade to 2.x
===============
#### define constants for the version of libraries in root build.gradle
    ext {
      ......
    }

#### app/build.gradle from
    compile 'com.squareup.retrofit:retrofit:1.6.1'

    compile 'com.jakewharton:butterknife:8.5.1'
    annotationProcessor "com.jakewharton:butterknife-compiler:8.5.1"

    compile 'com.squareup.picasso:picasso:2.5.2'

    compile 'org.parceler:parceler-api:1.1.6'
    annotationProcessor 'org.parceler:parceler:1.1.6'
#### to
    compile "com.squareup.retrofit2:retrofit:$rootProject.retrofit2Version"
    compile "com.squareup.retrofit2:converter-gson:$rootProject.retrofit2Version"
    compile "com.squareup.okhttp3:okhttp:$rootProject.okhttp3Version"
    compile "com.squareup.okhttp3:logging-interceptor:$rootProject.okhttp3Version"

    compile "com.jakewharton:butterknife:$rootProject.butterknifeVersion"
    annotationProcessor "com.jakewharton:butterknife-compiler:$rootProject.butterknifeVersion"

    compile "com.squareup.picasso:picasso:$rootProject.picassoVersion"
    compile "org.parceler:parceler-api:$rootProject.parcelerVersion"

    annotationProcessor "org.parceler:parceler:$rootProject.parcelerVersion"

#### in WeatherService.java from
    void getWeather(@Query("q") String strCity, @Query("appid") String appid, Callback<ApiResponse> callback);
#### to
    Call<ApiResponse> getWeather(@Query("q") String strCity, @Query("appid") String appid);

#### in RestClient.java from
    RestAdapter restAdapter = new RestAdapter.Builder()
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint(BASE_URL)
            .setConverter(new GsonConverter(gson))
            .build();

    apiService = restAdapter.create(WeatherService.class);
#### to
    Retrofit.Builder retrofitBuilder
            = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson));

    OkHttpClient defaultOkHttpClient
            = new OkHttpClient.Builder()
            .build();

    OkHttpClient.Builder okHttpClientBuilder = defaultOkHttpClient.newBuilder();

    // todo: wrap all common param within a interceptor rather than spreading
    // all over every api methods.
    //        if(networkInterceptor != null){
    //            okHttpClientBuilder.addNetworkInterceptor(networkInterceptor);
    //        }

    OkHttpClient modifiedOkHttpClient = okHttpClientBuilder
            .addInterceptor(getHttpLoggingInterceptor())
            .build();

    retrofitBuilder.client(modifiedOkHttpClient);
    retrofitBuilder.baseUrl(BASE_URL);

    Retrofit retrofit = retrofitBuilder.build();
    apiService = retrofit.create(WeatherService.class);

#### in MainActivity.java from
    String appid = ApiConstant.APP_ID;
    App.getRestClient().getWeatherService().getWeather(searchEditText.getText().toString(), appid, new Callback<ApiResponse>() {
        @Override
        public void success(ApiResponse apiResponse, Response response)
        {
            handleResponse(apiResponse);
        }

        @Override
        public void failure(RetrofitError error)
        {
            handleFailure(error.getMessage());
        }
    });
#### to
    String appid = ApiConstant.APP_ID;
    Call<ApiResponse> call = App.getRestClient().getWeatherService().getWeather(searchEditText.getText().toString(), appid);
    call.enqueue(new Callback<ApiResponse>() {
        @Override
        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
            if (response.isSuccessful()) {
                handleResponse(response.body());
            } else {
                handleFailure(response.code() + response.message());
            }
        }

        @Override
        public void onFailure(Call<ApiResponse> call, Throwable t) {
            handleFailure(t.getMessage());
        }
    });

# Upgrade v1.0 - update for use retrofit 1.x
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
