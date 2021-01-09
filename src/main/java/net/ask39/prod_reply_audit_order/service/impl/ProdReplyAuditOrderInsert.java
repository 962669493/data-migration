package net.ask39.prod_reply_audit_order.service.impl;

import net.ask39.enums.MyConstants;
import net.ask39.prod_reply_audit_order.enums.ReplyAuditStatusEnum;
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
public class ProdReplyAuditOrderInsert extends BaseInsert {
    public ProdReplyAuditOrderInsert() throws IOException {
        super("input/ReplyAuditResult.txt", MyConstants.ESC);
    }

    private final Logger log = LoggerFactory.getLogger(ProdReplyAuditOrderInsert.class);
    @Resource(name = "produceJdbcTemplate")
    private JdbcTemplate produceJdbcTemplate;

    @Override
    protected void before() throws Exception {
    }

    @Override
    public void insert(String[] values) {
        String auditStatus = values[8];
        ReplyAuditStatusEnum replyAuditStatusEnum = ReplyAuditStatusEnum.getByValue(Integer.parseInt(auditStatus));
        switch (replyAuditStatusEnum){
            case Pass:
                values[8] = "8";
                break;
            case Low:
                values[8] = "3";
                break;
            case Wait:
                values[8] = "2";
                break;
            case High:
                values[8] = "8";
                values[14] = "10";
                break;
            case Unqualified:
                values[8] = "4";
                break;
            case NullValue:
                values[8] = "2";
                break;
            default:
                break;
        }
        values[1] = String.valueOf(Long.MAX_VALUE);
        values[2] = String.valueOf(Long.MAX_VALUE);
        values[3] = String.valueOf(Long.MAX_VALUE);
        values[6] = String.valueOf(0);
        values[7] = String.valueOf(Long.MAX_VALUE);
        values[9] = String.valueOf(0);
        produceJdbcTemplate.update("INSERT INTO prod_reply_audit_order\n" +
                "(topic_id, task_config_id, task_id, assigned_id, reply_id, replier_id, reply_no, reply_standard_id, status, source, reject_types, remark, auditor_id, auditor_name, quality, update_time, create_time, is_batch)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", values);
    }
}
