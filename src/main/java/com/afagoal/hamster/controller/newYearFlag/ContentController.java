package com.afagoal.hamster.controller.newYearFlag;

import com.afagoal.hamster.dao.newYearFlag.FlagDao;
import com.afagoal.hamster.dao.newYearFlag.GoodSayingDao;
import com.afagoal.utildto.Response;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by BaoCai on 2019/1/5. Description:
 */
@RestController
public class ContentController {

    @Autowired
    private FlagDao flagDao;
    @Autowired
    private GoodSayingDao goodSayingDao;

    @PostMapping("/flags")
    @Transactional
    public Response addFlag(@RequestBody String contents) {

        Arrays.stream(contents.split("&&"))
                .forEach(content -> flagDao.insert(content));

        return Response.ok("ok!");
    }

    @PostMapping("/good_sayings")
    @Transactional
    public Response addGoodSayings(@RequestBody String contents) {

        Arrays.stream(contents.split("&&"))
                .forEach(content -> goodSayingDao.insert(content));

        return Response.ok("ok!");
    }

}
