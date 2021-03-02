package net.ask39.prod_topics.service.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import net.ask39.enums.MyConstants;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
public class TopicExtExport {
    @Resource(name = "askdata4DataSource")
    private DataSource askdata4DataSource;
    @Resource(name = "askconfigDataSource")
    private DataSource askconfigDataSource;

    private Connection askdata4Connection;
    private Connection askconfigConnection;
    private FileOutputStream outputStream;

    private final Logger log = LoggerFactory.getLogger(TopicExtExport.class);

    private void before() throws SQLException, FileNotFoundException {
        askdata4Connection = askdata4DataSource.getConnection();
        askconfigConnection = askconfigDataSource.getConnection();
        outputStream = new FileOutputStream(ProdTopicsInsert.TOPIC_EXT);
    }

    public void export() throws SQLException, IOException {
        before();
        int total = 0;
        PreparedStatement preparedStatement = askdata4Connection.prepareStatement("select tid from AuthTopics");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Integer> tids = new ArrayList<>(1000);
        for (int i = 0, k = 0; resultSet.next(); i++) {
            tids.add(resultSet.getInt(1));
            if (i % 1000 == 0) {
                total += exportTopicExtByTid(tids);
                tids = new ArrayList<>(1000);
            }
        }
        total += exportTopicExtByTid(tids);
        log.info("完成，共导出[{}]条帖子额外信息", total);
    }

    private int exportTopicExtByTid(List<Integer> tids) throws IOException, SQLException {
        String sql = "SELECT tid, sex, age from TopicExt" +
                " where tid in (" + Joiner.on(",").join(tids) + ")";
        PreparedStatement preparedStatement = askconfigConnection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int j = 0;
        int columnCount = metaData.getColumnCount();
        for (int k = 0; resultSet.next(); j++) {
            List<Object> data = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                String value = resultSet.getString(i);
                data.add(value);
            }
            IOUtils.writeLines(Lists.newArrayList(Joiner.on(MyConstants.ESC).useForNull("").join(data)), System.getProperty("line.separator"), outputStream, MyConstants.CHART_SET);
            j++;
        }
        log.info("导出[{}]条帖子额外信息", j);
        return j;
    }
}
