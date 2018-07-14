package com.afagoal.hamster.config;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Created by BaoCai on 2018/7/13.
 */
@Configuration
public class DBConfig {

    @Bean
    @Primary
    public PlatformTransactionManager afagoalTransactionManager() {
        return new JpaTransactionManager(afagoalEntityManagerFactory().getObject());
    }

    @Bean
    @Primary
    public EntityManager afagoalEntityManager() {
        return afagoalEntityManagerFactory().getObject().createEntityManager();
    }

    @Bean(name = "afagoalDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.afagoal")
    public DataSource afagoalDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean afagoalEntityManagerFactory() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(afagoalDataSource());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setPackagesToScan(
                "com.afagoal"
        );
        Map<String, Object> map = new HashMap<>();
        map.put("hibernate.physical_naming_strategy", new SpringPhysicalNamingStrategy());
        map.put("hibernate.show_sql",Boolean.FALSE);
        map.put("hibernate.format_sql",Boolean.FALSE);
        factoryBean.setJpaPropertyMap(map);
        return factoryBean;
    }

}
