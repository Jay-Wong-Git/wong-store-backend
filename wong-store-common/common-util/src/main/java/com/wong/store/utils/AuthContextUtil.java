package com.wong.store.utils;

import com.wong.store.model.entity.system.SysUser;

/**
 * @author Jay Wong
 * @date 2023/12/22 20:34
 */
public class AuthContextUtil {
    // 创建一个ThreadLocal对象
    private static final ThreadLocal<SysUser> THREAD_LOCAL = new ThreadLocal<>();

    // 存储数据
    public static void set(SysUser sysUser) {
        THREAD_LOCAL.set(sysUser);
    }

    // 读取数据
    public static SysUser get() {
        return THREAD_LOCAL.get();
    }

    // 删除数据
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
