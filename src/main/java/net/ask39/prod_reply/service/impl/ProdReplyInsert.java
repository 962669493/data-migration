package net.ask39.prod_reply.service.impl;

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
public class ProdReplyInsert extends BaseInsert {
    public ProdReplyInsert() throws IOException {
        super("input/IssuePost.txt", MyConstants.ESC);
    }

    private final Logger log = LoggerFactory.getLogger(ProdReplyInsert.class);
    @Resource(name = "produceJdbcTemplate")
    private JdbcTemplate produceJdbcTemplate;

    @Override
    protected void before() throws Exception {

    }

    @Override
    public void insert(String[] values) {
        String replyContent = values[8];
        if(!StringUtils.isEmpty(replyContent)){
            values[8] = replyContent.replaceAll(MyConstants.SUB, "\n");
        }
        values[6] = String.valueOf(Long.MAX_VALUE);
        log.info("replyId：[{}]", values[0]);
        produceJdbcTemplate.update("INSERT INTO prod_reply\n" +
                "(id, reply_id, topic_id, forum_id, sources, ip, order_id, reply_no, reply_standard_id, reply_content, score, inner_copy_check_result, outer_copy_ratio, audit_status, is_manual_audit, quality, replier_type, reject_count, qc_state_id, forum_tree_code, forum_tree_value, forum_id_v2, remark, reply_version, machine_audit_status, replier_id, replier_name, update_user, create_on, update_time, is_delete)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", values);
    }
}
