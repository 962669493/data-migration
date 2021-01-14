package net.ask39.prod_production_plan.service.impl;

import net.ask39.enums.MyConstants;
import net.ask39.prod_production_standards.service.impl.ProdProductionStandardsMigration;
import net.ask39.service.BaseInsert;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 帖子表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
@Lazy
@Service
public class ProdProductionPlanInsert extends BaseInsert {
    public ProdProductionPlanInsert() throws IOException {
        super("input/ProductionPlan.txt");
    }

    @Resource(name = "produceJdbcTemplate")
    private JdbcTemplate produceJdbcTemplate;

    private Map<String, Integer> standardsIdMap;

    @Override
    public void before() throws Exception {
        standardsIdMap = new HashMap<>(256);
        List<String> readLines = IOUtils.readLines(new FileInputStream(ProdProductionStandardsMigration.STANDARDS_ID_OUT_PUT_FILE_NAME), MyConstants.CHART_SET);
        for(String line:readLines){
            String[] split = line.split(MyConstants.ESC);
            standardsIdMap.put(split[0], Integer.valueOf(split[1]));
        }
    }

    @Override
    public void insert(String[] values) {
        values[12] = String.valueOf(standardsIdMap.get(values[12]));
        String topics_num = values[3];
        String reply_schedule = values[6];
        if(!StringUtils.isEmpty(reply_schedule)){
            values[6] = String.valueOf(new BigDecimal(reply_schedule).divide(new BigDecimal(100)).multiply(new BigDecimal(topics_num)).setScale(0, BigDecimal.ROUND_DOWN).intValue());
        }
        String audit_schedule = values[7];
        if(!StringUtils.isEmpty(audit_schedule)){
            values[7] = String.valueOf(new BigDecimal(audit_schedule).divide(new BigDecimal(100)).multiply(new BigDecimal(topics_num)).setScale(0, BigDecimal.ROUND_DOWN).intValue());
        }
        String authorize_schedule = values[8];
        if(!StringUtils.isEmpty(authorize_schedule)){
            values[8] = String.valueOf(new BigDecimal(authorize_schedule).divide(new BigDecimal(100)).multiply(new BigDecimal(topics_num)).setScale(0, BigDecimal.ROUND_DOWN).intValue());
        }
        produceJdbcTemplate.update("INSERT INTO prod_production_plan\n" +
                "(id, term_num, demand_position, topics_num, prod_success, done_time, reply_schedule, audit_schedule, authorize_schedule, funder_id, funder_name, create_on, production_standards_id, update_time, is_deleted)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", values);
    }
}
