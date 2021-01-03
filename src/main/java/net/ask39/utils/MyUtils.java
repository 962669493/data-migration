package net.ask39.utils;

import net.ask39.enums.MyConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author zhangzheng
 * @date 2021-01-03
 **/
@Component
public class MyUtils {
    private static JdbcTemplate jdbcTemplate;

    public static JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.setFetchSize(MyConstants.FETCH_SIZE);
        MyUtils.jdbcTemplate = jdbcTemplate;
    }
}
