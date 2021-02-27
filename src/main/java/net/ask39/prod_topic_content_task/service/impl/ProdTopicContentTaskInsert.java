package net.ask39.prod_topic_content_task.service.impl;

import net.ask39.enums.MyConstants;
import net.ask39.service.BaseInsert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 帖子表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
@Lazy
@Service
public class ProdTopicContentTaskInsert extends BaseInsert {
    public ProdTopicContentTaskInsert() throws IOException {
        super(ProdTopicContentTaskMigration.OUT_PUT_FILE_NAME, MyConstants.ESC);
    }

    private final Logger log = LoggerFactory.getLogger(ProdTopicContentTaskInsert.class);
    @Resource(name = "produceJdbcTemplate")
    private JdbcTemplate produceJdbcTemplate;

    @Override
    public void insert(String[] values) {
        produceJdbcTemplate.update("INSERT INTO prod_topic_content_task210224\n" +
                "(id, task_name, precedence, state, `type`, topic_condition, topic_amount, complete_amount, completion_rate, is_start, create_on, create_name, update_time, update_name, is_deleted)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", values);
    }
}
