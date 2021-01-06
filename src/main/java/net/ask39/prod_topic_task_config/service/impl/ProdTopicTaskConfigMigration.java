package net.ask39.prod_topic_task_config.service.impl;

import net.ask39.enums.MyConstants;
import net.ask39.service.BaseMigration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 内容生产任务表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-05
 **/
@Lazy
@Service
public class ProdTopicTaskConfigMigration extends BaseMigration<String[]> {
    private static final String SQL_FILE_NAME = "sql/prod_topic_task_config.sql";
    private static final String OUT_PUT_FILE_NAME = "data/prod_topic_task_config.txt";
    private static final OutputStream OUTPUT_STREAM;
    static {
        try {
            OUTPUT_STREAM = new FileOutputStream(OUT_PUT_FILE_NAME);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ProdTopicTaskConfigMigration() {
        super(OUTPUT_STREAM);
    }

    @Override
    public String[] convert(String line) throws Exception {
        return line.split(MyConstants.HT);
    }

    @Override
    public void process(String[] strings) throws Exception {

    }
}
