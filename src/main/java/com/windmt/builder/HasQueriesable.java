package com.windmt.builder;

import java.util.Map;

/**
 * @author: yibo
 **/
public interface HasQueriesable {

    AbstractRequestBuilder queries(Map<String, String> queries, boolean... replace);

    AbstractRequestBuilder addQuery(String key, String val);


}
