package com.afagoal.hamster.entity.client;

import com.afagoal.hamster.entity.IdEntity;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by BaoCai on 2018/7/14.
 */
@Getter
@Setter
@Entity
@Table(name = "afa_s_client")
public class Client extends IdEntity {

    private Long salemanUserId;

    private String productName;

    private Long productId;

    private String clientName;

    private Byte clientState;

    private String clientMobile;

    private Byte clientSource;

    private LocalDate noticeDate;

    private String clientAddress;

    private Byte clientLevel;

    private Byte clientIndustry;

}
