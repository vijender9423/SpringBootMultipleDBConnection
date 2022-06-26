package com.shl.scoring.amcat.configurations;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "tcDbEntityManager",
        transactionManagerRef = "tcDbTransactionManager",
        basePackages = {"com.shl.scoring.amcat.repositories.tc"})
public class tcDbConfiguration {

    /*@Bean
    @Primary
    @ConfigurationProperties(prefix = "tc.datasource")
    public DataSource tcDbDataSourceConf() {
        return DataSourceBuilder
                .create()
                .build();
    }*/
    @Primary
    @Bean(name = "tcDataSourceProperties")
    @ConfigurationProperties("tc.datasource")
    public DataSourceProperties tcDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "tcDataSource")
    @ConfigurationProperties("tc.datasource.configuration")
    public DataSource tcDataSource(@Qualifier("tcDataSourceProperties") DataSourceProperties tcDataSourceProperties) {
        return tcDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "tcDbEntityManager")
    public LocalContainerEntityManagerFactoryBean sqlEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("tcDataSource") DataSource tcDataSource) {
        Map<String, String> primaryJpaProperties = new HashMap<>();
        primaryJpaProperties.put("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");
        primaryJpaProperties.put("hibernate.hbm2ddl.auto", "update");

        return builder
                .dataSource(tcDataSource)
                .packages("com.example.demo.models.tc")
                .persistenceUnit("tcDataSource")

                .build();
    }

    @Primary
    @Bean(name = "tcDbTransactionManager")
    public PlatformTransactionManager sqlTransactionManager(@Qualifier("tcDbEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
