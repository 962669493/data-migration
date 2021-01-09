package net.ask39.prod_reply.service.impl;

import net.ask39.enums.MyConstants;
import net.ask39.service.BaseExport;
import net.ask39.service.BaseMigration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * 帖子表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
@Lazy
@Service
public class ProdReplyExport extends BaseExport {
    @Resource(name = "askdata4DataSource")
    DataSource dataSource;
    public ProdReplyExport() {
        super(ProdReplyMigration.SQL_FILE_NAME, "input/IssuePost.txt");
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public List<String> convert(List<String> values) {
        String replyContent = values.get(11);
        if(!StringUtils.isEmpty(replyContent)){
            values.set(11, replyContent.replaceAll("\t", MyConstants.SUB));
        }
        return values;
    }
}
