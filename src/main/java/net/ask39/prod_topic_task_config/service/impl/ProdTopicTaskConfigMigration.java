package net.ask39.prod_topic_task_config.service.impl;

import net.ask39.service.AbstractMigration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * 内容生产任务表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-05
 **/
@Lazy
@Service
public class ProdTopicTaskConfigMigration extends AbstractMigration {
    @Resource(name = "askconfigJdbcTemplate")
    private JdbcTemplate askconfigJdbcTemplate;

    private static final String SQL_FILE_NAME = "sql/prod_topic_task_config.sql";

    private static final String OUT_PUT_FILE_NAME = "data/prod_topic_task_config.txt";

    public ProdTopicTaskConfigMigration() throws FileNotFoundException {
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
        return askconfigJdbcTemplate;
    }
}
