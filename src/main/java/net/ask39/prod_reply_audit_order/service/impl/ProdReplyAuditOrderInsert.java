package net.ask39.prod_reply_audit_order.service.impl;

import net.ask39.enums.MyConstants;
import net.ask39.prod_reply_audit_order.enums.ReplyAuditStatusEnum;
import net.ask39.service.BaseInsert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    private Map<String, String> tid_topicId;
    private Set<String> reply_id;

    @Override
    protected void before() throws Exception {
        tid_topicId = new HashMap<>(1000000);
        produceJdbcTemplate.query("select id, tid from prod_topics" + MyConstants.TABLE_SUFFIX, rs -> {
            for (; rs.next(); ) {
                tid_topicId.put(rs.getString(2), rs.getString(1));
            }
            return null;
        });
        reply_id = new HashSet<>(1500000);
        produceJdbcTemplate.query("select reply_id from prod_reply_audit_order" + MyConstants.TABLE_SUFFIX, rs -> {
            for (; rs.next(); ) {
                reply_id.add(rs.getString(1));
            }
            return null;
        });
    }

    @Override
    public void insert(String[] values) {
        String auditStatus = values[8];
        ReplyAuditStatusEnum replyAuditStatusEnum = ReplyAuditStatusEnum.getByValue(Integer.parseInt(auditStatus));
        switch (replyAuditStatusEnum) {
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
        // task_config_id
        values[1] = String.valueOf(Long.MAX_VALUE);
        // topic_id
        values[0] = tid_topicId.get(values[0]);
        if (values[0] == null) {
            // 任务没有生产计划或标准
            return;
        }
        if (values[2] == null) {
            // task_id
            values[2] = String.valueOf(Long.MAX_VALUE);
        }
        // assigned_id
        values[3] = String.valueOf(0);
        if(reply_id.contains(values[4])){
            return;
        }else{
            reply_id.add(values[4]);
        }
        // reply_no
        values[6] = String.valueOf(0);
        // reply_standard_id
        values[7] = String.valueOf(Long.MAX_VALUE);
        // source
        values[9] = String.valueOf(0);
        produceJdbcTemplate.update("INSERT INTO prod_reply_audit_order" + MyConstants.TABLE_SUFFIX +
                "(topic_id, task_config_id, task_id, assigned_id, reply_id, replier_id, reply_no, reply_standard_id, status, source, reject_types, remark, auditor_id, auditor_name, quality, update_time, create_time, is_batch)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", values);
    }
}
