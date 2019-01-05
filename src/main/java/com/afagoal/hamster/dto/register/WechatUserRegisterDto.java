package com.afagoal.hamster.dto.register;

import com.afagoal.constant.BaseConstant;
import com.afagoal.hamster.entity.user.User;
import com.afagoal.hamster.entity.user.UserExt;
import com.afagoal.security.MD5Utils;
import com.afagoal.utils.regex.RegExUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

/**
 * Created by BaoCai on 18/6/28. Description:
 */
@Getter
@Setter
public class WechatUserRegisterDto {

    private String wechatNickName;

    private String wechatAvatarUrl;

    private Byte wechatGender;

    private String wechatCity;

    private String wechatProvince;

    private String wechatLanguage;

    private String wechatCountry;

    private String email;

    private String mobile;

    private String password;

    private String repassword;

    private String userName;


    public String valid() {
        if (StringUtils.isEmpty(this.getMobile())) {
            return "请填写手机号码！";
        }
        if (!RegExUtils.isMobile(this.getMobile())) {
            return "请填写正确格式手机号码！";
        }
        if (StringUtils.isEmpty(this.getEmail())) {
            return "请填写邮箱！";
        }
        if (!RegExUtils.isEmail(this.getEmail())) {
            return "请填写正确格式的邮箱！";
        }
        if (StringUtils.isEmpty(this.getUserName())) {
            return "请填写登录名！";
        }
        if (StringUtils.isEmpty(this.getPassword())) {
            return "请填写密码！";
        }
        if (!this.getPassword().equals(this.getRepassword())) {
            return "两次输入密码不正确！";
        }
        return null;
    }

    public User instanceUser() {
        User user = new User();
        user.setPassword(MD5Utils.passwordEncode(this.getPassword()));
        user.setUserName(this.getUserName());
        user.setMobile(this.getMobile());
        user.setNickName(this.getWechatNickName());
        user.setState(BaseConstant.DEFAULT_STATE);
        user.setEmail(this.getEmail());
        user.setUserType((byte) 0);    //普通用户
        return user;
    }

    public UserExt instanceUserExt() {
        UserExt userExt = new UserExt();
        BeanUtils.copyProperties(this, userExt);
        return userExt;
    }

}
