package net.ask39.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author zhangzheng
 * @date 2021-01-04
 **/
@Configuration
public class DataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.askconfig")
    @Qualifier("askconfigDataSource")
    DataSource askconfigDataSource() { return DataSourceBuilder.create().build(); }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.askdata4")
    @Qualifier("askdata4DataSource")
    DataSource askdata4DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.asklog")
    @Qualifier("asklogDataSource")
    DataSource asklogDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.askcenter")
    @Qualifier("askcenterDataSource")
    DataSource askcenterDataSource() {
        return DataSourceBuilder.create().build();
    }
}
