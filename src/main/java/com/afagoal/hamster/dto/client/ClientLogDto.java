package com.afagoal.hamster.dto.client;

import com.afagoal.hamster.entity.client.Client;
import com.afagoal.hamster.entity.client.ClientLog;
import com.afagoal.utils.json.CustomDateTimeSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * Created by BaoCai on 2018/7/14.
 */
@Getter
@Setter
public class ClientLogDto {

    private Long id;

    private Long clientId;

    private Byte evaluate;

    private String remark;

    @JsonSerialize(using = CustomDateTimeSerialize.class)
    private LocalDateTime createdAt;

    public ClientLog instanceClientLog() {
        ClientLog clientLog = new ClientLog();

        BeanUtils.copyProperties(this, clientLog);

        return clientLog;
    }

    public static ClientLogDto instance(ClientLog log) {

        ClientLogDto dto = new ClientLogDto();

        BeanUtils.copyProperties(log, dto);

        return dto;
    }

}