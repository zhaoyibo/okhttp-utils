package com.windmt.builder;

import com.windmt.request.GetRequest;
import com.windmt.request.RequestCall;

/**
 * @author: yibo
 **/
public class GetBuilder extends AbstractQueriesableRequestBuilder<GetBuilder> implements HasQueriesable {

    @Override
    protected RequestCall selfBuild() {
        if (queries != null) {
            url = appendQueries(url, queries);
        }

        return new GetRequest(url, queries, params, headers).build();
    }


}
