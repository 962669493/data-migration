package net.ask39.prod_reply.service.impl;

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

/**
 * 帖子表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
@Lazy
@Service
public class ProdReplyMigration extends BaseMigration<String[]> {
    private static final String SQL_FILE_NAME = "sql/prod_reply.sql";
    private static final String OUT_PUT_FILE_NAME = "output/prod_reply.txt";
    private static final OutputStream OUTPUT_STREAM;
    static {
        try {
            OUTPUT_STREAM = new FileOutputStream(OUT_PUT_FILE_NAME);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ProdReplyMigration() {
        super(OUTPUT_STREAM);
    }

    @Override
    public String[] convert(String line) {
        return line.split(MyConstants.HT);
    }

    @Override
    public void writer(String[] strings) throws IOException {
        IOUtils.writeLines(Lists.newArrayList(Joiner.on(MyConstants.ESC).join(strings)), System.getProperty("line.separator"), OUTPUT_STREAM, MyConstants.CHART_SET);
    }

    @Override
    public String[] process(String[] strings) {
        return strings;
    }
}
