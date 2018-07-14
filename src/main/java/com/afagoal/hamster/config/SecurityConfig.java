package com.afagoal.hamster.config;


import com.afagoal.auth.AfagoalTokenAuthenticationFilter;
import com.afagoal.auth.AfagoalTokenExtractor;
import com.afagoal.auth.AuthenticationStores;
import com.afagoal.hamster.dao.user.UserDao;
import com.afagoal.hamster.security.AfagoalPasswordEncoder;
import com.afagoal.hamster.security.AfagoalUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

/**
 * Created by BaoCai on 2018/7/13.
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthenticationStores authenticationStores;
    @Value("${afagoal.session.enable}")
    private Boolean afagoalSessionEnable;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/afagoal/login").permitAll()
                .anyRequest().authenticated()
//                .and().logout().logoutUrl("/logout")
//                .and().formLogin()
//                .loginProcessingUrl("/login")
//                .failureUrl("/login?authentication_error=true")
//                .defaultSuccessUrl("/")
//                .loginPage("/login").permitAll()
        ;

        http.csrf().disable();

        enableTokenAuthenticate(http);
    }

    private void enableTokenAuthenticate(HttpSecurity http) {
        if(afagoalSessionEnable){
            AfagoalTokenAuthenticationFilter afagoalTokenAuthenticationFilter
                    = new AfagoalTokenAuthenticationFilter(AfagoalTokenExtractor.AfagoalTokenExtractor_INSTANCE,authenticationStores);
            http.addFilterAfter(afagoalTokenAuthenticationFilter,SecurityContextPersistenceFilter.class);
        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new AfagoalPasswordEncoder());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new AfagoalUserDetailsService(userDao);
    }
}
