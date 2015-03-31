package com.dllx.security.common;

/**
 * Security class加载器
 */
public class SecurityLoader {

    public static ClassLoader getClassLoader(){
        return SecurityLoader.class.getClassLoader();
    }
}
