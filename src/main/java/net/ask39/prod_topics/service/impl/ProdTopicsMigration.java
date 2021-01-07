package net.ask39.prod_topics.service.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import net.ask39.enums.MyConstants;
import net.ask39.prod_production_standards.service.impl.ProdProductionStandardsMigration;
import net.ask39.prod_topics.enums.TopicContentTaskTypeEnum;
import net.ask39.service.BaseMigration;
import net.ask39.utils.JsonUtils;
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
public class ProdTopicsMigration extends BaseMigration<List<String>> {
    private static final String SQL_FILE_NAME = "sql/prod_topics.sql";
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
    private Map<String, String> replyStandardsIdAndNeedAuth;

    @Override
    public void before() throws IOException {
        standardsIdMap = new HashMap<>(64);
        for (String line : IOUtils.readLines(new FileInputStream(ProdProductionStandardsMigration.STANDARDS_ID_OUT_PUT_FILE_NAME), MyConstants.CHART_SET)) {
            String[] split = line.split(MyConstants.ESC);
            standardsIdMap.put(split[0], split[1]);
        }

        topicExtMap = new HashMap<>(2048);
        for (String line : IOUtils.readLines(new FileInputStream("output/TopicExt.txt"), MyConstants.CHART_SET)) {
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

        replyStandardsIdAndNeedAuth = new HashMap<>(64);
        for (String line : IOUtils.readLines(new FileInputStream(ProdProductionStandardsMigration.REPLY_STANDARD_MAP_OUT_PUT_FILE_NAME), MyConstants.CHART_SET)) {
            String[] values = line.split(MyConstants.ESC);
            replyStandardsIdAndNeedAuth.put(values[0], values[8]);
        }

    }

    @Override
    public List<String> convert(String line) {
        return Arrays.asList(line.split(MyConstants.HT));
    }

    private void writerTopicIdAndReplyTaskIdAndAuthTaskId(String topicId, Integer replyTaskId, Integer authTaskId) throws IOException {
        IOUtils.writeLines(Lists.newArrayList(Joiner.on(MyConstants.HT).join(Lists.newArrayList(topicId, replyTaskId, authTaskId))), System.getProperty("line.separator"), TOPIC_ID_AND_REPLY_TASK_ID_AND_AUTH_TASK_ID_OUTPUT_STREAM, MyConstants.CHART_SET);
    }

    @Override
    public List<String> process(List<String> values) throws Exception {
        String topicId = values.get(0);
        // production_standards_id
        String newStandardsId = standardsIdMap.get(values.get(8));
        values.set(8, newStandardsId);
        TopicExt topicExt = topicExtMap.get(topicId);
        // sex
        values.set(13, String.valueOf(topicExt.getSex()));
        // age
        values.set(14, String.valueOf(topicExt.getAge()));
        // title_hash
        values.set(18, String.valueOf(values.get(11).hashCode()));

        // taskId
        Map<String, Integer> taskIds = JsonUtils.string2Obj(values.get(21), Map.class);
        Integer replyTaskId = taskIds.get(String.valueOf(TopicContentTaskTypeEnum.REPLY.getValue()));
        Integer authTaskId = taskIds.get(String.valueOf(TopicContentTaskTypeEnum.AUTH.getValue()));
        writerTopicIdAndReplyTaskIdAndAuthTaskId(topicId, replyTaskId, authTaskId);
        String createTime = values.get(16);
        writerProdReplyOrder(newStandardsIdAndReplyNos.get(newStandardsId), topicId, replyTaskId, createTime);
        writerProdAuthOrder(newStandardsIdAndReplyNos.get(newStandardsId), topicId, authTaskId, createTime);
        values.remove(21);
        return values;
    }

    private void writerProdAuthOrder(List<String> replyNos, String topicId, Integer taskId, String topicCreateTime) throws IOException {
        for (int i = 1; i <= replyNos.size(); i++) {
            String replyStandardsId = replyNos.get(i - 1);
            String needAuth = replyStandardsIdAndNeedAuth.get(replyStandardsId);
            if(!Objects.equals(needAuth, "1")){
                continue;
            }
            List<Object> data = new ArrayList<>();
            data.add(topicId);
            // TODO 更新授权工单的任务配置ID
            data.add(null);
            data.add(taskId);
            // TODO 更新授权工单的指派授权人ID
            data.add(null);
            // TODO 更新授权工单的回复ID
            data.add(null);
            // TODO 更新授权工单的回复人ID
            data.add(null);
            data.add(i);
            data.add(replyStandardsId);
            data.add(3);
            // TODO 更新授权工单的回复积分
            data.add(null);
            data.add(topicCreateTime);
            data.add(topicCreateTime);
            IOUtils.writeLines(Lists.newArrayList(Joiner.on(MyConstants.HT).join(data)), System.getProperty("line.separator"), PROD_AUTH_ORDER_OUTPUT_STREAM, MyConstants.CHART_SET);
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
            data.add(taskId);
            // TODO 更新答题工单的指派回复人ID
            data.add(null);
            data.add(replyNos.get(i-1));
            data.add(3);
            data.add(topicCreateTime);
            data.add(topicCreateTime);
            IOUtils.writeLines(Lists.newArrayList(Joiner.on(MyConstants.HT).join(data)), System.getProperty("line.separator"), PROD_REPLY_ORDER_OUTPUT_STREAM, MyConstants.CHART_SET);
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
