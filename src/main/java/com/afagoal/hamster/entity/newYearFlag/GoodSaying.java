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
@Table(name = "nf_good_saying")
@Entity
public class GoodSaying extends IdEntity {

    private String content;

}
