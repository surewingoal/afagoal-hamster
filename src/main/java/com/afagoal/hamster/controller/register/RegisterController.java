package com.afagoal.hamster.controller.register;

import com.afagoal.hamster.dto.register.WechatUserRegisterDto;
import com.afagoal.hamster.exception.UserRegisteredException;
import com.afagoal.hamster.service.user.UserService;
import com.afagoal.utildto.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by BaoCai on 2018/7/13.
 */
@RestController
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/register/wechat")
    @ResponseBody
    public Response registerWechat(@RequestBody WechatUserRegisterDto user) {
        String validResult = user.valid();
        if (!StringUtils.isEmpty(validResult)) {
            return Response.error(validResult);
        }
        try {
            userService.createWechatUser(user);
        } catch (UserRegisteredException e) {
            return Response.error(e.getMessage());
        }
        return Response.ok("注册成功！");
    }

}
