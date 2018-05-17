package com.windmt;

import com.windmt.builder.*;
import com.windmt.constant.Method;
import com.windmt.log.LoggerInterceptor;
import com.windmt.request.RequestCall;
import com.windmt.ssl.SslUtils;
import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author: yibo
 **/
public class OkHttpUtils {

    public static final long DEFAULT_MILLISECONDS = 10_000L;
    private volatile static OkHttpUtils instance;
    @Getter
    private OkHttpClient okHttpClient;


    public OkHttpUtils(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            SslUtils.SslParams sslParams = SslUtils.getSslSocketFactory(null, null, null);
            this.okHttpClient = new OkHttpClient()
                    .newBuilder()
                    .addInterceptor(new LoggerInterceptor(true))
                    .sslSocketFactory(sslParams.socketFactory, sslParams.trustManager)
                    .readTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
                    .writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
                    .connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
                    .build();
        } else {
            this.okHttpClient = okHttpClient;
        }
    }

    public static OkHttpUtils initClient(OkHttpClient okHttpClient) {
        if (instance == null) {
            synchronized (OkHttpUtils.class) {
                if (instance == null) {
                    instance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return instance;
    }

    public static OkHttpUtils getInstance() {
        return initClient(null);
    }

    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    public static OtherRequestBuilder put() {
        return new OtherRequestBuilder(Method.PUT);
    }

    public static OtherRequestBuilder delete() {
        return new OtherRequestBuilder(Method.DELETE);
    }

    public static HeadBuilder head() {
        return new HeadBuilder();
    }

    public Response execute(RequestCall requestCall) throws IOException {
        try (Response response = requestCall.execute()) {
            return response;
        }
    }

    public String ret(RequestCall requestCall) throws IOException {
        try (Response response = requestCall.execute(); ResponseBody body = response.body()) {
            if (response.isSuccessful()) {
                return body.string();
            } else {
                System.out.println("request failed , reponse's code is : " + response.code());
                return null;
            }
        }
    }

}
