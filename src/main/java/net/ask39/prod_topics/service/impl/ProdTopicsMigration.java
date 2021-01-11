package net.ask39.prod_topics.service.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import net.ask39.enums.MyConstants;
import net.ask39.prod_production_standards.service.impl.ProdProductionStandardsMigration;
import net.ask39.prod_topic_task_config.service.impl.ProdTopicTaskConfigMigration;
import net.ask39.prod_topics.enums.TopicContentTaskTypeEnum;
import net.ask39.service.BaseMigration;
import net.ask39.utils.JsonUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
public class ProdTopicsMigration extends BaseMigration<List<String>> {
    public static final String SQL_FILE_NAME = "sql/prod_topics.sql";
    private static final OutputStream OUTPUT_STREAM;
    private static final OutputStream PROD_REPLY_ORDER_OUTPUT_STREAM;
    private static final OutputStream PROD_AUTH_ORDER_OUTPUT_STREAM;
    private static final OutputStream TOPIC_ID_AND_REPLY_TASK_ID_AND_AUTH_TASK_ID_OUTPUT_STREAM;

    static {
        try {
            OUTPUT_STREAM = new FileOutputStream("output/prod_topics.txt");
            PROD_REPLY_ORDER_OUTPUT_STREAM = new FileOutputStream("output/prod_reply_order.txt");
            PROD_AUTH_ORDER_OUTPUT_STREAM = new FileOutputStream("output/prod_auth_order.txt");
            TOPIC_ID_AND_REPLY_TASK_ID_AND_AUTH_TASK_ID_OUTPUT_STREAM = new FileOutputStream("output/topic_id-reply_task_id-auth_task_id.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ProdTopicsMigration() {
        super(OUTPUT_STREAM);
    }

    @Override
    public void after() throws Exception {
        OUTPUT_STREAM.close();
        PROD_REPLY_ORDER_OUTPUT_STREAM.close();
        PROD_AUTH_ORDER_OUTPUT_STREAM.close();
        TOPIC_ID_AND_REPLY_TASK_ID_AND_AUTH_TASK_ID_OUTPUT_STREAM.close();
    }

    private Map<String, String> standardsIdMap;
    private Map<Integer, TopicExt> topicExtMap;
    private Map<String, List<String>> newStandardsIdAndReplyNos;
    private Map<String, String> taskIdAndReplyNo_type;

    @Override
    public void before() throws IOException {
        standardsIdMap = new HashMap<>(64);
        for (String line : IOUtils.readLines(new FileInputStream(ProdProductionStandardsMigration.STANDARDS_ID_OUT_PUT_FILE_NAME), MyConstants.CHART_SET)) {
            String[] split = line.split(MyConstants.ESC);
            standardsIdMap.put(split[0], split[1]);
        }

        topicExtMap = new HashMap<>(2048);
        for (String line : IOUtils.readLines(new FileInputStream("input/TopicExt.txt"), MyConstants.CHART_SET)) {
            String[] values = line.split(MyConstants.ESC);
            TopicExt topicExt = new TopicExt();
            topicExt.setSex(Integer.valueOf(values[1]));
            topicExt.setAge(values[2]);
            topicExtMap.put(Integer.valueOf(values[0]), topicExt);
        }

        newStandardsIdAndReplyNos = new HashMap<>(64);
        for (String line : IOUtils.readLines(new FileInputStream(ProdProductionStandardsMigration.REPLY_STANDARD_MAP_OUT_PUT_FILE_NAME), MyConstants.CHART_SET)) {
            String[] values = line.split(MyConstants.ESC);
            String prodStandardsId = values[0];
            List<String> replyNos = newStandardsIdAndReplyNos.get(prodStandardsId);
            if (replyNos == null) {
                replyNos = new ArrayList<>(3);
            }
            replyNos.add(values[1]);
            newStandardsIdAndReplyNos.put(prodStandardsId, replyNos);
        }

        taskIdAndReplyNo_type = new HashMap<>(256);
        for (String line : IOUtils.readLines(new FileInputStream(ProdTopicTaskConfigMigration.OUT_PUT_FILE_NAME), MyConstants.CHART_SET)) {
            String[] values = line.split(MyConstants.ESC);
            taskIdAndReplyNo_type.put(values[1] + values[9], values[3]);
        }
    }

    private final Logger log = LoggerFactory.getLogger(ProdTopicsMigration.class);

    @Override
    public List<String> convert(String line) {
        return new ArrayList<>(Arrays.asList(line.split(MyConstants.ESC, -1)));
    }

    private void writerTopicIdAndReplyTaskIdAndAuthTaskId(String topicId, Integer replyTaskId, Integer authTaskId) throws IOException {
        IOUtils.writeLines(Lists.newArrayList(Joiner.on(MyConstants.ESC).join(Lists.newArrayList(topicId, replyTaskId, authTaskId))), System.getProperty("line.separator"), TOPIC_ID_AND_REPLY_TASK_ID_AND_AUTH_TASK_ID_OUTPUT_STREAM, MyConstants.CHART_SET);
    }

    @Override
    public List<String> process(List<String> values) throws Exception {
        String topicId = values.get(0);
        String plan_id = values.get(11);
        String production_standards_id = values.get(12);
        String newStandardsId = standardsIdMap.get(production_standards_id);
        values.set(12, newStandardsId);
        TopicExt topicExt = topicExtMap.get(topicId);
        if(topicExt != null){
            // sex
            values.set(13, String.valueOf(topicExt.getSex()));
            // age
            values.set(14, String.valueOf(topicExt.getAge()));
        }
        // title_hash
        values.set(22, String.valueOf(values.get(15).hashCode()));
        // taskId
        String taskIdsStr = values.get(25);
        Map<String, Integer> taskIds = JsonUtils.string2Obj(taskIdsStr, Map.class);
        Integer replyTaskId = null;
        Integer authTaskId = null;
        if(StringUtils.isEmpty(taskIdsStr)){
            //log.warn("帖子没有对应的任务：[{}]", values);
        }else{
            replyTaskId = taskIds.get(String.valueOf(TopicContentTaskTypeEnum.REPLY.getValue()));
            authTaskId = taskIds.get(String.valueOf(TopicContentTaskTypeEnum.AUTH.getValue()));
            writerTopicIdAndReplyTaskIdAndAuthTaskId(topicId, replyTaskId, authTaskId);
        }

        String createTime = values.get(20);
        if(!StringUtils.isEmpty(newStandardsId)){
            List<String> replyNos = newStandardsIdAndReplyNos.get(newStandardsId);
            writerProdReplyOrder(replyNos, topicId, replyTaskId, createTime);
            writerProdAuthOrder(replyNos, topicId, authTaskId, createTime);
        }
        if(StringUtils.isEmpty(plan_id) && StringUtils.isEmpty(newStandardsId)){
            log.warn("帖子[{}]没有对应的生产计划、生产标准", topicId);
        }else if(StringUtils.isEmpty(plan_id)){
            log.warn("帖子[{}]没有对应的生产计划", topicId);
        }else if(StringUtils.isEmpty(newStandardsId)){
            log.warn("帖子[{}]没有对应的生产标准", topicId);
        }
        values.remove(25);
        return values;
    }

    private void writerProdAuthOrder(List<String> replyNos, String topicId, Integer taskId, String topicCreateTime) throws IOException {
        for (int i = 1; i <= replyNos.size(); i++) {
            String type = taskIdAndReplyNo_type.get(taskId + "" +i);
            if(!Objects.equals(type, "4")){
                continue;
            }
            List<Object> data = new ArrayList<>();
            data.add(topicId);
            // TODO 更新授权工单的任务配置ID
            data.add(null);
            data.add(taskId != null ? taskId : Long.MAX_VALUE);
            // TODO 更新授权工单的指派授权人ID
            data.add(null);
            // TODO 更新授权工单的回复ID
            data.add(null);
            // TODO 更新授权工单的回复人ID
            data.add(null);
            data.add(i);
            data.add(replyNos.get(i - 1));
            data.add(3);
            // TODO 更新授权工单的回复积分
            data.add(null);
            data.add(topicCreateTime);
            data.add(topicCreateTime);
            IOUtils.writeLines(Lists.newArrayList(Joiner.on(MyConstants.ESC).useForNull("").join(data)), System.getProperty("line.separator"), PROD_AUTH_ORDER_OUTPUT_STREAM, MyConstants.CHART_SET);
        }
    }

    private void writerProdReplyOrder(List<String> replyNos, String topicId, Integer taskId, String topicCreateTime) throws IOException {
        for (int i = 1; i <= replyNos.size(); i++) {
            List<Object> data = new ArrayList<>();
            data.add(topicId);
            // TODO 更新答题工单的任务配置ID
            data.add(null);
            data.add(i);
            // TODO 更新答题工单的回复积分
            data.add(null);
            data.add(taskId != null ? taskId : Long.MAX_VALUE);
            // TODO 更新答题工单的指派回复人ID
            data.add(null);
            data.add(replyNos.get(i-1));
            data.add(3);
            data.add(topicCreateTime);
            data.add(topicCreateTime);
            IOUtils.writeLines(Lists.newArrayList(Joiner.on(MyConstants.ESC).useForNull("").join(data)), System.getProperty("line.separator"), PROD_REPLY_ORDER_OUTPUT_STREAM, MyConstants.CHART_SET);
        }
    }

    class TopicExt {
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
