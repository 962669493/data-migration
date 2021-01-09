package net.ask39.prod_topic_content_task.service.impl;

import net.ask39.enums.MyConstants;
import net.ask39.prod_reply.service.impl.ProdReplyMigration;
import net.ask39.service.BaseExport;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    @Resource(name = "askconfigDataSource")
    DataSource dataSource;
    public ProdTopicContentTaskExport() {
        super(ProdTopicContentTaskMigration.SQL_FILE_NAME, "input/TopicContentTask.txt");
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
