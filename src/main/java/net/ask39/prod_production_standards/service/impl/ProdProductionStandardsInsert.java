package net.ask39.prod_production_standards.service.impl;

import net.ask39.enums.MyConstants;
import net.ask39.service.BaseInsert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 帖子表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
@Lazy
@Service
public class ProdProductionStandardsInsert extends BaseInsert {
    public ProdProductionStandardsInsert() throws IOException {
        super(ProdProductionStandardsMigration.STANDARDS_OUT_PUT_FILE_NAME, MyConstants.ESC);
    }

    @Resource(name = "produceJdbcTemplate")
    private JdbcTemplate produceJdbcTemplate;
    @Override
    public void insert(String[] values) {
        produceJdbcTemplate.update("INSERT INTO prod_production_standards" + MyConstants.TABLE_SUFFIX +
                "(id, standards_name, demand_position, need_detail, founder_name, founder_id, create_on, reply_count, update_time, update_user, is_deleted)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", values);
    }
}
