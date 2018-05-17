package com.windmt.builder;

import com.windmt.request.PostFormRequest;
import com.windmt.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: yibo
 **/
public class PostFormBuilder extends AbstractQueriesableRequestBuilder<PostFormBuilder> implements HasQueriesable, HasParamsable {

    @Override
    public PostFormBuilder params(Map<String, String> params, boolean... replace) {
        this.params = mergeMap(this.params, params, replace);
        return this;
    }

    @Override
    public PostFormBuilder addParam(String key, String val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }

    @Override
    public RequestCall selfBuild() {
        if (queries != null) {
            url = appendQueries(url, queries);
        }
        return new PostFormRequest(url, queries, params, headers).build();
    }

}
