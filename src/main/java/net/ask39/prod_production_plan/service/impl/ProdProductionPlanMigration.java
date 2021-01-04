package net.ask39.prod_production_plan.service.impl;

import net.ask39.service.AbstractMigration;
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
@Service
public class ProdProductionPlanMigration extends AbstractMigration {
    @Resource(name = "askconfigJdbcTemplate")
    private JdbcTemplate askconfigJdbcTemplate;

    @Resource(name = "askdata4JdbcTemplate")
    private JdbcTemplate askdata4JdbcTemplate;

    private static final String SQL_FILE_NAME = "sql/prod_production_plan.sql";

    private static final String OUT_PUT_FILE_NAME = "C:/Users/zhangzheng/Desktop/39/prod_production_plan.txt";

    public ProdProductionPlanMigration() throws FileNotFoundException {
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
        return askdata4JdbcTemplate;
    }
}
