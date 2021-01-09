package net.ask39.prod_topic_task_config.service.impl;

import net.ask39.enums.MyConstants;
import net.ask39.prod_topic_content_task.service.impl.ProdTopicContentTaskMigration;
import net.ask39.service.BaseInsert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
public class ProdTopicTaskConfigInsert extends BaseInsert {
    public ProdTopicTaskConfigInsert() throws IOException {
        super(ProdTopicTaskConfigMigration.OUT_PUT_FILE_NAME, MyConstants.ESC);
    }

    private final Logger log = LoggerFactory.getLogger(ProdTopicTaskConfigInsert.class);
    @Resource(name = "produceJdbcTemplate")
    private JdbcTemplate produceJdbcTemplate;

    @Override
    public void insert(String[] values) {
        String score = values[8];
        if(!StringUtils.isEmpty(score)){
            if(Integer.valueOf(score) < 0){
                values[8] = "0";
            }
        }
        produceJdbcTemplate.update("INSERT INTO pd_produce_dev_db.prod_topic_task_config\n" +
                "(id, task_id, task_name, `type`, doctor_type, is_assigned, assigned_id, doctor_tag, score, reply_no, create_on, create_name, update_time, is_deleted)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", values);
    }
}
