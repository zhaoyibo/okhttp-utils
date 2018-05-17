package com.windmt.request;

import com.windmt.constant.GlobalConstants;
import com.windmt.util.Exceptions;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.Map;

/**
 * @author: yibo
 **/
public class PostStringRequest extends OkHttpRequest {


    private String content;
    private MediaType mediaType;

    public PostStringRequest(String url, Map<String, String> queries, Map<String, String> params, Map<String, String> headers, String content, MediaType mediaType) {
        super(url, queries, params, headers);
        this.content = content;
        this.mediaType = mediaType;

        if (this.content == null) {
            Exceptions.illegalArgument("the content can not be null !");
        }
        if (this.mediaType == null) {
            this.mediaType = GlobalConstants.MEDIA_TYPE_PLAIN;
        }

    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.post(requestBody).build();
    }

    @Override
    protected RequestBody buildRequestBody() {
        return RequestBody.create(mediaType, content);
    }
}
