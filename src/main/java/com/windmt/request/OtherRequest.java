package com.windmt.request;

import com.windmt.constant.GlobalConstants;
import com.windmt.constant.Method;
import com.windmt.util.Exceptions;
import com.windmt.util.StringUtils;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.internal.http.HttpMethod;

import java.util.Map;

/**
 * @author: yibo
 **/
public class OtherRequest extends OkHttpRequest {


    private RequestBody requestBody;
    private Method method;
    private String content;

    public OtherRequest(String url, Map<String, String> queries, Map<String, String> params, Map<String, String> headers, RequestBody requestBody, String content, Method method) {
        super(url, queries, params, headers);
        this.requestBody = requestBody;
        this.method = method;
        this.content = content;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        switch (method) {
            case PUT:
                builder.put(requestBody);
                break;
            case DELETE:
                if (requestBody == null) {
                    builder.delete();
                } else {
                    builder.delete(requestBody);
                }
                break;
            case HEAD:
                builder.head();
                break;
            case PATCH:
                builder.patch(requestBody);
                break;
        }

        return builder.build();
    }

    @Override
    protected RequestBody buildRequestBody() {
        if (requestBody == null && StringUtils.isEmpty(content) && HttpMethod.requiresRequestBody(method.getName())) {
            Exceptions.illegalArgument("requestBody and content can not be null in method:" + method);
        }

        if (requestBody == null && StringUtils.isNotEmpty(content)) {
            requestBody = RequestBody.create(GlobalConstants.MEDIA_TYPE_PLAIN, content);
        }

        return requestBody;
    }
}
