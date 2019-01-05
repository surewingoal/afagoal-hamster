package com.afagoal.hamster.dao.client;

import com.afagoal.constant.BaseConstant;
import com.afagoal.hamster.dao.BaseDao;
import com.afagoal.hamster.entity.client.Client;
import com.afagoal.hamster.entity.client.QClient;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by BaoCai on 2018/7/14.
 */
@Repository
public class ClientDao extends BaseDao<Client, QClient> {

    public ClientDao() {
        this.setQEntity(QClient.client);
    }

    public Client getById(Long clientId) {
        List<BooleanExpression> expressions = new ArrayList();
        expressions.add(this.getQEntity().state.ne(BaseConstant.DELETE_STATE));
        expressions.add(this.getQEntity().id.eq(clientId));

        return this.getEntity(expressions);

    }

    public void delete(Set<Long> idArray) {

        String sql = "update afa_s_client set state = 99 where id in ( ?1 ) ;";
        Query query = this.getEntityManager().createNativeQuery(sql);
        query.setParameter(1, idArray);

        query.executeUpdate();
    }
}
