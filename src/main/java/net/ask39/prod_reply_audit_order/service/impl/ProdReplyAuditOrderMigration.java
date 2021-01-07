package net.ask39.prod_reply_audit_order.service.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import net.ask39.enums.MyConstants;
import net.ask39.prod_reply_audit_order.enums.ReplyAuditStatusEnum;
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
 * 帖子表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
@Lazy
@Service
public class ProdReplyAuditOrderMigration extends BaseMigration<List<String>> {
    private static final String SQL_FILE_NAME = "sql/prod_reply_audit_order.sql";
    private static final String OUT_PUT_FILE_NAME = "output/prod_reply_audit_order.txt";
    private static final OutputStream OUTPUT_STREAM;
    static {
        try {
            OUTPUT_STREAM = new FileOutputStream(OUT_PUT_FILE_NAME);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ProdReplyAuditOrderMigration() {
        super(OUTPUT_STREAM);
    }

    @Override
    public List<String> convert(String line) {
        return Arrays.asList(line.split(MyConstants.HT));
    }

    @Override
    public List<String> process(List<String> values) {
        String auditStatus = values.get(8);
        ReplyAuditStatusEnum replyAuditStatusEnum = ReplyAuditStatusEnum.getByValue(Integer.parseInt(auditStatus));
        switch (replyAuditStatusEnum){
            case Pass:
                values.set(8, "8");
                break;
            case Low:
                values.set(8, "3");
                break;
            case Wait:
                values.set(8, "2");
                break;
            case High:
                values.set(8, "8");
                values.set(14, "10");
                break;
            case Unqualified:
                values.set(8, "4");
                break;
            case NullValue:
                values.set(8, "2");
                break;
            default:
                break;
        }
        return values;
    }
}
