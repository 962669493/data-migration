package net.ask39;

import net.ask39.prod_production_plan.service.impl.ProdProductionPlanInsert;
import net.ask39.prod_production_plan.service.impl.ProdProductionPlanMigration;
import net.ask39.prod_production_reply_standard_map.service.impl.ProdProductionReplyStandardMapInsert;
import net.ask39.prod_production_reply_standards.service.impl.ProdProductionReplyStandardsInsert;
import net.ask39.prod_production_standards.service.impl.ProdProductionStandardsInsert;
import net.ask39.prod_production_standards.service.impl.ProdProductionStandardsMigration;
import net.ask39.prod_reply.service.impl.ProdReplyExport;
import net.ask39.prod_reply.service.impl.ProdReplyInsert;
import net.ask39.prod_reply.service.impl.ProdReplyMigration;
import net.ask39.prod_reply_audit_order.service.impl.ProdReplyAuditOrderInsert;
import net.ask39.prod_reply_auth_order.service.impl.ProdAuthOrderInsert;
import net.ask39.prod_reply_order.service.impl.ProdReplyOrderInsert;
import net.ask39.prod_topic_content_task.service.impl.ProdTopicContentTaskExport;
import net.ask39.prod_topic_content_task.service.impl.ProdTopicContentTaskInsert;
import net.ask39.prod_topic_content_task.service.impl.ProdTopicContentTaskMigration;
import net.ask39.prod_topic_task_config.service.impl.ProdTopicTaskConfigInsert;
import net.ask39.prod_topic_task_config.service.impl.ProdTopicTaskConfigMigration;
import net.ask39.prod_topics.service.impl.ProdTopicExport;
import net.ask39.prod_topics.service.impl.ProdTopicsInsert;
import net.ask39.prod_topics.service.impl.ProdTopicsMigration;
import net.ask39.prod_topics_operate_log.service.impl.ProdTopicsOperateLogMigration;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.File;

/**
 * 应用入口
 *
 * @author zhangzheng
 * @date 2020-10-28 17:21
 **/
@SpringBootApplication
public class Main implements CommandLineRunner, ApplicationContextAware {
    public static void main(String[] args) {
        new SpringApplication(Main.class).run(args);
    }

    private static ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
        //applicationContext.getBean(ProdTopicContentTaskExport.class).export();
        //applicationContext.getBean(ProdReplyExport.class).export();
        //applicationContext.getBean(ProdTopicExport.class).export();

        //applicationContext.getBean(ProdProductionStandardsMigration.class).reader(null);
        //applicationContext.getBean(ProdProductionPlanMigration.class).reader(new File("input/ProductionPlan.txt"));
        //applicationContext.getBean(ProdTopicContentTaskMigration.class).reader(new File("input/TopicContentTask.txt"));
        //applicationContext.getBean(ProdTopicTaskConfigMigration.class).reader(new File("input/TopicTaskConfig.txt"));
        //applicationContext.getBean(ProdTopicsMigration.class).reader(new File(ProdTopicExport.OUTPUT_FILE));
        //applicationContext.getBean(ProdTopicsOperateLogMigration.class).reader(new File("input/AuthTopicsHandleLog.txt"));
        //applicationContext.getBean(ProdReplyMigration.class).reader(new File("input/IssuePost.txt"));
        //applicationContext.getBean(ProdTopicsMigration.class).reader(new File("input/AuthTopics.txt"));

        //applicationContext.getBean(ProdProductionReplyStandardsInsert.class).insert();
        //applicationContext.getBean(ProdProductionReplyStandardMapInsert.class).insert();
        //applicationContext.getBean(ProdProductionStandardsInsert.class).insert();
        //applicationContext.getBean(ProdProductionPlanInsert.class).insert();
        //applicationContext.getBean(ProdTopicContentTaskInsert.class).insert();
        applicationContext.getBean(ProdTopicTaskConfigInsert.class).insert();
        //applicationContext.getBean(ProdTopicsInsert.class).insert();
        //applicationContext.getBean(ProdReplyOrderInsert.class).insert();
        //applicationContext.getBean(ProdAuthOrderInsert.class).insert();
        //applicationContext.getBean(ProdReplyAuditOrderInsert.class).insert();
        //applicationContext.getBean(ProdReplyInsert.class).insert();

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Main.applicationContext = applicationContext;
    }
}
