package com.afagoal.hamster.service.user;

import com.afagoal.hamster.dao.user.UserDao;
import com.afagoal.hamster.dao.user.UserExtDao;
import com.afagoal.hamster.dto.register.WechatUserRegisterDto;
import com.afagoal.hamster.entity.user.User;
import com.afagoal.hamster.entity.user.UserExt;
import com.afagoal.hamster.exception.UserRegisteredException;
import com.afagoal.security.MD5Utils;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by BaoCai on 18/5/25.
 * Description:
 */
@Service
public class UserService {

    private static final Integer DEFAULT_USER_ROLE_ID = 2;

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserExtDao userExtDao;

    @Transactional
    public void createUser(User user) {
        Assert.notNull(user, "用户信息不可为空！");
        String password = MD5Utils.passwordEncode(user.getPassword());
        user.setPassword(password);
        saveUser(user);
    }

    private void saveUser(User user) {
        checkUserExist(user);
        if (null == user.getId()) {
            userDao.save(user);
        } else {
            userDao.merge(user);
        }
        createDefaultRole(user);
    }

    private void checkUserExist(User user) {
        List<BooleanExpression> booleanExpressions = new ArrayList();
        booleanExpressions.add(userDao.getQEntity().userName.eq(user.getUserName()));
        long count = userDao.getCount(booleanExpressions);
        if (count > 0) {
            throw new UserRegisteredException("该用户名已被被注册！");
        }
        booleanExpressions.clear();
        booleanExpressions.add(userDao.getQEntity().mobile.eq(user.getMobile()));
        count = userDao.getCount(booleanExpressions);
        if (count > 0) {
            throw new UserRegisteredException("该电话号码已被被注册！");
        }
    }

    private void createDefaultRole(User user) {
    }

    @Transactional
    public void createWechatUser(WechatUserRegisterDto userDto) {
        User user = userDto.instanceUser();
        saveUser(user);

        UserExt userExt = userDto.instanceUserExt();
        userExt.setUserId(user.getId());

        userExtDao.save(userExt);
    }
}
