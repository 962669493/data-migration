package net.ask39.prod_reply.service.impl;

import net.ask39.service.AbstractMigration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * 帖子表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
@Lazy
@Service
public class ProdReplyMigration extends AbstractMigration {
    @Resource(name = "askdata4JdbcTemplate")
    private JdbcTemplate askdata4JdbcTemplate;
    @Resource(name = "askcenterJdbcTemplate")
    private JdbcTemplate askcenterJdbcTemplate;

    private static final String SQL_FILE_NAME = "sql/prod_reply.sql";

    private static final String OUT_PUT_FILE_NAME = "data/prod_reply.txt";

    public ProdReplyMigration() throws FileNotFoundException {
        super(SQL_FILE_NAME, OUT_PUT_FILE_NAME);
    }

    @Override
    protected void before() throws Exception {

    }

    @Override
    protected void convert(Map<String, Object> row) {
        List<Map<String, Object>> mapList = askcenterJdbcTemplate.queryForList("SELECT replyId, replyno, score FROM AuthTopicPool2020 where replyId = ?", row.get("id"));
        if(!CollectionUtils.isEmpty(mapList)){
            Map<String, Object> objectMap = mapList.get(0);
            row.put("score", objectMap.get("score"));
            row.put("reply_no", objectMap.get("replyno"));
        }
    }

    @Override
    protected JdbcTemplate getJdbcTemplate() {
        return askdata4JdbcTemplate;
    }
}
