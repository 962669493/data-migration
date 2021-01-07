package net.ask39.service;

import net.ask39.enums.MyConstants;
import net.ask39.prod_production_plan.service.impl.ProdProductionPlanMigration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author zhangzheng
 * @date 2021-01-06
 **/
public abstract class BaseInsert {
    private final LineIterator lineIterator;

    protected BaseInsert(String fileName) throws IOException {
        lineIterator = FileUtils.lineIterator(new File(fileName), MyConstants.CHART_SET.toString());
    }

    protected BaseInsert(String fileName, String charsetName) throws IOException {
        lineIterator = FileUtils.lineIterator(new File(fileName), charsetName);
    }

    public void insert() throws Exception {
        before();
        while (true) {
            String row = getRow();
            if (row != null) {
                String[] values = convert(row);
                insert(values);
            } else {
                lineIterator.close();
                break;
            }
        }

    }

    protected abstract void insert(String[] values);
    protected void before() throws Exception{

    }

    public String getRow() {
        if (lineIterator.hasNext()) {
            return lineIterator.next();
        }
        return null;
    }

    public String[] convert(String row) {
        String[] values = row.split(MyConstants.HT);
        for (int i = 0; i < values.length; i++) {
            if (Objects.equals(values[i], "")) {
                values[i] = null;
            }
            if (Objects.equals(values[i], "NULL")) {
                values[i] = null;
            }
        }
        return values;
    }
}
