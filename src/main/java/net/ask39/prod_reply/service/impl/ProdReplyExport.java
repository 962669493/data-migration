package net.ask39.prod_reply.service.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import net.ask39.enums.MyConstants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 帖子表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
@Lazy
@Service
public class ProdReplyExport {
    @Resource(name = "askdata4DataSource")
    private DataSource askdata4DataSource;
    @Resource(name = "askdata5DataSource")
    private DataSource askdata5DataSource;

    private Connection askdata4Connection;
    private Connection askdata5Connection;
    private FileOutputStream outputStream;

    private final Logger log = LoggerFactory.getLogger(ProdReplyExport.class);

    private void before() throws SQLException, FileNotFoundException {
        askdata4Connection = askdata4DataSource.getConnection();
        askdata5Connection = askdata5DataSource.getConnection();
        outputStream = new FileOutputStream(ProdReplyInsert.INPUT_ISSUE_POST_TXT);
    }

    public void export() throws SQLException, IOException {
        before();
        int total = 0;
        PreparedStatement preparedStatement = askdata4Connection.prepareStatement("select tid from AuthTopics where createOn <= '"+ MyConstants.END_TIME + "'");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Integer> tids = new ArrayList<>(1000);
        for (int i = 0, k = 0; resultSet.next(); i++) {
            tids.add(resultSet.getInt(1));
            if (i % 1000 == 0) {
                total += exportReplyByTid(tids);
                tids = new ArrayList<>(1000);
            }
        }
        total += exportReplyByTid(tids);
        log.info("完成，共导出[{}]条回复", total);
    }

    private int exportReplyByTid(List<Integer> tids) throws IOException, SQLException {
        String sql = FileUtils.readFileToString(new File(ProdReplyMigration.SQL_FILE_NAME), StandardCharsets.UTF_8) +
                " and t1.tid in (" + Joiner.on(",").join(tids) + ")";
        //log.info("sql：{}", sql);
        PreparedStatement preparedStatement = askdata5Connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int j = 0;
        int columnCount = metaData.getColumnCount();
        for (int k = 0; resultSet.next(); j++) {
            List<Object> data = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                String value = resultSet.getString(i);
                if (i == 10 && !StringUtils.isEmpty(value)) {
                    String v1 = value.replaceAll("\r\n", MyConstants.SUB);
                    String v2 = v1.replaceAll("\n", MyConstants.SUB);
                    data.add(v2.replaceAll("\r", MyConstants.SUB));
                } else {
                    data.add(value);
                }
            }
            IOUtils.writeLines(Lists.newArrayList(Joiner.on(MyConstants.ESC).useForNull("").join(data)), System.getProperty("line.separator"), outputStream, MyConstants.CHART_SET);
            j++;
        }
        log.info("导出[{}]条回复", j);
        return j;
    }
}
