package com.afagoal.hamster.entity.client;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by BaoCai on 2018/7/14.
 */
@Getter
@Setter
@Entity
@Table(name = "afa_s_client_log")
public class ClientLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clientId;

    private Byte evaluate;

    private String remark;

    private Byte state;

    private LocalDateTime createdAt;

    private Long createdBy;
}
