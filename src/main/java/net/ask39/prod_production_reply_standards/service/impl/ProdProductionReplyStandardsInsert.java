package net.ask39.prod_production_reply_standards.service.impl;

import com.google.common.base.Joiner;
import net.ask39.enums.MyConstants;
import net.ask39.prod_production_standards.service.impl.ProdProductionStandardsMigration;
import net.ask39.service.BaseInsert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 帖子表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
@Lazy
@Service
public class ProdProductionReplyStandardsInsert extends BaseInsert {
    public ProdProductionReplyStandardsInsert() throws IOException {
        super(ProdProductionStandardsMigration.REPLY_STANDARDS_OUT_PUT_FILE_NAME);
    }
    private final Logger log = LoggerFactory.getLogger(ProdProductionReplyStandardsInsert.class);
    @Resource(name = "produceJdbcTemplate")
    private JdbcTemplate produceJdbcTemplate;
    @Override
    public void insert(String[] values) {
        if(values.length != 15){
            log.warn("存在问题的回复标准：{}", Joiner.on(MyConstants.ESC).useForNull("").join(values));
            return;
        }
        String reply_standards_detail = values[4];
        if(reply_standards_detail != null){
            values[4] = reply_standards_detail.replaceAll(MyConstants.SUB, "\n");
        }
        String title_demo = values[5];
        if(title_demo != null){
            values[5] = title_demo.replaceAll(MyConstants.SUB, "\n");
        }
        String content_demo = values[6];
        if(content_demo != null){
            values[6] = content_demo.replaceAll(MyConstants.SUB, "\n");
        }
        String section = values[7];
        if(section != null){
            values[7] = section.replaceAll(MyConstants.SUB, "\n");
        }
        String scheme_content = values[8];
        if(scheme_content != null){
            values[8] = scheme_content.replaceAll(MyConstants.SUB, "\n");
            values[8] = scheme_content.replaceAll(MyConstants.HT, MyConstants.ESC);
        }
        produceJdbcTemplate.update("INSERT INTO prod_production_reply_standards\n" +
                "(id, production_standards_id, low_word_count, high_word_count, reply_standards_detail, title_demo, content_demo, `section`, scheme_content, need_auth, create_on, create_name, update_time, update_name, is_deleted)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", values);
    }
}
