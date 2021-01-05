package net.ask39.prod_topics.service.impl;

import net.ask39.enums.MyConstants;
import net.ask39.prod_production_standards.service.impl.ProdProductionStandardsMigration;
import net.ask39.prod_topics.entity.TopicExt;
import net.ask39.service.AbstractMigration;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    private Map<String, Integer> standardsIdMap;
    private Map<Integer, TopicExt> topicExtMap;

    @Override
    protected void before() throws IOException {
        standardsIdMap = new HashMap<>(256);
        List<String> readLines = IOUtils.readLines(new FileInputStream(ProdProductionStandardsMigration.STANDARDS_ID_OUT_PUT_FILE_NAME), MyConstants.CHART_SET);
        for(String line:readLines){
            String[] split = line.split(MyConstants.ESC);
            standardsIdMap.put(split[0], Integer.valueOf(split[1]));
        }

        SqlRowSet sqlRowSet = askconfigJdbcTemplate.queryForRowSet("select tid, sex, age from TopicExt");
        this.topicExtMap = new HashMap<>(1024);
        while (sqlRowSet.next()){
            int tid = sqlRowSet.getInt("tid");
            int sex = sqlRowSet.getInt("sex");
            String age = sqlRowSet.getString("age");
            TopicExt topicExt = new TopicExt();
            topicExt.setSex(sex);
            topicExt.setAge(age);
            topicExtMap.put(tid, topicExt);
        }
    }

    @Override
    protected void convert(Map<String, Object> row) {
        row.put("title_hash", row.get("title").hashCode());

        Object oldId = row.get("production_standards_id");
        row.put("production_standards_id", standardsIdMap.get(oldId));

        Object tid = row.get("tid");
        TopicExt topicExt = topicExtMap.get(tid);
        if(topicExt != null){
            row.put("sex", topicExt.getSex());
            row.put("age", topicExt.getAge());
        }
    }

    @Override
    protected JdbcTemplate getJdbcTemplate() {
        return askdata4JdbcTemplate;
    }
}
