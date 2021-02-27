package net.ask39.prod_production_reply_standard_map.service.impl;

import net.ask39.enums.MyConstants;
import net.ask39.prod_production_standards.service.impl.ProdProductionStandardsMigration;
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
public class ProdProductionReplyStandardMapInsert extends BaseInsert {
    public ProdProductionReplyStandardMapInsert() throws IOException {
        super(ProdProductionStandardsMigration.REPLY_STANDARD_MAP_OUT_PUT_FILE_NAME, MyConstants.ESC);
    }
    private final Logger log = LoggerFactory.getLogger(ProdProductionReplyStandardMapInsert.class);
    @Resource(name = "produceJdbcTemplate")
    private JdbcTemplate produceJdbcTemplate;
    @Override
    public void insert(String[] values) {
        produceJdbcTemplate.update("INSERT INTO prod_production_reply_standard_map" + MyConstants.TABLE_SUFFIX +
                "(prod_standard_id, reply_standard_id, reply_no)" +
                "VALUES(?, ?, ?)", values);
    }
}
