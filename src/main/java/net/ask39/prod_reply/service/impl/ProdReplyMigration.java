package net.ask39.prod_reply.service.impl;

import net.ask39.enums.MyConstants;
import net.ask39.service.BaseMigration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

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
public class ProdReplyMigration extends BaseMigration<List<String>> {
    public static final String SQL_FILE_NAME = "sql/prod_reply.sql";
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
    public List<String> convert(String line) {
        return Arrays.asList(line.split(MyConstants.ESC));
    }

    @Override
    public List<String> process(List<String> values) {
        return values;
    }
}
