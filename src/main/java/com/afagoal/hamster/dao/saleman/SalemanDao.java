package com.afagoal.hamster.dao.saleman;

import com.afagoal.hamster.dao.BaseDao;
import com.afagoal.hamster.entity.saleman.QSaleman;
import com.afagoal.hamster.entity.saleman.Saleman;
import org.springframework.stereotype.Repository;

/**
 * Created by BaoCai on 2018/7/14.
 */
@Repository
public class SalemanDao extends BaseDao<Saleman, QSaleman> {

    public SalemanDao() {
        this.setQEntity(QSaleman.saleman);
    }

}
