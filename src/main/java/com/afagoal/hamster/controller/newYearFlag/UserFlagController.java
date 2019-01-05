package com.afagoal.hamster.controller.newYearFlag;

import com.afagoal.hamster.dto.newYearFlag.UserFlagsDto;
import com.afagoal.hamster.service.newYearFlag.UserFlagService;
import com.afagoal.utildto.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by BaoCai on 2019/1/5. Description:
 */
@RestController
@Api(tags = "新年flag文档", value = "新年flag文档")
public class UserFlagController {

    @Autowired
    private UserFlagService userFlagService;

    @GetMapping("/user_flags")
    @ApiOperation("获取新年flag")
    public Response<UserFlagsDto> userFlags(
            @ApiParam(value = "user_name", required = true) @RequestParam(value = "user_name") String userName,
            @ApiParam(value = "gender") @RequestParam(value = "gender", defaultValue = "0") Byte gender) {

        UserFlagsDto userFlagsDto = userFlagService.findUserFlags(userName, gender);

        if (null == userFlagsDto) {
            userFlagsDto = userFlagService.createUserFlags(userName, gender);
        }

        userFlagService.cacheUserFlags(userFlagsDto);
        return Response.ok(userFlagsDto);
    }

}
