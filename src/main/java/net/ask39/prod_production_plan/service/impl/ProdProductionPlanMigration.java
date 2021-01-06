package net.ask39.prod_production_plan.service.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import net.ask39.enums.MyConstants;
import net.ask39.prod_production_standards.service.impl.ProdProductionStandardsMigration;
import net.ask39.service.BaseMigration;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.*;
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
public class ProdProductionPlanMigration extends BaseMigration<String[]> {
    private static final String SQL_FILE_NAME = "sql/prod_production_plan.sql";
    private static final String OUT_PUT_FILE_NAME = "output/prod_production_plan.txt";
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
    public void writer(String[] strings) throws IOException {
        IOUtils.writeLines(Lists.newArrayList(Joiner.on(MyConstants.ESC).join(strings)), System.getProperty("line.separator"), OUTPUT_STREAM, MyConstants.CHART_SET);
    }

    @Override
    public String[] convert(String line) {
        return line.split("\\|");
    }

    @Override
    public String[] process(String[] strings) {
        // production_standards_id
        strings[12] = String.valueOf(standardsIdMap.get(strings[12]));
        return strings;
    }
}
