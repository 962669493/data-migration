package net.ask39.prod_topic_task_config.service.impl;

import net.ask39.prod_topic_content_task.service.impl.ProdTopicContentTaskMigration;
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
public class ProdTopicTaskConfigExport extends BaseExport {
    public static final String INPUT_TOPIC_TASK_CONFIG_TXT = "input/TopicTaskConfig.txt";
    @Resource(name = "askconfigDataSource")
    DataSource dataSource;
    public ProdTopicTaskConfigExport() {
        super(ProdTopicTaskConfigMigration.SQL_FILE_NAME, INPUT_TOPIC_TASK_CONFIG_TXT);
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
