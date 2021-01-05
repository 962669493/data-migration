package net.ask39.service;

import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import net.ask39.enums.MyConstants;
import net.ask39.utils.MyUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
public abstract class AbstractMigration {

    /**
     * 查询sql的文件名称
     */
    private final String sqlFileName;
    /**
     * 转换后输出文件的位置
     */
    private final OutputStream outputStream;

    public AbstractMigration(String sqlFileName, String outPutFileName) throws FileNotFoundException {
        this.sqlFileName = sqlFileName;
        this.outputStream = new FileOutputStream(outPutFileName);
    }

    private final Logger log = LoggerFactory.getLogger(AbstractMigration.class);

    public void migration() throws IOException {
        log.info("开始运行class[{}]", this.getClass().getSimpleName());
        before();
        Stopwatch stopwatch = Stopwatch.createStarted();

        SqlRowSet sqlRowSet = getJdbcTemplate().queryForRowSet(getSql(sqlFileName));
        SqlRowSetMetaData metaData = sqlRowSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 0, k = 0; sqlRowSet.next(); i++) {
            if (i % MyConstants.FETCH_SIZE == 0) {
                k++;
                outputStream.flush();
                log.info("开始进行第[{}]个批次的迁移", k);
            }

            Map<String, Object> row = new LinkedHashMap<>(columnCount);
            for (int j = 0; j < columnCount; j++) {
                String columnName = metaData.getColumnName(j + 1);
                Object columnValue = sqlRowSet.getObject(columnName);
                if (columnValue instanceof Timestamp) {
                    Timestamp timestamp = (Timestamp) columnValue;
                    row.put(columnName, MyUtils.ldt2Str(timestamp.toLocalDateTime()));
                } else {
                    row.put(columnName, columnValue);
                }
            }
            convert(row);
            ArrayList<Object> values = new ArrayList<>(row.values());
            values.add(System.getProperty("line.separator"));

            Joiner joiner = Joiner.on(MyConstants.ESC).useForNull("");
            writer(joiner.join(values));
        }

        log.info("运行class[{}]结束，耗时[{}]秒", this.getClass().getName(), stopwatch.elapsed(TimeUnit.SECONDS));
        outputStream.close();
    }

    /**
     * 映射
     *
     * @param row 列名
     * @return 转换后的值
     * @author zhangzheng
     * @date 2021/1/3
     */
    protected abstract void convert(Map<String, Object> row);

    /**
     * JdbcTemplate
     *
     * @return JdbcTemplate
     * @author zhangzheng
     * @date 2021/1/3
     */
    protected abstract JdbcTemplate getJdbcTemplate();

    /**
     * 开始前的准备工作
     *
     * @author zhangzheng
     * @date 2021/1/3
     */
    protected abstract void before();

    public void writer(String t) {
        try {
            IOUtils.writeLines(Lists.newArrayList(t), System.getProperty("line.separator"), outputStream, MyConstants.CHART_SET);
        } catch (IOException e) {
            throw new RuntimeException("写入数据出错");
        }
    }

    private String getSql(String fileName) {
        ClassPathResource classPathResource = new ClassPathResource(fileName);
        try {
            return IOUtils.toString(classPathResource.getInputStream(), MyConstants.CHART_SET);
        } catch (IOException e) {
            throw new RuntimeException("读取sql文件：[" + fileName + "]出错");
        }
    }
}
