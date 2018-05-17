package com.windmt.log;


import com.windmt.util.StringUtils;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import okio.Buffer;

import java.io.IOException;
import java.util.Map;

/**
 * @author: yibo
 **/
@Log4j2
public class LoggerInterceptor implements Interceptor {

    private boolean showResponse;

    public LoggerInterceptor(boolean showResponse) {
        this.showResponse = showResponse;
    }

    public LoggerInterceptor(String tag) {
        this(false);
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        return logForResponse(response);
    }

    private void logForRequest(Request request) {
        String url = request.url().toString();
        Headers headers = request.headers();

        log.debug("========request'log=======");
        log.debug("method : {}", request.method());
        log.debug("url: {}", url);
        if (headers != null && headers.size() > 0) {
            log.debug("headers: ↓↓↓↓↓↓↓↓\n{}", headers.toString());
            log.debug("headers: ↑↑↑↑↑↑↑↑");
        }
        RequestBody requestBody = request.body();
        if (requestBody != null) {
            MediaType mediaType = requestBody.contentType();
            if (mediaType != null) {
                log.debug("requestBody's contentType: {}", mediaType.toString());
                if (isText(mediaType)) {
                    log.debug("requestBody's content: {}", bodyToString(request));
                } else {
                    log.debug("requestBody's content: {}", " maybe [file part] , too large too print , ignored!");
                }
            }
        }
        log.debug("========request'log=======end");
    }

    private Response logForResponse(Response response) {
        //===>response log
        log.debug("========response'log=======");
        Response.Builder builder = response.newBuilder();
        Response clone = builder.build();
        log.debug("url: {}", clone.request().url());
        log.debug("code: {}", clone.code());
        log.debug("protocol: {}", clone.protocol());
        if (StringUtils.isNotEmpty(clone.message())) {
            log.debug("message: {}", clone.message());
        }

        if (showResponse) {
            ResponseBody body = clone.body();
            if (body != null) {
                MediaType mediaType = body.contentType();
                log.debug("responseBody's mediaType: {}", mediaType);
                if (mediaType != null) {
                    log.debug("responseBody's contentType: {}", mediaType.toString());
                    if (isText(mediaType)) {
                        String resp = null;
                        try {
                            resp = body.string();
                            log.debug("responseBody's content: {}", resp);
                        } catch (IOException e) {
                            log.debug("something error when show responseBody.", e);
                        }
                        body = ResponseBody.create(mediaType, resp);
                        return response.newBuilder().body(body).build();
                    } else {
                        log.debug("responseBody's content: {}", " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
        }

        log.debug("========response'log=======end");

        return response;
    }


    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            return mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml");
        }
        return false;
    }

    private String bodyToString(Request request) {
        try {
            Request copy = request.newBuilder().build();
            Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (IOException e) {
            log.debug("something error when show requestBody.", e);
            return null;
        }
    }

    private String formatMap(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
            first = false;
        }
        return sb.toString();
    }

}
