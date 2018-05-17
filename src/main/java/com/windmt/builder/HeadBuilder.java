package com.windmt.builder;

import com.windmt.constant.Method;
import com.windmt.request.OtherRequest;
import com.windmt.request.RequestCall;

/**
 * @author: yibo
 **/
public class HeadBuilder extends AbstractQueriesableRequestBuilder<HeadBuilder> implements HasQueriesable {

    @Override
    protected RequestCall selfBuild() {
        if (this.queries != null) {
            appendQueries(url, queries);
        }
        return new OtherRequest(url, queries, params, headers, null, null, Method.HEAD).build();
    }

}
