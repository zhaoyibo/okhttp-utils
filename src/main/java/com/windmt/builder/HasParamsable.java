package com.windmt.builder;

import java.util.Map;

/**
 * @author: yibo
 **/
public interface HasParamsable {

    AbstractRequestBuilder params(Map<String, String> params, boolean... replace);

    AbstractRequestBuilder addParam(String key, String val);


}
