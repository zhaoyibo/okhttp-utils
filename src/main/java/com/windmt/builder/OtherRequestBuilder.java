package com.windmt.builder;

import com.windmt.constant.Method;
import com.windmt.request.OtherRequest;
import com.windmt.request.RequestCall;
import okhttp3.RequestBody;

/**
 * @author: yibo
 **/
public class OtherRequestBuilder extends AbstractRequestBuilder<OtherRequestBuilder> {

    private RequestBody requestBody;
    private Method method;
    private String content;


    public OtherRequestBuilder(Method method) {
        this.method = method;
    }

    @Override
    public RequestCall selfBuild() {
        return new OtherRequest(url, queries, params, headers, requestBody, content, method).build();
    }

    public OtherRequestBuilder requestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public OtherRequestBuilder requestBody(String content) {
        this.content = content;
        return this;
    }

}
