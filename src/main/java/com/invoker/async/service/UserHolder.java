package com.invoker.async.service;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 2021/9/1-4:03 下午
 *
 * @author <a href="mailto:javajason@163.com">Jason(LiuJianJun)</a>
 */
public class UserHolder {
    public static final TransmittableThreadLocal<String> THREAD_LOCAL = new TransmittableThreadLocal<>();

    public UserHolder() {
    }

    public static void set(String user) {
        THREAD_LOCAL.set(user);
    }

    public static String get() {
        return (String) THREAD_LOCAL.get();
    }

    public static String getUserExt() {
        return get();
    }

    public static void clear() {
        THREAD_LOCAL.remove();
    }
}
