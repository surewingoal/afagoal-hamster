package com.afagoal.hamster.dto.newYearFlag;

import com.afagoal.hamster.entity.newYearFlag.Flag;
import com.afagoal.hamster.entity.newYearFlag.UserRecord;
import com.mysema.commons.lang.Assert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by BaoCai on 2019/1/5. Description:
 */
@Getter
@Setter
@ApiModel
public class UserFlagsDto {

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "性别 0-不明 1-女 2-男 ")
    private Byte Gender;

    @ApiModelProperty(value = "新年flags")
    private List<String> flags;

    @ApiModelProperty(value = "励志名言")
    private String goodSaying;

    public static UserFlagsDto instance(UserRecord userRecord, Set<Flag> flags) {
        Assert.notNull(userRecord, "userRecord cna`t be null!");
        Assert.notEmpty(flags, "flags cna`t be empty!");
        UserFlagsDto dto = new UserFlagsDto();
        dto.setGender(userRecord.getGender());
        dto.setUserName(userRecord.getUserName());
        List<String> userFlags = new ArrayList();

        Map<Long, Flag> flagMap = flags.stream()
                .collect(Collectors.toMap(flag -> flag.getId(), flag -> flag));

        Arrays.stream(userRecord.getFlags().split(","))
                .forEach(id -> {
                    Flag flag = flagMap.get(Long.valueOf(id));
                    if (flag != null) {
                        userFlags.add(flag.getFlagName());
                    }
                });
        dto.setFlags(userFlags);

        if (null != userRecord.getGoodSaying()) {
            dto.setGoodSaying(userRecord.getGoodSaying().getContent());
        }
        return dto;
    }

}
