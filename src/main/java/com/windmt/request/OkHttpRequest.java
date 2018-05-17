package com.windmt.request;

import com.windmt.util.Exceptions;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.Map;

/**
 * @author: yibo
 **/
public abstract class OkHttpRequest {

    protected String url;
    protected Map<String, String> queries;
    protected Map<String, String> params;
    protected Map<String, String> headers;

    protected Request.Builder builder = new Request.Builder();


    protected OkHttpRequest(String url, Map<String, String> queries,
                            Map<String, String> params, Map<String, String> headers) {
        this.url = url;
        this.queries = queries;
        this.params = params;
        this.headers = headers;

        if (url == null) {
            Exceptions.illegalArgument("url can not be null.");
        }

        initBuilder();
    }

    private void initBuilder() {
        builder.url(url);
        appendHeaders();
    }

    protected void appendHeaders() {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) {
            return;
        }

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }

    protected abstract Request buildRequest(RequestBody requestBody);

    protected abstract RequestBody buildRequestBody();

    public Request generateRequest() {
        RequestBody requestBody = buildRequestBody();
        Request request = buildRequest(requestBody);
        return request;
    }

    public RequestCall build() {
        return new RequestCall(this);
    }

}
