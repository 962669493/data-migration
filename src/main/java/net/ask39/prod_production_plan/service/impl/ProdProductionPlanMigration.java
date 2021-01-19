package net.ask39.prod_production_plan.service.impl;

import net.ask39.enums.MyConstants;
import net.ask39.prod_production_standards.service.impl.ProdProductionStandardsMigration;
import net.ask39.service.BaseMigration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

/**
 * 帖子表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
@Lazy
@Service
public class ProdProductionPlanMigration extends BaseMigration<List<String>> {
    public static final String SQL_FILE_NAME = "sql/prod_production_plan.sql";
    public static final String OUT_PUT_FILE_NAME = "output/prod_production_plan.txt";
    private static final OutputStream OUTPUT_STREAM;
    static {
        try {
            OUTPUT_STREAM = new FileOutputStream(OUT_PUT_FILE_NAME);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ProdProductionPlanMigration() {
        super(OUTPUT_STREAM);
    }

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
    public List<String> convert(String line) {
        return Arrays.asList(line.split(MyConstants.ESC, -1));
    }

    @Override
    public List<String> process(List<String> values) {
        // production_standards_id
        Integer production_standards_id = standardsIdMap.get(values.get(12));
        if(production_standards_id != null){
            values.set(12, String.valueOf(production_standards_id));
        }else{
            values.set(12, "");
        }
        return values;
    }

    @Resource(name = "produceJdbcTemplate")
    private JdbcTemplate produceJdbcTemplate;

    public void insert() throws Exception{
        try(LineIterator it = FileUtils.lineIterator(new File(OUT_PUT_FILE_NAME), MyConstants.CHART_SET.toString())) {
            while (it.hasNext()) {
                String values = it.nextLine().replaceAll(MyConstants.ESC, ",");
                produceJdbcTemplate.update("insert into prod_production_plan values (" + values +")");
            }
        }
    }
}
