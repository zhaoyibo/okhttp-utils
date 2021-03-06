# okhttp-utils

对 okhttp 的封装类，java 适用，参考 https://github.com/hongyangAndroid/okhttputils

okhttp 见：https://github.com/square/okhttp 目前对应 okhttp 版本 3.10.0

jdk 要求 1.8+

# 目前对以下需求进行了封装

* 一般的 GET 请求
* 一般的 POST 请求
* 支持 HEAD、DELETE、PATCH、PUT
* 支持自签名网站 https 的访问，提供方法设置下证书就行

# 配置 OkhttpClient
默认情况下，将直接使用 okhttp 默认的配置生成 OkHttpClient，如果你有任何配置，记得调用`initClient`方法进行设置。

```java
OkHttpClient okHttpClient = new OkHttpClient.Builder()
//        .addInterceptor(new LoggerInterceptor(true))
          .connectTimeout(10000L, TimeUnit.MILLISECONDS)
          .readTimeout(10000L, TimeUnit.MILLISECONDS)
          //其他配置
          .build();
         
OkHttpUtils.initClient(okHttpClient);
```

# 对于 Log
初始化 OkHttpClient 时，通过设置拦截器实现，提供了一个`LoggerInterceptor`，当然你可以自行实现一个 Interceptor 。
```java
OkHttpClient okHttpClient = new OkHttpClient.Builder()
       .addInterceptor(new LoggerInterceptor(true))
       //其他配置
       .build();
OkHttpUtils.initClient(okHttpClient);
```

# 对于 Https
依然是通过配置即可，提供了一个类`SslUtils`

* 设置可访问所有的 https 网站
```java
SslUtils.SslParams sslParams = SslUtils.getSslSocketFactory(null, null, null);
OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .sslSocketFactory(sslParams.sslSocketFactory, sslParams.trustManager)
        //其他配置
        .build();
OkHttpUtils.initClient(okHttpClient);
```

* 设置具体的证书
```java
SslUtils.SslParams sslParams = SslUtils.getSslSocketFactory(证书的inputstream, null, null);
OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .sslSocketFactory(sslParams.sslSocketFactory, sslParams.trustManager)
//其他配置
.build();
OkHttpUtils.initClient(okHttpClient);
```

* 双向认证
```java
SslUtils.getSslSocketFactory(
	证书的inputstream, 
	本地证书的inputstream, 
	本地证书的密码)
```

# GET 请求
```java
String url = "http://www.csdn.net/";
Response response = OkHttpUtils.get()
        .url(url)
        .addQuery("username", "hello")
        .addQuery("password", "world")
        .build()
        .execute();
```


# POST 请求
```java
Response response = OkHttpUtils.post()
        .url(url)
        .addQuery("action", "login")
        .addParam("username", "hello")
        .addParam("password", "world")
        .build()
        .execute();
```

# POST JSON

```java
Response response = OkHttpUtils.postString()
        .url(url)
        .mediaType(GlobalConstants.MEDIA_TYPE_JSON)
        .content("{\"username\":\"hello\",\"password\":\"world\"}")
        .build()
        .execute();
```

# cUrl 命令解析
提供了一个工具类`CurlTransformer`，可以将 curl 命令解析成对应的

* url（带 query 和不带 query 的）
* method
* headers
* cookies
* queries
* post parameters

```java
String curlCommand = "";
CurlTransformer transformer = new CurlTransformer(curl);

Method method = transformer.getMethod();
String rawUrl = transformer.getRawUrl(); // 带 query
String baseUrl = transformer.getBaseUrl(); // 不带 query
Map<String, String> headers = transformer.getHeaders();
Map<String, String> cookies = transformer.getCookies();
Map<String, String> queries = transformer.getQueries();
Map<String, String> params = transformer.getParams(); // post parameters

```
