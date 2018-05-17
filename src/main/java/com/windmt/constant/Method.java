package com.windmt.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: yibo
 **/
@Getter
public enum Method {
    GET("GET"), POST("POST"), DELETE("DELETE"), PUT("PUT"), HEAD("HEAD"), PATCH("PATCH");
    private static final Map<String, Method> methods;

    static {
        methods = Arrays.stream(Method.values()).collect(Collectors.toMap(i -> i.getName(), Function.identity()));
    }

    private final String name;

    Method(String name) {
        this.name = name;
    }

    public static Method from(String name) {
        return methods.get(name);
    }

}
