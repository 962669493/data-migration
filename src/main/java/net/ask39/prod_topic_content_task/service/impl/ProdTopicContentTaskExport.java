package net.ask39.prod_topic_content_task.service.impl;

import net.ask39.service.BaseExport;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

/**
 * 帖子表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
@Lazy
@Service
public class ProdTopicContentTaskExport extends BaseExport {
    public static final String INPUT_TOPIC_CONTENT_TASK_TXT = "input/TopicContentTask.txt";
    @Resource(name = "askconfigDataSource")
    DataSource dataSource;

    public ProdTopicContentTaskExport() {
        super(ProdTopicContentTaskMigration.SQL_FILE_NAME, INPUT_TOPIC_CONTENT_TASK_TXT);
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public List<String> convert(List<String> values) {
        return values;
    }
}
