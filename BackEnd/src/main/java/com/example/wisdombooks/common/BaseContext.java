package com.example.wisdombooks.common;

/**
 * 基于ThreadLocal封装的工具类，用于保存和获取当前登录用户id
 */
public class BaseContext {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(String id_card) {
        threadLocal.set(id_card);
    }

    public static String getCurrentId() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
