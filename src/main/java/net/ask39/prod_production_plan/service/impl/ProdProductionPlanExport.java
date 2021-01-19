package net.ask39.prod_production_plan.service.impl;

import net.ask39.prod_topic_content_task.service.impl.ProdTopicContentTaskMigration;
import net.ask39.service.BaseExport;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

/**
 * 帖子表数据迁移
 *
 * @author zhangzheng
 * @date 2021-01-03
 **/
@Lazy
@Service
public class ProdProductionPlanExport extends BaseExport {
    @Resource(name = "askdata4DataSource")
    DataSource dataSource;
    public ProdProductionPlanExport() {
        super(ProdProductionPlanMigration.SQL_FILE_NAME, "input/ProductionPlan.txt");
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public List<String> convert(List<String> values) {
        return values;
    }
}
