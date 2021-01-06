package net.ask39.prod_topics.service.impl;

import net.ask39.enums.MyConstants;
import net.ask39.prod_production_standards.service.impl.ProdProductionStandardsMigration;
import net.ask39.service.BaseMigration;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

/**
 * 帖子表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
@Lazy
@Service
public class ProdTopicsMigration extends BaseMigration<String[]> {
    private static final String SQL_FILE_NAME = "sql/prod_topics.sql";
    private static final String OUT_PUT_FILE_NAME = "data/prod_topics.txt";
    private static final OutputStream OUTPUT_STREAM;
    static {
        try {
            OUTPUT_STREAM = new FileOutputStream(OUT_PUT_FILE_NAME);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ProdTopicsMigration() {
        super(OUTPUT_STREAM);
    }

    private Map<String, Integer> standardsIdMap;
    private Map<Integer, TopicExt> topicExtMap;

    @Override
    public void before() throws IOException {
        standardsIdMap = new HashMap<>(256);
        for(String line:IOUtils.readLines(new FileInputStream(ProdProductionStandardsMigration.STANDARDS_ID_OUT_PUT_FILE_NAME), MyConstants.CHART_SET)){
            String[] split = line.split(MyConstants.ESC);
            standardsIdMap.put(split[0], Integer.valueOf(split[1]));
        }

        topicExtMap = new HashMap<>(2048);
        for(String line:IOUtils.readLines(new FileInputStream("data/TopicExt.txt"), MyConstants.CHART_SET)){
            String[] values = line.split(MyConstants.ESC);
            TopicExt topicExt = new TopicExt();
            topicExt.setSex(Integer.valueOf(values[1]));
            topicExt.setAge(values[2]);
            topicExtMap.put(Integer.valueOf(values[0]), topicExt);
        }
    }

    @Override
    public String[] convert(String line) throws Exception {
        return line.split(MyConstants.HT);
    }

    @Override
    public void process(String[] strings) throws Exception {
        TopicExt topicExt = topicExtMap.get(strings[0]);
        // sex
        strings[13] = String.valueOf(topicExt.getSex());
        // age
        strings[14] = topicExt.getAge();
        // title_hash
        strings[18] = String.valueOf(strings[11].hashCode());
    }

    class TopicExt{
        private Integer sex;
        private String age;

        public Integer getSex() {
            return sex;
        }

        public void setSex(Integer sex) {
            this.sex = sex;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }
}
