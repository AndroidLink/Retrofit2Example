Version Tags Roadmap
===============

[v1.1-appid](https://github.com/AndroidLink/Retrofit2Example/tree/1.1-appid) keep retrofit 1.x and update all other libraries to latest version. And apply appid from http:openweathermap.org to resolve api response with 401 error code.

[v2.0] upgrade retrofit to 2.x, companied with okhttp3 and its logging-interceptor

[v2.1] move common request parameters, appid, from each api to isolate networking interceptor

[v2.2] apply rxjava adapter and request api in rx ways

Experience branch
===============
[ui-2.x] branch off from v2.2 and improve ui functions via integrating libraries

[di-aspect-2.x] experience with AspectJ for components seperation. e.g., as openweathermap could not response city name with Chinese, we could extend the query by Hanzi2Pinyin，before emitting query.

[realm-2.x] experience with local database framework, realm

[data-repository-2.x] deploy repository pattern for data layer query, union both remote retrofit request and local realm database for weather query.

[cache-2.x] cache data, branch off from tag v2.0
