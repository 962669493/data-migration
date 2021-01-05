package net.ask39.prod_topics_operate_log.service.impl;

import net.ask39.service.AbstractMigration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * 帖子表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
@Lazy
@Service
public class ProdTopicsOperateLogMigration extends AbstractMigration {
    @Resource(name = "askconfigJdbcTemplate")
    private JdbcTemplate askconfigJdbcTemplate;

    @Resource(name = "asklogJdbcTemplate")
    private JdbcTemplate asklogJdbcTemplate;

    private static final String SQL_FILE_NAME = "sql/prod_topics_operate_log.sql";

    private static final String OUT_PUT_FILE_NAME = "data/prod_topics_operate_log.txt";

    public ProdTopicsOperateLogMigration() throws FileNotFoundException {
        super(SQL_FILE_NAME, OUT_PUT_FILE_NAME);
    }

    @Override
    protected void before(){

    }

    @Override
    protected void convert(Map<String, Object> row) {

    }

    @Override
    protected JdbcTemplate getJdbcTemplate() {
        return asklogJdbcTemplate;
    }
}
