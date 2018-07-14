package com.afagoal.hamster.controller.login;

import com.afagoal.auth.AuthenticationStores;
import com.afagoal.hamster.security.AfagoalUser;
import com.afagoal.security.MD5Utils;
import com.afagoal.utildto.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by BaoCai on 2018/7/14.
 */
@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationStores authenticationStores;

    @RequestMapping(value = "/afagoal/login", method = RequestMethod.POST)
    @ResponseBody
    public Response tokenLogin(@RequestParam("username") String username,
            @RequestParam("password") String password) {
        if (StringUtils.isEmpty(username)) {
            return Response.ok("请填写用户名！");
        }
        if (StringUtils.isEmpty(password)) {
            return Response.ok("请填写密码！");
        }
        password = MD5Utils.passwordEncode(password);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);
        try {
            Authentication authentication = authenticationManager.authenticate(authRequest);

            if (null == authentication) {
                return Response.ok("用户不存在！");
            }

            String tokenId = UUID.randomUUID().toString().replace("-", "");
            authenticationStores.saveAuthentication(tokenId, authentication);
            AfagoalUser afagoalUser = (AfagoalUser) authentication.getPrincipal();
            Map result = new HashMap(afagoalUser.getDetails());
            result.put("afagoal_token", tokenId);
            return Response.ok(result);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return Response.ok("用户名密码错误！");
        }
    }

    @RequestMapping("/tokens_online")
    @ResponseBody
    public Response onlineSession() {
        return Response.ok(authenticationStores.onlineAuthentications());
    }

}
