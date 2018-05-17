package com.windmt.constant;

import okhttp3.MediaType;

/**
 * @author: yibo
 **/
public interface GlobalConstants {

    MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain; charset=utf-8");
    MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

}
