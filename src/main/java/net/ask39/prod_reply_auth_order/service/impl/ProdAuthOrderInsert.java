package net.ask39.prod_reply_auth_order.service.impl;

import net.ask39.enums.MyConstants;
import net.ask39.prod_topic_task_config.service.impl.ProdTopicTaskConfigMigration;
import net.ask39.service.BaseInsert;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 帖子表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
@Lazy
@Service
public class ProdAuthOrderInsert extends BaseInsert {
    public ProdAuthOrderInsert() throws IOException {
        super("output/prod_auth_order.txt", MyConstants.ESC);
    }

    private final Logger log = LoggerFactory.getLogger(ProdAuthOrderInsert.class);
    @Resource(name = "produceJdbcTemplate")
    private JdbcTemplate produceJdbcTemplate;
    private Map<String, String> taskIdReplyNo_taskConfigId;
    private Map<String, String> taskIdReplyNo_producerId;

    @Override
    protected void before() throws Exception {
        taskIdReplyNo_taskConfigId = new HashMap<>(512);
        taskIdReplyNo_producerId = new HashMap<>(512);
        for (String line : IOUtils.readLines(new FileInputStream(ProdTopicTaskConfigMigration.OUT_PUT_FILE_NAME), MyConstants.CHART_SET)) {
            String[] values = line.split(MyConstants.ESC);
            taskIdReplyNo_taskConfigId.put(values[1] + "" + values[9], values[0]);
            String producer_id = values[6];
            if (!StringUtils.isEmpty(producer_id) && !Objects.equals(producer_id, "NULL")) {
                taskIdReplyNo_producerId.put(values[1] + "" + values[9], producer_id);
            }
        }
    }

    @Override
    public void insert(String[] values) {
        values[1] = taskIdReplyNo_taskConfigId.get(values[4] + "" + values[2]);
        if (values[1] == null) {
            values[1] = String.valueOf(Long.MAX_VALUE);
        }
        String producer_id = values[3];
        if (StringUtils.isEmpty(producer_id)) {
            values[3] = "0";
        } else {
            values[3] = taskIdReplyNo_producerId.get(values[2] + "" + values[6]);
        }
        values[4] = String.valueOf(Long.MAX_VALUE);
        values[5] = String.valueOf(Long.MAX_VALUE);
        values[9] = String.valueOf(Integer.MAX_VALUE);
        produceJdbcTemplate.update("INSERT INTO pd_produce_dev_db.prod_reply_auth_order\n" +
                "(topic_id, task_config_id, task_id, assigned_id, reply_id, replier_id, reply_no, reply_standard_id, status, score, update_time, create_time)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", values);
    }
}