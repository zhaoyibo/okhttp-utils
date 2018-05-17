package com.windmt.util;

import com.windmt.constant.Method;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: yibo
 **/
public class CurlTransformer {

    /**
     * 以 " 或 ' 开头并结尾，中间不出现 " 或 '
     */
    private static final String VALUE_PATTERN = "(?:\"(([^\"])+)\")|(?:'(([^'])+)')";

    private static final Pattern HEADER_PATTERN = Pattern.compile("(?<=-H|--header) +" + VALUE_PATTERN);
    private static final Pattern DATA_PATTERN = Pattern.compile("--?d(ata)? +" + VALUE_PATTERN);
    private static final Pattern URL_PATTERN = Pattern.compile("(?:\"(https?://\\S+)\")|(?:'(https?://\\S+)')");
    private static final Pattern METHOD_PATTERN = Pattern.compile("(?<=-X|--request) +(.*?) +");

    private final String curlCommand;
    private FluentMap<String, String> queries;
    /**
     * post RequestBody
     */
    private FluentMap<String, String> params;
    private FluentMap<String, String> headers;
    private FluentMap<String, String> cookies;
    /**
     * baseUri no queries
     */
    private String baseUrl;
    /**
     * original url with ?xx=yy
     */
    private String rawUrl;
    private Method method;


    public CurlTransformer(@NonNull String curlCommand) {
        curlCommand = curlCommand.trim();
        // trim leading $ or # that may have been left in
        boolean trimLeading = curlCommand.length() > 2 && ((curlCommand.charAt(0) == '$') || curlCommand.charAt(0) == '#') && curlCommand.charAt(1) == ' ';
        if (trimLeading) {
            curlCommand = curlCommand.substring(1).trim();
        }
        this.curlCommand = curlCommand;
    }

    public static void main(String[] args) {
        CurlTransformer transformer =
                new CurlTransformer("curl -k -X POST -H \"User-Agent: Mozilla/5.0 (iPhone; CPU iPhone OS 11_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E302 MicroMessenger/6.6.6 NetType/WIFI Language/zh_CN\" -H \"Cookie: last_env=g2; env_orgcode=lddcadmin; public_no_token=dsf%3A2%3A%7Bi%3A0%3Bs%3A15%3A%22public_no_token%22%3Bi%3A1%3Bs%3A16%3A%22iykqxr1449213609%22%3B%7D; yunke_org_id=976f0945d9067e8e037d36e5f116f6b520376594bfdeddaede92e674a971eddba%3A2%3A%7Bi%3A0%3Bs%3A12%3A%22yunke_org_id%22%3Bi%3A1%3Bs%3A36%3A%2239d47385-46d3-84b7-b831-ed614163e83c%22%3B%7D; ztc_org_id=19d7cd18ccb3c6159831e7232f3199088d5e02a26ec555ef16713120ec759432a%3A2%3A%7Bi%3A0%3Bs%3A10%3A%22ztc_org_id%22%3Bi%3A1%3Bi%3A426%3B%7D; PHPSESSID=9dj3lfjl3flrevu0vaop3h4kt7; aliyungf_tc=AQAAABudnxgyfQQA7hGB3ujabXuE/Xoe; env_orgcode=lddcadmin; iykqxr1449213610_openid=okYHEvpPZK7EinPM8YJY8ZyIlzos; gr_user_id=b76b244d-6963-4c3c-ae78-4c9bb6c62330\" -H \"X-Requested-With: XMLHttpRequest\" -H \"Host: ztcwx.myscrm.cn\" -H \"Referer: https://ztcwx.myscrm.cn/page/room.html?token=iykqxr1449213610&activityId=2936&chooseRoomId=1398309\" -H \"Origin: https://ztcwx.myscrm.cn\" -d \"token=iykqxr1449213610&chooseRoomId=1391309&randomCode=hxOtiGTL7qxHsltOkDCoy5oj6BOUCTCM&question_option_id=0\" \"https://ztcwx.myscrm.cn/index.php?r=choose-room/submit-order\"");
        System.out.println("raw url: " + transformer.getRawUrl());
        System.out.println("base url: " + transformer.getBaseUrl());
        System.out.println("method: " + transformer.getMethod());
        System.out.println("queries: " + transformer.getQueries());
        System.out.println("params: " + transformer.getParams());
        System.out.println("headers: " + transformer.getHeaders());
        System.out.println("cookies: " + transformer.getCookie());
    }

    public String getBaseUrl() {
        if (baseUrl != null) {
            return baseUrl;
        }
        baseUrl = getRawUrl().split("\\?")[0];
        return baseUrl;
    }

    public String getRawUrl() {
        if (rawUrl != null) {
            return rawUrl;
        }
        Matcher matcher = URL_PATTERN.matcher(curlCommand);
        if (matcher.find()) {
            rawUrl = matcher.group(1);
        } else {
            Exceptions.illegalArgument("can not find url, pls check your curl command");
        }
        return rawUrl;
    }

    public Method getMethod() {
        if (method != null) {
            return method;
        }
        Matcher matcher = METHOD_PATTERN.matcher(curlCommand);
        if (matcher.find()) {
            method = Method.from(matcher.group(1));
        }
        if (method == null) {
            method = Method.GET;
        }
        return method;
    }

    public FluentMap<String, String> getHeaders() {
        if (headers != null) {
            return headers;
        }
        headers = new FluentMap<>();
        Matcher matcher = HEADER_PATTERN.matcher(curlCommand);
        while (matcher.find()) {
            String header = matcher.group(1);
            String[] arr = header.split(": ?");
            headers.put(arr[0].trim(), arr[1].trim());
        }
        return headers;
    }

    public FluentMap<String, String> getCookie() {
        if (cookies != null) {
            return cookies;
        }
        cookies = new FluentMap<>();
        Map<String, String> headers = getHeaders();
        if (headers == null) {
            return cookies;
        }
        String cookie = getHeaders().get("Cookie");
        if (StringUtils.isNotEmpty(cookie)) {
            Arrays.stream(cookie.split("; ?"))
                    .map(s -> s.split("="))
                    .forEach(arr -> cookies.put(arr[0].trim(), arr[1].trim()));
        }
        return cookies;
    }

    public FluentMap<String, String> getParams() {
        if (params != null) {
            return params;
        }
        params = new FluentMap<>();
        Matcher matcher = DATA_PATTERN.matcher(curlCommand);
        while (matcher.find()) {
            String parameters = matcher.group(2);
            Arrays.stream(parameters.split("&"))
                    .map(s -> s.split("="))
                    .forEach(arr -> params.put(arr[0].trim(), arr[1].trim()));
        }
        return params;
    }

    public FluentMap<String, String> getQueries() {
        if (queries != null) {
            return queries;
        }
        queries = new FluentMap<>();
        String[] arr = getRawUrl().split("\\?");
        if (arr.length == 2) {
            Arrays.stream(arr[1].split("&"))
                    .map(String::trim)
                    .filter(StringUtils::isNotEmpty)
                    .map(s -> s.split("="))
                    .forEach(pair -> {
                        queries.put(pair[0], pair.length == 2 ? pair[1] : "");
                    });
        }
        return queries;
    }

}
