package com.afagoal.hamster.controller;

import com.afagoal.utildto.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by BaoCai on 2019/1/5. Description:
 */
@RestController
public class HelloController {


    @GetMapping("/hello")
    public Response hello() {
        return Response.ok("ok!");
    }

}
