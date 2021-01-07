package net.ask39.prod_production_plan.service.impl;

import net.ask39.service.BaseInsert;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;

/**
 * 帖子表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
@Lazy
@Service
public class ProdProductionPlanInsert extends BaseInsert {
    public ProdProductionPlanInsert() throws IOException {
        super(ProdProductionPlanMigration.OUT_PUT_FILE_NAME);
    }

    @Resource(name = "produceJdbcTemplate")
    private JdbcTemplate produceJdbcTemplate;

    @Override
    public void insert(String[] values) {
        produceJdbcTemplate.update("INSERT INTO prod_production_plan\n" +
                "(id, term_num, demand_position, topics_num, prod_success, done_time, reply_schedule, audit_schedule, authorize_schedule, funder_id, funder_name, create_on, production_standards_id, update_time, is_deleted)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", values);
    }
}
