package com.afagoal.hamster.dto.saleman;

import com.afagoal.hamster.entity.saleman.Saleman;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * Created by BaoCai on 2018/7/14.
 */
@Getter
@Setter
public class SalemanDto {

    private Long unitId = Long.valueOf(1);

    private String productName;

    private Long productId;

    private String salemanIntro;


    public Saleman instanceSaleman() {
        Saleman saleman = new Saleman();
        BeanUtils.copyProperties(this, saleman);
        return saleman;
    }

}