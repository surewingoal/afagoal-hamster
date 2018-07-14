package com.afagoal.hamster.dao.client;

import com.afagoal.hamster.dao.BaseDao;
import com.afagoal.hamster.entity.client.ClientLog;
import com.afagoal.hamster.entity.client.QClientLog;
import org.springframework.stereotype.Repository;

/**
 * Created by BaoCai on 2018/7/14.
 */
@Repository
public class ClientLogDao extends BaseDao<ClientLog, QClientLog> {

    public ClientLogDao() {
        this.setQEntity(QClientLog.clientLog);
    }

}
