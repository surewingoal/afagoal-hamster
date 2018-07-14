package com.afagoal.hamster.security;

import com.afagoal.hamster.entity.user.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by BaoCai on 18/2/26.
 * Description:
 */
public class SecurityContext {

    public final static String ANONYMOUS_USER = "anonymousUser";

    public static User currentUser() {
        Object userObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AfagoalUser user;
        if (userObject instanceof AfagoalUser) {
            user = (AfagoalUser) userObject;
        } else if (userObject instanceof String && ANONYMOUS_USER.equals(userObject)) {
            return emptyUser();
        } else {
            throw new RuntimeException("unknown type of user!");
        }

        User sysUser = new User();
        sysUser.setId(user.getId());
        sysUser.setUserName(user.getUsername());
        return sysUser;
    }

    private static User emptyUser() {
        User sysUser = new User();
        sysUser.setId(Long.valueOf(-1));
        sysUser.setUserName(ANONYMOUS_USER);
        return sysUser;
    }

}
