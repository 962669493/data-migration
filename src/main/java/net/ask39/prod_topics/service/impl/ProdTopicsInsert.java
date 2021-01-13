package net.ask39.prod_topics.service.impl;

import net.ask39.enums.MyConstants;
import net.ask39.service.BaseInsert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * 帖子表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
@Lazy
@Service
public class ProdTopicsInsert extends BaseInsert {
    public ProdTopicsInsert() throws IOException {
        super("output/prod_topics.txt", MyConstants.ESC);
    }

    private final Logger log = LoggerFactory.getLogger(ProdTopicsInsert.class);
    @Resource(name = "produceJdbcTemplate")
    private JdbcTemplate produceJdbcTemplate;
    @Resource(name = "askcenterJdbcTemplate")
    private JdbcTemplate askcenterJdbcTemplate;

    private Set<String> removeTid;
    private Map<String, Forum> forumId_Forum;

    @Override
    protected void before() throws Exception {
        removeTid = new HashSet<>();
        //removeTid.add("66628605");
        removeTid.add("66621813");
        removeTid.add("66628605");
        removeTid.add("66621812");
        //removeTid.add("66635500");
        removeTid.add("66635511");

        forumId_Forum = new HashMap<>(90000);
        askcenterJdbcTemplate.query("select ForumID, ForumName, TreeCode from ForumBaseInfo", rs -> {
            for(;rs.next();){
                forumId_Forum.put(rs.getString(1), new Forum(rs.getString(2), rs.getString(3)));
            }
            return null;
        });
    }

    @Override
    public void insert(String[] values) {
        String tid = values[1];
        if(removeTid.contains(tid)){
            return;
        }
        // plan_id
        if(values[11] == null){
            values[11] = String.valueOf(Long.MAX_VALUE);
        }
        // production_standards_id
        if(values[12] == null){
            values[12] = String.valueOf(Long.MAX_VALUE);
        }
        Forum page_forum = forumId_Forum.get(values[2]);
        if(page_forum != null){
            values[3] = page_forum.getForumCode();
            values[4] = page_forum.getForumName();
        }
        Forum assign_forum = forumId_Forum.get(values[5]);
        if(assign_forum != null){
            values[6] = page_forum.getForumCode();
            values[7] = page_forum.getForumName();
        }
        produceJdbcTemplate.update("INSERT INTO prod_topics\n" +
                "(id, tid, page_forum, page_forum_tree_code, page_forum_name, assign_forum, assign_forum_tree_code, assign_forum_name, reply_status, audit_status, auth_status, plan_id, production_standards_id, term_num, query, title, `desc`, sex, age, status, create_on, update_time, title_hash, is_deleted, feedback_count)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", values);
    }

    private class Forum{
        private String forumName;
        private String forumCode;

        public Forum(String forumName, String forumCode) {
            this.forumName = forumName;
            this.forumCode = forumCode;
        }

        public String getForumName() {
            return forumName;
        }

        public String getForumCode() {
            return forumCode;
        }
    }
}
