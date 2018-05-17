package com.windmt.builder;

import okhttp3.HttpUrl;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: yibo
 **/
public abstract class AbstractQueriesableRequestBuilder<T extends AbstractQueriesableRequestBuilder> extends AbstractRequestBuilder<T> implements HasQueriesable {

    protected String appendQueries(String url, Map<String, String> queries) {
        if (url == null || queries == null || queries.isEmpty()) {
            return url;
        }

        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        for (Map.Entry<String, String> param : queries.entrySet()) {
            httpBuilder.addQueryParameter(param.getKey(), param.getValue());
        }

        HttpUrl httpUrl = httpBuilder.build();

        return httpUrl.toString();
    }

    @Override
    public T queries(Map<String, String> queries, boolean... replace) {
        this.queries = mergeMap(this.queries, queries, replace);
        return (T)this;
    }

    @Override
    public T addQuery(String key, String val) {
        if (this.queries == null) {
            this.queries = new LinkedHashMap<>();
        }
        this.queries.put(key, val);
        return (T)this;
    }

}
