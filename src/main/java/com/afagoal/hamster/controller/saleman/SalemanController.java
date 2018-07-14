package com.afagoal.hamster.controller.saleman;

import com.afagoal.hamster.dao.saleman.SalemanDao;
import com.afagoal.hamster.dto.saleman.SalemanDto;
import com.afagoal.hamster.entity.saleman.Saleman;
import com.afagoal.hamster.entity.user.User;
import com.afagoal.hamster.security.SecurityContext;
import com.afagoal.utildto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by BaoCai on 2018/7/14.
 */
@RestController
public class SalemanController {

    @Autowired
    private SalemanDao salemanDao;

    @PostMapping("/salemans")
    @Transactional
    public Response registerSaleman(@RequestBody SalemanDto salemanDto) {
        Saleman saleman = salemanDto.instanceSaleman();
        User user = SecurityContext.currentUser();

        if (user.getId() == -1) {
            throw new RuntimeException("您未登陆！");
        }
        saleman.setUserId(user.getId());
        salemanDao.save(saleman);
        return Response.ok("提交成功！");
    }

    //TODO 审核

}
