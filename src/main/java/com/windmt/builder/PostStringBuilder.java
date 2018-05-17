package com.windmt.builder;

import com.windmt.request.PostStringRequest;
import com.windmt.request.RequestCall;
import okhttp3.MediaType;

/**
 * @author: yibo
 **/
public class PostStringBuilder extends AbstractQueriesableRequestBuilder<PostStringBuilder> implements HasQueriesable {

    private String content;
    private MediaType mediaType;

    public PostStringBuilder content(String content) {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall selfBuild() {
        return new PostStringRequest(url, queries, params, headers, content, mediaType).build();
    }

}
