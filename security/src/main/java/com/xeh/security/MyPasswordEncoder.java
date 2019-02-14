package com.xeh.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author: xeh
 * @date: 2019/1/28 17:04
 * @description: 加密类
 */
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }
}
