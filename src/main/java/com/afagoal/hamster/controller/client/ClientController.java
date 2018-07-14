package com.afagoal.hamster.controller.client;

import com.afagoal.constant.BaseConstant;
import com.afagoal.hamster.dao.client.ClientDao;
import com.afagoal.hamster.dao.client.ClientLogDao;
import com.afagoal.hamster.dto.client.ClientDto;
import com.afagoal.hamster.dto.client.ClientLogDto;
import com.afagoal.hamster.entity.client.Client;
import com.afagoal.hamster.entity.client.ClientLog;
import com.afagoal.hamster.entity.user.User;
import com.afagoal.hamster.security.SecurityContext;
import com.afagoal.hamster.service.client.ClientService;
import com.afagoal.utildto.PageData;
import com.afagoal.utildto.Response;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by BaoCai on 2018/7/14.
 */
@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientDao clientDao;
    @Autowired
    private ClientLogDao clientLogDao;

    @PostMapping("/clients")
    public Response addClient(@RequestBody ClientDto clientDto) {
        String validInfo = clientDto.valid();
        if (StringUtils.isNotEmpty(validInfo)) {
            return Response.error(validInfo);
        }

        clientService.addClient(clientDto);
        return Response.ok("添加成功！");
    }

    @PutMapping("/clients")
    public Response updateClient(@RequestBody ClientDto clientDto) {
        clientService.updateClient(clientDto);
        return Response.ok("操作成功！");
    }

    @GetMapping("/clients")
    public Response getClients(
            @RequestParam(value = "client_level", required = false) Byte clientLevel,
            @RequestParam(value = "client_state", required = false) Byte clientState,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "15") int size,
            @RequestParam(value = "client_name", required = false) String clientName) {

        User user = SecurityContext.currentUser();

        List<BooleanExpression> expressionList = new ArrayList();
        expressionList.add(clientDao.getQEntity().state.ne(BaseConstant.DELETE_STATE));
        expressionList.add(clientDao.getQEntity().salemanUserId.eq(user.getId()));

        if (null != clientLevel) {
            expressionList.add(clientDao.getQEntity().clientLevel.ne(clientLevel));
        }

        if (null != clientState) {
            expressionList.add(clientDao.getQEntity().clientState.ne(clientState));
        }

        if (!StringUtils.isEmpty(clientName)) {
            expressionList
                    .add(clientDao.getQEntity().clientName.like("%" + clientName.trim() + "%"));
        }

        Pageable pageable = new PageRequest(Math.max(0, page - 1), size);

        List<OrderSpecifier> orderSpecifiers = new ArrayList();
        orderSpecifiers.add(clientDao.getQEntity().createdAt.desc());

        List<Client> clients = clientDao.getEntities(expressionList, orderSpecifiers, pageable);
        Long count = clientDao.getCount(expressionList);

        List<ClientDto> dtos = clients.stream()
                .map(client -> ClientDto.instance(client))
                .collect(Collectors.toList());

        PageData pageData = new PageData(dtos, count.intValue());

        return Response.ok(pageData);
    }

    @DeleteMapping("clients")
    public Response deleteClient(@RequestParam(value = "ids") String ids) {
        if (StringUtils.isEmpty(ids)) {
            return Response.error("请选择需要删除的记录！");
        }
        clientService.deleteClient(ids);
        return Response.ok("操作成功！");
    }

    @PostMapping("/clients/log")
    public Response logClient(@RequestBody ClientLogDto clientLogDto) {
        clientService.logClient(clientLogDto);
        return Response.ok("保存成功！");
    }

    @GetMapping("/clients/{client_id}/log")
    public Response getClientLog(@PathVariable(value = "client_id") Long clientId) {
        List<BooleanExpression> expressionList = new ArrayList();
        expressionList.add(clientLogDao.getQEntity().state.ne(BaseConstant.DELETE_STATE));
        expressionList.add(clientLogDao.getQEntity().clientId.eq(clientId));

        List<OrderSpecifier> orderSpecifiers = new ArrayList();
        orderSpecifiers.add(clientLogDao.getQEntity().createdAt.desc());

        List<ClientLog> logs = clientLogDao.getEntities(expressionList, orderSpecifiers, null);
        Long count = clientLogDao.getCount(expressionList);
        List<ClientLogDto> dtos = logs.stream()
                .map(log -> ClientLogDto.instance(log))
                .collect(Collectors.toList());

        PageData pageData = new PageData(dtos, count.intValue());

        return Response.ok(pageData);
    }

}
