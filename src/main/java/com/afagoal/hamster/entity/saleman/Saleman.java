package com.afagoal.hamster.entity.saleman;

import com.afagoal.hamster.entity.IdEntity;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by BaoCai on 2018/7/14.
 */
@Getter
@Setter
@Table(name = "afa_s_saleman")
@Entity
public class Saleman extends IdEntity {

    private Long userId;

    private Long unitId;

    private String productName;

    private Long productId;

    private String salemanIntro;

    private Byte auditState;

    private LocalDateTime auditTime;

    private String auditUserName;

    private Long auditUserId;

    private String auditRemark;

}
