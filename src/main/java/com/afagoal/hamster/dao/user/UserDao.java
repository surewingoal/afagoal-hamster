package com.afagoal.hamster.dao.user;

import com.afagoal.constant.BaseConstant;
import com.afagoal.hamster.dao.BaseDao;
import com.afagoal.hamster.entity.user.QUser;
import com.afagoal.hamster.entity.user.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 * Created by BaoCai on 2018/7/13.
 */
@Repository
public class UserDao extends BaseDao<User, QUser> {

    public UserDao() {
        this.setQEntity(QUser.user);
    }

    public User getUserByKey(String key) {
        List<BooleanExpression> expressionList = new ArrayList();
        expressionList.add(this.getQEntity().userName.eq(key)
                .or(this.getQEntity().mobile.eq(key)));
        expressionList.add(this.getQEntity().state.eq(BaseConstant.DEFAULT_STATE));
        List<User> users = this.getEntities(expressionList, null);
        if (CollectionUtils.isEmpty(users)) {
            return null;
        }
        return users.get(0);
    }
}
