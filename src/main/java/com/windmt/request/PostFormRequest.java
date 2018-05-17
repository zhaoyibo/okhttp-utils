package com.windmt.request;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.Map;

/**
 * @author: yibo
 **/
public class PostFormRequest extends OkHttpRequest {


    public PostFormRequest(String url, Map<String, String> queries, Map<String, String> params, Map<String, String> headers) {
        super(url, queries, params, headers);
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.post(requestBody).build();
    }

    @Override
    protected RequestBody buildRequestBody() {
        FormBody.Builder builder = new FormBody.Builder();
        addParams(builder);
        FormBody formBody = builder.build();
        return formBody;
    }

    private void addParams(FormBody.Builder builder) {
        if (params != null) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
    }

}
