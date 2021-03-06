package com.afagoal.hamster.security;

import com.afagoal.security.MD5Utils;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by BaoCai on 18/4/13. Description:
 */
public class AfagoalPasswordEncoder implements PasswordEncoder {

    private static final String AFAGOAL_KEY = "afagoal_";

    @Override
    public String encode(CharSequence rawPassword) {
        String result = MD5Utils.md5Hex(rawPassword + AFAGOAL_KEY);
        return result;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return StringUtils.equals(encode(rawPassword), encodedPassword);
    }
}
