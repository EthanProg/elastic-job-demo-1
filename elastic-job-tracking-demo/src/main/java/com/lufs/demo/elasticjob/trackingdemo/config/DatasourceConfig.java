package com.lufs.demo.elasticjob.trackingdemo.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {
    @Primary
    @ConfigurationProperties("spring.datasource.druid.elasticjob")
    @Bean
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

}
