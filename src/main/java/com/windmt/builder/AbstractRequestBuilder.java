package com.windmt.builder;

import com.windmt.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: yibo
 **/
public abstract class AbstractRequestBuilder<T extends AbstractRequestBuilder> {

    protected String url;
    protected Map<String, String> queries;
    protected Map<String, String> headers;
    protected Map<String, String> params;
    protected Map<String, String> cookies;

    public T url(String url) {
        this.url = url;
        return (T) this;
    }

    public T headers(Map<String, String> headers, boolean... replace) {
        this.headers = mergeMap(this.headers, headers, replace);
        return (T) this;
    }

    protected Map<String, String> mergeMap(Map<String, String> map1, Map<String, String> map2, boolean[] replace) {
        if (map2 == null){
            return map1;
        }
        if (map1 == null || (replace.length > 0 && replace[0])) {
            return map2;
        } else {
            map1.putAll(map2);
            return map1;
        }
    }

    public T addHeader(String key, String val) {
        if (this.headers == null) {
            headers = new LinkedHashMap<>();
        }
        headers.put(key, val);
        return (T) this;
    }

    public T cookies(Map<String, String> cookies) {
        this.cookies = cookies;
        return (T) this;
    }

    public T addCookie(String key, String val) {
        if (this.cookies == null) {
            this.cookies = new LinkedHashMap<>();
        }
        cookies.put(key, val);
        return (T) this;
    }

    protected abstract RequestCall selfBuild();

    public RequestCall build() {

        if (cookies != null) {
            String cookie = cookies.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("; "));
            addHeader("Cookie", cookie);
        }

        return selfBuild();
    }


}
