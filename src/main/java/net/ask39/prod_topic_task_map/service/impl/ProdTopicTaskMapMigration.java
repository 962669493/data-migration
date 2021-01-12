package net.ask39.prod_topic_task_map.service.impl;

import net.ask39.enums.MyConstants;
import net.ask39.service.BaseMigration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * 内容生产任务表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-05
 **/
@Lazy
@Service
public class ProdTopicTaskMapMigration extends BaseMigration<List<String>> {
    public static final String OUT_PUT_FILE_NAME = "output/prod_topic_task_map.txt";
    private static final OutputStream OUTPUT_STREAM;

    static {
        try {
            OUTPUT_STREAM = new FileOutputStream(OUT_PUT_FILE_NAME);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ProdTopicTaskMapMigration() {
        super(OUTPUT_STREAM);
    }

    private Set<String> replyTaskId_authTaskId = new HashSet<>(128);

    @Override
    public List<String> convert(String line) {
        return new ArrayList<>(Arrays.asList(line.split(MyConstants.ESC)));
    }

    @Override
    public List<String> process(List<String> values) {
        String topicId = values.get(0);
        String taskId = values.get(1);
        String type = values.get(2);
        if(Objects.equals("0", taskId)){
            return null;
        }
        return values;
    }
}
