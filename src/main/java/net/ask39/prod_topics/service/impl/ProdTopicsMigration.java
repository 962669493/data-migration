package net.ask39.prod_topics.service.impl;

import net.ask39.service.AbstractMigration;
import net.ask39.utils.MyUtils;

import java.io.FileNotFoundException;
import java.sql.Timestamp;

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
    protected Object convert(String columnName, Object columnValue) {
        if(columnValue instanceof Timestamp){
            Timestamp timestamp = (Timestamp) columnValue;
            return MyUtils.ldt2Str(timestamp.toLocalDateTime());
        }
        return columnValue;
    }
}
