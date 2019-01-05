package com.afagoal.hamster.entity.newYearFlag;

import com.afagoal.hamster.entity.IdEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by BaoCai on 2019/1/5. Description:
 */
@Getter
@Setter
@Entity
@Table(name = "nf_flags")
public class Flag extends IdEntity {


    private String flagName;

    private Byte gender;


}
