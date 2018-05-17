package com.windmt.request;

import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.Map;

/**
 * @author: yibo
 **/
public class GetRequest extends OkHttpRequest {

    public GetRequest(String url, Map<String, String> queries, Map<String, String> params, Map<String, String> headers) {
        super(url, queries, params, headers);
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.get().build();
    }

    @Override
    protected RequestBody buildRequestBody() {
        return null;
    }
}
