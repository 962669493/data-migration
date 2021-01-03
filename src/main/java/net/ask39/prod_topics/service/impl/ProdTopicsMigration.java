package net.ask39.prod_topics.service.impl;

import net.ask39.service.AbstractMigration;

import java.io.FileNotFoundException;

/**
 * 帖子表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
public class ProdTopicsMigration extends AbstractMigration {
    public ProdTopicsMigration(String sqlFileName, String outPutFileName) throws FileNotFoundException {
        super(sqlFileName, outPutFileName);
    }

    @Override
    protected Object transfer(String columnName, Object columnValue) {
        return columnValue;
    }
}
