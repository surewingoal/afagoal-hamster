package com.afagoal.hamster.dao.user;

import com.afagoal.hamster.dao.BaseDao;
import com.afagoal.hamster.entity.user.QUserExt;
import com.afagoal.hamster.entity.user.UserExt;
import org.springframework.stereotype.Repository;

/**
 * Created by BaoCai on 2018/7/13.
 */
@Repository
public class UserExtDao extends BaseDao<UserExt, QUserExt> {

    public UserExtDao() {
        this.setQEntity(QUserExt.userExt);
    }
}
