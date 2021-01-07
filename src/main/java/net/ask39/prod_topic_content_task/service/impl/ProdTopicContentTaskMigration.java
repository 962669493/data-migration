package net.ask39.prod_topic_content_task.service.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import net.ask39.enums.MyConstants;
import net.ask39.service.BaseMigration;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * 内容生产任务表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-05
 **/
@Lazy
@Service
public class ProdTopicContentTaskMigration extends BaseMigration<List<String>> {
    private static final String SQL_FILE_NAME = "sql/prod_topic_content_task.sql";
    private static final String OUT_PUT_FILE_NAME = "output/prod_topic_content_task.txt";
    private static final OutputStream OUTPUT_STREAM;
    static {
        try {
            OUTPUT_STREAM = new FileOutputStream(OUT_PUT_FILE_NAME);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ProdTopicContentTaskMigration() {
        super(OUTPUT_STREAM);
    }

    @Override
    public List<String> convert(String line) {
        return Arrays.asList(line.split(MyConstants.HT));
    }

    @Override
    public List<String> process(List<String> values) {
        return values;
    }
}
