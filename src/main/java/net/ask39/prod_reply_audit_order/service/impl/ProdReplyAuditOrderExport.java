package net.ask39.prod_reply_audit_order.service.impl;

import net.ask39.prod_topics.service.impl.ProdTopicsMigration;
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
public class ProdReplyAuditOrderExport extends BaseExport {
    public static final String OUTPUT_FILE = "input/ReplyAuditResult.txt";
    @Resource(name = "askdata4DataSource")
    DataSource dataSource;
    public ProdReplyAuditOrderExport() {
        super(ProdReplyAuditOrderMigration.SQL_FILE_NAME, OUTPUT_FILE);
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
