package com.afagoal.hamster.entity.user;

import com.afagoal.hamster.entity.IdEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

/**
 * Created by BaoCai on 2018/7/13.
 */
@Entity
@Getter
@Setter
@Table(name = "afa_user")
public class User extends IdEntity {

    private String userName;

    private String nickName;

    private String realName;

    private String password;

    private String loginIp;

    private int loginTimes;

    private String email;

    @ColumnTransformer(
            read = "AES_DECRYPT(FROM_BASE64(mobile),'jb9o8i$#3e4f5nlb')",
            write = "TO_BASE64(AES_ENCRYPT(IFNULL(?, 'null'),'jb9o8i$#3e4f5nlb'))"
    )
    private String mobile;

    private Integer unitId;

    private Byte userType;


}
