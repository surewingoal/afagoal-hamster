package com.afagoal.hamster.service.client;

import com.afagoal.constant.BaseConstant;
import com.afagoal.hamster.dao.client.ClientDao;
import com.afagoal.hamster.dao.client.ClientLogDao;
import com.afagoal.hamster.dto.client.ClientDto;
import com.afagoal.hamster.dto.client.ClientLogDto;
import com.afagoal.hamster.entity.client.Client;
import com.afagoal.hamster.entity.client.ClientLog;
import com.afagoal.hamster.entity.user.User;
import com.afagoal.hamster.security.SecurityContext;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by BaoCai on 2018/7/14.
 */
@Service
public class ClientService {

    @Autowired
    private ClientDao clientDao;
    @Autowired
    private ClientLogDao clientLogDao;

    @Transactional
    public void addClient(ClientDto clientDto) {

        Client client = clientDto.instanceClient();

        User user = SecurityContext.currentUser();
        client.setSalemanUserId(user.getId());

        clientDao.save(client);
    }

    @Transactional
    public void logClient(ClientLogDto clientLogDto) {

        ClientLog clientLog = clientLogDto.instanceClientLog();
        clientLog.setState(BaseConstant.DEFAULT_STATE);
        User user = SecurityContext.currentUser();
        clientLog.setCreatedAt(LocalDateTime.now());
        clientLog.setCreatedBy(user.getId());

        clientLogDao.save(clientLog);
    }

    @Transactional
    public void updateClient(ClientDto clientDto) {
        
        Client client = clientDao.getById(clientDto.getId());

        if(null == client){
            throw new RuntimeException("找不到客户！client_id : " + clientDto.getId());
        }

        clientDto.updateClient(client);

        clientDao.save(client);
    }

    @Transactional
    public void deleteClient(String ids) {
        String[] idArray = ids.split(",");
        Set<Long> idSet = Arrays.stream(idArray)
                .map(id -> Long.valueOf(id))
                .collect(Collectors.toSet());
        clientDao.delete(idSet);
    }
}
