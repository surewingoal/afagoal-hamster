package com.afagoal.hamster.dao.newYearFlag;

import com.afagoal.hamster.dao.BaseDao;
import com.afagoal.hamster.entity.newYearFlag.Flag;
import com.afagoal.hamster.entity.newYearFlag.QFlag;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Created by BaoCai on 2019/1/5. Description:
 */
@Repository
public class FlagDao extends BaseDao<Flag, QFlag> {

    public FlagDao() {
        this.setQEntity(QFlag.flag);
    }

    public List<Flag> getByIds(List<Long> flagIds) {
        Assert.notEmpty(flagIds, "flagIds can`t be empty!");
        List<BooleanExpression> expressions = new ArrayList();
        expressions.add(this.getQEntity().id.in(flagIds));

        return this.getEntities(expressions, null);
    }

    public List<Flag> getAll() {
        List<BooleanExpression> expressions = new ArrayList();
        return this.getEntities(expressions, null);
    }

    public void insert(String content) {
        if (StringUtils.isBlank(content)) {
            return;
        }
        String sql = "insert into nf_flags (flag_name) values ( ?1 ); ";

        Query query = this.getEntityManager().createNativeQuery(sql);
        query.setParameter(1, content);

        query.executeUpdate();
    }
}
