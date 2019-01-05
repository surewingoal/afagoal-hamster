package com.afagoal.hamster.security;

import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Created by BaoCai on 18/2/26. Description:
 */
@Getter
@Setter
public class AfagoalUser extends User {

    private Long id;

    private String info;

    private final Map details;

    public AfagoalUser(String username, String password,
            Collection<? extends GrantedAuthority> authorities, Long id) {
        this(username, password, authorities, id, null);
    }

    public AfagoalUser(String username, String password,
            Collection<? extends GrantedAuthority> authorities, Long id, Map details) {
        super(username, password, authorities);
        this.id = id;
        this.details = details;
    }
}
