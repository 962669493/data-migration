package net.ask39.prod_production_plan.service.impl;

import net.ask39.enums.MyConstants;
import net.ask39.prod_production_standards.service.impl.ProdProductionStandardsMigration;
import net.ask39.service.AbstractMigration;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
public class ProdProductionPlanMigration extends AbstractMigration {
    @Resource(name = "askconfigJdbcTemplate")
    private JdbcTemplate askconfigJdbcTemplate;

    @Resource(name = "askdata4JdbcTemplate")
    private JdbcTemplate askdata4JdbcTemplate;

    private static final String SQL_FILE_NAME = "sql/prod_production_plan.sql";

    private static final String OUT_PUT_FILE_NAME = "data/prod_production_plan.txt";

    public ProdProductionPlanMigration() throws FileNotFoundException {
        super(SQL_FILE_NAME, OUT_PUT_FILE_NAME);
    }

    private Map<String, Integer> standardsIdMap;

    @Override
    protected void before() throws Exception {
        standardsIdMap = new HashMap<>(256);
        List<String> readLines = IOUtils.readLines(new FileInputStream(ProdProductionStandardsMigration.STANDARDS_ID_OUT_PUT_FILE_NAME), MyConstants.CHART_SET);
        for(String line:readLines){
            String[] split = line.split(MyConstants.ESC);
            standardsIdMap.put(split[0], Integer.valueOf(split[1]));
        }
    }

    @Override
    protected void convert(Map<String, Object> row) {
        Object oldId = row.get("production_standards_id");
        row.put("production_standards_id", standardsIdMap.get(oldId));
    }

    @Override
    protected JdbcTemplate getJdbcTemplate() {
        return askdata4JdbcTemplate;
    }
}
