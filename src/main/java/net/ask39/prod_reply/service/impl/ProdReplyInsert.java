package net.ask39.prod_reply.service.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import net.ask39.enums.MyConstants;
import net.ask39.prod_topic_task_config.service.impl.ProdTopicTaskConfigMigration;
import net.ask39.service.BaseInsert;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class ProdReplyInsert extends BaseInsert {

    public static final String INPUT_ISSUE_POST_TXT = "input/IssuePost.txt";

    public ProdReplyInsert() throws IOException {
        super(INPUT_ISSUE_POST_TXT, MyConstants.ESC);
    }

    private final Logger log = LoggerFactory.getLogger(ProdReplyInsert.class);
    @Resource(name = "produceJdbcTemplate")
    private JdbcTemplate produceJdbcTemplate;
    @Resource(name = "askcenterJdbcTemplate")
    private JdbcTemplate askcenterJdbcTemplate;
    private Map<String, String> topicIdReplyNo_id;
    private Map<String, String> tid_topicId;
    private Map<String, Integer> replyIdAndTypeLv2_score;
    private Set<String> replyOrderIds;
    private OutputStream replyContentIsNull;
    private OutputStream notExistReplyOrder;
    private OutputStream repeatReplyOrder;

    @Override
    protected void before() throws Exception {
        replyContentIsNull = new FileOutputStream("output/回复内容为空.txt");
        notExistReplyOrder = new FileOutputStream("output/不存在答题工单.txt");
        repeatReplyOrder = new FileOutputStream("output/重复的答题工单.txt");

        replyOrderIds = new HashSet<>(1000000);
        topicIdReplyNo_id = new HashMap<>(1000000);
        produceJdbcTemplate.query("select id, topic_id, reply_no from prod_reply_order", rs -> {
            for(;rs.next();){
                topicIdReplyNo_id.put(rs.getString(2) +""+rs.getString(3), rs.getString(1));
            }
            return null;
        });

        tid_topicId = new HashMap<>(1000000);
        produceJdbcTemplate.query("select id, tid from prod_topics", rs -> {
            for(;rs.next();){
                tid_topicId.put(rs.getString(2), rs.getString(1));
            }
            return null;
        });

        replyIdAndTypeLv2_score = new HashMap<>(2000000);
        askcenterJdbcTemplate.query("SELECT t1.Score, t1.replyid, t1.TypeLv2 from MemberPointsDetailAudit t1\n" +
                "where t1.replyID is not null and t1.TypeLv1  = 2", rs -> {
            for(;rs.next();){
                tid_topicId.put(rs.getString(2), rs.getString(1));
                replyIdAndTypeLv2_score.put(rs.getString(2) + rs.getString(3), rs.getInt(1));
            }
            return null;
        });
    }

    @Override
    public void insert(String[] values) throws IOException {
        String replyContent = values[9];
        if(!StringUtils.isEmpty(replyContent)){
            values[9] = replyContent.replaceAll(MyConstants.SUB, "\n");
        }else{
            IOUtils.writeLines(Lists.newArrayList(values[0]), System.getProperty("line.separator"), replyContentIsNull, MyConstants.CHART_SET);
            values[9] = "";
        }
        values[2] = tid_topicId.get(values[2]);
        values[6] = topicIdReplyNo_id.get(values[2] + values[7]);
        if(values[6] == null){
            IOUtils.writeLines(Lists.newArrayList(values[0]), System.getProperty("line.separator"), notExistReplyOrder, MyConstants.CHART_SET);
            return;
        }
        if(replyOrderIds.contains(values[6])){
            IOUtils.writeLines(Lists.newArrayList(values[0]), System.getProperty("line.separator"), repeatReplyOrder, MyConstants.CHART_SET);
            return;
        }else{
            replyOrderIds.add(values[6]);
        }
        values[8] = String.valueOf(Long.MAX_VALUE);
        Integer replyScore = replyIdAndTypeLv2_score.get(values[0] + "7");
        if(replyScore == null){
            replyScore = 0;
        }
        Integer doctorScore = replyIdAndTypeLv2_score.get(values[0] + "8");
        if(doctorScore == null){
            doctorScore = 0;
        }
        values[10] = String.valueOf(replyScore + doctorScore);
        values[13] = String.valueOf(3);
        produceJdbcTemplate.update("INSERT INTO prod_reply\n" +
                "(id, reply_id, topic_id, forum_id, sources, ip, order_id, reply_no, reply_standard_id, reply_content, score, inner_copy_check_result, outer_copy_ratio, audit_status, is_manual_audit, quality, replier_type, reject_count, qc_state_id, page_forum_tree_code, page_forum_tree_value, page_forum, remark, reply_version, machine_audit_status, replier_id, replier_name, update_user, create_on, update_time, is_delete)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", values);
    }
}
