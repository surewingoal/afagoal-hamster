package com.afagoal.hamster.dao.newYearFlag;

import com.afagoal.hamster.dao.BaseDao;
import com.afagoal.hamster.entity.newYearFlag.QUserRecord;
import com.afagoal.hamster.entity.newYearFlag.UserRecord;
import com.mysema.commons.lang.Assert;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 * Created by BaoCai on 2019/1/5. Description:
 */
@Repository
public class UserRecordDao extends BaseDao<UserRecord, QUserRecord> {

    public UserRecordDao() {
        this.setQEntity(QUserRecord.userRecord);
    }

    public UserRecord getByUserNameAndGender(String userName, Byte gender) {

        Assert.notNull(userName, "userName can`t be null !");

        List<BooleanExpression> expressionList = new ArrayList();
        expressionList.add(this.getQEntity().userName.eq(userName));
        if (null != gender) {
            expressionList.add(this.getQEntity().gender.eq(gender));
        }

        expressionList = this.rectifyExpressions(expressionList);

        JPAQuery<UserRecord> query = new JPAQuery(this.getEntityManager());

        query.select(this.getQEntity())
                .from(this.getQEntity())
                .leftJoin(this.getQEntity().goodSaying).fetchJoin()
                .where(expressionList.toArray(new BooleanExpression[expressionList.size()]));

        List<OrderSpecifier> orders = new ArrayList();
        orders.add(this.getQEntity().createdAt.desc());

        if (!CollectionUtils.isEmpty(orders)) {
            query.orderBy(orders.toArray(new OrderSpecifier[orders.size()]));
        }

        List<UserRecord> userRecords = query.fetch();

        if (!CollectionUtils.isEmpty(userRecords)) {
            return userRecords.get(0);
        }

        return null;
    }
}
