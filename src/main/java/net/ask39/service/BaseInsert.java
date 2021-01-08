package net.ask39.service;

import com.google.common.base.Stopwatch;
import net.ask39.enums.MyConstants;
import net.ask39.prod_production_plan.service.impl.ProdProductionPlanMigration;
import net.ask39.prod_topics.service.impl.ProdTopicsInsert;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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

    private final Logger log = LoggerFactory.getLogger(BaseInsert.class);

    public void insert() throws Exception {
        Stopwatch watch = Stopwatch.createStarted();
        int i = 0;
        before();
        while (true) {
            String row = getRow();
            if (row != null) {
                i++;
                String[] values = convert(row);
                insert(values);
            } else {
                lineIterator.close();
                break;
            }
        }
        log.info("[{}]insert完成，共[{}]行，耗时[{}]秒", this.getClass().getSimpleName(), i, watch.elapsed(TimeUnit.SECONDS));
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
        String[] values = row.split(MyConstants.HT, -1);
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
