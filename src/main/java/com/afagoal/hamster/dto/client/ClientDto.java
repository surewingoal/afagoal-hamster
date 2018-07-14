package com.afagoal.hamster.dto.client;

import com.afagoal.hamster.entity.client.Client;
import com.afagoal.utils.date.DateUtils;
import com.afagoal.utils.regex.RegExUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

/**
 * Created by BaoCai on 2018/7/14.
 */
@Getter
@Setter
public class ClientDto {

    private Long id;

    private String productName;

    private Long productId;

    private String clientName;

    private Byte clientState = 0;

    private String clientMobile;

    private Byte clientSource;

    private String noticeDateStr;

    private String clientAddress;

    private Byte clientLevel = 1;

    private Byte clientIndustry;

    public Client instanceClient() {
        Client client = new Client();
        BeanUtils.copyProperties(this, client);

        if (!StringUtils.isEmpty(this.getNoticeDateStr())) {
            client.setNoticeDate(DateUtils.valueOfDate(this.getNoticeDateStr()));
        }

        return client;
    }

    public static ClientDto instance(Client client) {
        ClientDto dto = new ClientDto();
        BeanUtils.copyProperties(client, dto);
        return dto;
    }


    public String valid() {
        if (StringUtils.isEmpty(this.getClientMobile())) {
            return "请填写手机号码！";
        }
        if (!RegExUtils.isMobile(this.getClientMobile())) {
            return "请填写正确格式手机号码！";
        }
        return null;
    }

    public void updateClient(Client client) {

        if (null != this.getClientState()) {
            client.setClientState(this.getClientState());
        }

        if (null != this.getClientLevel()) {
            client.setClientLevel(this.getClientLevel());
        }

        if (null != this.getClientIndustry()) {
            client.setClientIndustry(this.getClientIndustry());
        }

        if (!StringUtils.isEmpty(this.getNoticeDateStr())) {
            client.setNoticeDate(DateUtils.valueOfDate(this.getNoticeDateStr()));
        }

    }
}
