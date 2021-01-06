package net.ask39.prod_reply_audit_order.service.impl;

import net.ask39.enums.MyConstants;
import net.ask39.prod_reply_audit_order.enums.ReplyAuditStatusEnum;
import net.ask39.service.BaseMigration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 帖子表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
@Lazy
@Service
public class ProdReplyAuditOrderMigration extends BaseMigration<String[]> {
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
    public String[] convert(String line) {
        return line.split(MyConstants.HT);
    }

    @Override
    public String[] process(String[] strings) {
        String auditStatus = strings[8];
        ReplyAuditStatusEnum replyAuditStatusEnum = ReplyAuditStatusEnum.getByValue(Integer.parseInt(auditStatus));
        switch (replyAuditStatusEnum){
            case Pass:
                strings[8] = "8";
                break;
            case Low:
                strings[8] = "3";
                break;
            case Wait:
                strings[8] = "2";
                break;
            case High:
                strings[8] = "8";
                strings[14] = "10";
                break;
            case Unqualified:
                strings[8] = "4";
                break;
            case NullValue:
                strings[8] = "2";
                break;
            default:
                break;
        }
        return strings;
    }
}
