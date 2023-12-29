package com.wong.store.utils;

import com.wong.store.model.entity.system.SysUser;
import com.wong.store.model.entity.user.UserInfo;

/**
 * @author Jay Wong
 * @date 2023/12/22 20:34
 */
public class AuthContextUtil {
    // 创建一个ThreadLocal对象
    private static final ThreadLocal<SysUser> SYS_USER_THREAD_LOCAL = new ThreadLocal<>();
    private static final ThreadLocal<UserInfo> USER_INFO_THREAD_LOCAL = new ThreadLocal<>();

    // 存储SysUser对象
    public static void setSysUser(SysUser sysUser) {
        SYS_USER_THREAD_LOCAL.set(sysUser);
    }

    // 读取SysUser对象
    public static SysUser getSysUser() {
        return SYS_USER_THREAD_LOCAL.get();
    }

    // 删除SysUser对象
    public static void removeSysUser() {
        SYS_USER_THREAD_LOCAL.remove();
    }

    // 存储UserInfo对象
    public static void setUserInfo(UserInfo userInfo) {
        USER_INFO_THREAD_LOCAL.set(userInfo);
    }

    // 读取UserInfo对象
    public static UserInfo getUserInfo() {
        return USER_INFO_THREAD_LOCAL.get();
    }

    // 删除UserInfo对象
    public static void removeUserInfo() {
        USER_INFO_THREAD_LOCAL.remove();
    }
}
