package net.ask39.prod_topic_task_map.service.impl;

import net.ask39.enums.MyConstants;
import net.ask39.prod_topics.service.impl.ProdTopicsMigration;
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
public class ProdTopicTaskMapInsert extends BaseInsert {
    public ProdTopicTaskMapInsert() throws IOException {
        super(ProdTopicTaskMapMigration.OUT_PUT_FILE_NAME, MyConstants.ESC);
    }

    private final Logger log = LoggerFactory.getLogger(ProdTopicTaskMapInsert.class);
    @Resource(name = "produceJdbcTemplate")
    private JdbcTemplate produceJdbcTemplate;

    @Override
    protected void before() throws Exception {


    }

    @Override
    public void insert(String[] values) {
        produceJdbcTemplate.update("INSERT INTO prod_topic_task_map210224\n" +
                "(topic_id, task_id, `type`)\n" +
                "VALUES(?, ?, ?)", values);
    }
}
