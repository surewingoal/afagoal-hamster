package com.afagoal.hamster.security;

import com.afagoal.hamster.dao.user.UserDao;
import com.afagoal.hamster.entity.user.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

/**
 * Created by BaoCai on 18/2/26. Description:
 */
public class AfagoalUserDetailsService implements UserDetailsService {

    private UserDao userDao;

    private static List<GrantedAuthority> grantedAuthorities;

    public AfagoalUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
        Assert.notNull(userDao, "sysUserDao can't be null!");

        grantedAuthorities = new ArrayList();
        grantedAuthorities.add(new SimpleGrantedAuthority("BASE_USER"));
    }

    @Override
    public UserDetails loadUserByUsername(String key) throws UsernameNotFoundException {
        User user = userDao.getUserByKey(key);
        if (null == user) {
            throw new UsernameNotFoundException("用户不存在！");
        }

        AfagoalUser afagoalUser = new AfagoalUser(user.getUserName(), user.getPassword(),
                grantedAuthorities, user.getId(), userDetails(user));

        return afagoalUser;
    }

    private Map userDetails(User user) {
        Map details = new HashMap();
        details.put("user_name", user.getUserName());
        details.put("real_name", user.getRealName());
        details.put("nick_name", user.getNickName());
        details.put("user_id", user.getId());
        return details;
    }
}
