package com.afagoal.hamster.entity.newYearFlag;

import com.afagoal.hamster.entity.IdEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by BaoCai on 2019/1/5. Description:
 */
@Getter
@Setter
@Table(name = "nf_user_record")
@Entity
public class UserRecord extends IdEntity {

    private String userName;

    private Byte gender;

    private String flags;

    @Column(name = "good_saying_id")
    private Long goodSayingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "good_saying_id", updatable = false, insertable = false)
    private GoodSaying goodSaying;

}
