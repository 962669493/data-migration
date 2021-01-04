package net.ask39.config;

import net.ask39.enums.MyConstants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author zhangzheng
 * @date 2021-01-04
 **/
@Configuration
public class JdbcTemplateConfig {
    @Bean(name = "askconfigJdbcTemplate")
    public JdbcTemplate askconfigJdbcTemplate(
            @Qualifier("askconfigDataSource") DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setFetchSize(MyConstants.FETCH_SIZE);
        return jdbcTemplate;
    }

    @Bean(name = "askdata4JdbcTemplate")
    public JdbcTemplate askdata4JdbcTemplate(
            @Qualifier("askdata4DataSource") DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setFetchSize(MyConstants.FETCH_SIZE);
        return jdbcTemplate;
    }

    @Bean(name = "asklogJdbcTemplate")
    public JdbcTemplate asklogJdbcTemplate(
            @Qualifier("asklogDataSource") DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setFetchSize(MyConstants.FETCH_SIZE);
        return jdbcTemplate;
    }
}
