package com.afagoal.hamster.dao.newYearFlag;

import com.afagoal.hamster.dao.BaseDao;
import com.afagoal.hamster.entity.newYearFlag.GoodSaying;
import com.afagoal.hamster.entity.newYearFlag.QGoodSaying;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * Created by BaoCai on 2019/1/5. Description:
 */
@Repository
public class GoodSayingDao extends BaseDao<GoodSaying, QGoodSaying> {

    public GoodSayingDao() {
        this.setQEntity(QGoodSaying.goodSaying);
    }

    public List<GoodSaying> getAll() {
        return this.getEntities(new ArrayList(), null);
    }

    public void insert(String content) {

        if (StringUtils.isBlank(content)) {
            return;
        }

        String sql = "insert into nf_good_saying (content) values ( ?1 ); ";

        Query query = this.getEntityManager().createNativeQuery(sql);
        query.setParameter(1, content);

        query.executeUpdate();
    }
}
