package net.ask39.service;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import net.ask39.enums.MyConstants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangzheng
 * @date 2021-01-08
 **/
public abstract class BaseExport {
    private final Logger log = LoggerFactory.getLogger(BaseExport.class);
    private final String sqlFile;
    private final String outputFile;

    public BaseExport(String sqlFile, String outputFile) {
        this.sqlFile = sqlFile;
        this.outputFile = outputFile;
    }

    public void export() throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        Connection connection = getDataSource().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FileUtils.readFileToString(new File(sqlFile), MyConstants.CHART_SET));
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.setFetchSize(MyConstants.FETCH_SIZE);
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        log.info("开始写入数据到：[{}]", outputFile);
        for (int j = 0, k = 0; resultSet.next(); j++) {
            if (j % MyConstants.FETCH_SIZE == 0) {
                k++;
                log.info("开始第[{}]个批次的写入", k);
            }
            List<String> values = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                String value = resultSet.getString(i);
                values.add(value);
            }
            IOUtils.writeLines(Lists.newArrayList(Joiner.on(MyConstants.ESC).useForNull("").join(convert(values))), System.getProperty("line.separator"), fileOutputStream, MyConstants.CHART_SET);
        }
    }

    public abstract DataSource getDataSource();

    public abstract List<String> convert(List<String> values);
}
