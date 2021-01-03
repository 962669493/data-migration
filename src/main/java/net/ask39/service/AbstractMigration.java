package net.ask39.service;

import com.google.common.base.Joiner;
import net.ask39.enums.MyConstants;
import net.ask39.utils.MyUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import java.io.*;

/**
 * 帖子数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
public abstract class AbstractMigration implements IMigration<String> {

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

    private final Logger log= LoggerFactory.getLogger(AbstractMigration.class);

    @Override
    public void reader() throws IOException {
        SqlRowSet sqlRowSet = MyUtils.getJdbcTemplate().queryForRowSet(getSql(sqlFileName));
        while(sqlRowSet.next()) {
            SqlRowSetMetaData metaData = sqlRowSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            Object[] row = new Object[columnCount + 1];
            for (int j = 0; j < columnCount; j++) {
                String columnName = metaData.getColumnName(j + 1);
                Object columnValue = sqlRowSet.getObject(columnName);
                row[j] = transfer(columnName, columnValue);
            }
            row[columnCount] = System.getProperty("line.separator");
            Joiner joiner = Joiner.on(MyConstants.ESC).useForNull("");
            writer(joiner.join(row));
        }
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 映射
     *
     * @param columnName  列名
     * @param columnValue 列值
     * @return 转换后的值
     * @author zhangzheng
     * @date 2021/1/3
     */
    protected abstract Object transfer(String columnName, Object columnValue);

    @Override
    public void writer(String t) {
        try {
            IOUtils.write(t, outputStream, MyConstants.CHART_SET);
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
