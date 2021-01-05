package net.ask39.prod_topics.service.impl;

import net.ask39.prod_topics.entity.TopicExt;
import net.ask39.service.AbstractMigration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * 帖子表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
@Lazy
@Service
public class ProdTopicsMigration extends AbstractMigration {
    @Resource(name = "askconfigJdbcTemplate")
    private JdbcTemplate askconfigJdbcTemplate;

    @Resource(name = "askdata4JdbcTemplate")
    private JdbcTemplate askdata4JdbcTemplate;

    private static final String SQL_FILE_NAME = "sql/prod_topics.sql";

    private static final String OUT_PUT_FILE_NAME = "data/prod_topics.txt";

    public ProdTopicsMigration() throws FileNotFoundException {
        super(SQL_FILE_NAME, OUT_PUT_FILE_NAME);
    }

    Map<Integer, TopicExt> topicExtMap;

    @Override
    protected void before(){
        List<TopicExt> topicExtList = askconfigJdbcTemplate.queryForList("select tid, sex, age from TopicExt", TopicExt.class);
        this.topicExtMap = new HashMap<>(topicExtList.size());
        for(TopicExt topicExt:topicExtList){
            topicExtMap.put(topicExt.getTid(), topicExt);
        }
    }

    @Override
    protected void convert(Map<String, Object> row) {
        Object tid = row.get("tid");
        TopicExt topicExt = topicExtMap.get(tid);

        for(Map.Entry<String, Object> entry : row.entrySet()){
            String columnName = entry.getKey();
            Object columnValue = entry.getValue();
            if(Objects.equals(columnName, "sex") && topicExt != null){
                row.put(columnName, topicExt.getSex());
            }
            if(Objects.equals(columnName, "age") && topicExt != null){
                row.put(columnName, topicExt.getAge());
            }
            if(Objects.equals(columnName, "title_hash")){
                row.put(columnName, row.get("title").hashCode());
            }
        }
    }

    @Override
    protected JdbcTemplate getJdbcTemplate() {
        return askdata4JdbcTemplate;
    }
}
