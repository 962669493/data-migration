package net.ask39;

import net.ask39.prod_production_plan.service.impl.ProdProductionPlanExport;
import net.ask39.prod_production_plan.service.impl.ProdProductionPlanInsert;
import net.ask39.prod_production_plan.service.impl.ProdProductionPlanMigration;
import net.ask39.prod_production_reply_standard_map.service.impl.ProdProductionReplyStandardMapInsert;
import net.ask39.prod_production_reply_standards.service.impl.ProdProductionReplyStandardsInsert;
import net.ask39.prod_production_standards.service.impl.ProdProductionStandardsInsert;
import net.ask39.prod_production_standards.service.impl.ProdProductionStandardsMigration;
import net.ask39.prod_reply.service.impl.ProdReplyExport;
import net.ask39.prod_reply.service.impl.ProdReplyInsert;
import net.ask39.prod_reply.service.impl.ProdReplyMigration;
import net.ask39.prod_reply_audit_order.service.impl.ProdReplyAuditOrderExport;
import net.ask39.prod_reply_audit_order.service.impl.ProdReplyAuditOrderInsert;
import net.ask39.prod_reply_auth_order.service.impl.ProdAuthOrderInsert;
import net.ask39.prod_reply_order.service.impl.ProdReplyOrderInsert;
import net.ask39.prod_topic_content_task.service.impl.ProdTopicContentTaskExport;
import net.ask39.prod_topic_content_task.service.impl.ProdTopicContentTaskInsert;
import net.ask39.prod_topic_content_task.service.impl.ProdTopicContentTaskMigration;
import net.ask39.prod_topic_task_config.service.impl.ProdTopicTaskConfigExport;
import net.ask39.prod_topic_task_map.service.impl.ProdTopicTaskMapInsert;
import net.ask39.prod_topic_task_map.service.impl.ProdTopicTaskMapMigration;
import net.ask39.prod_topic_task_config.service.impl.ProdTopicTaskConfigInsert;
import net.ask39.prod_topic_task_config.service.impl.ProdTopicTaskConfigMigration;
import net.ask39.prod_topics.service.impl.ProdTopicExport;
import net.ask39.prod_topics.service.impl.ProdTopicsInsert;
import net.ask39.prod_topics.service.impl.ProdTopicsMigration;
import net.ask39.prod_topics.service.impl.TopicExtExport;
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
        String type = System.getProperty("type");
        switch (type){
            // 生产标准
            case "21":
                applicationContext.getBean(ProdProductionStandardsMigration.class).reader(null);
                break;
            case "31":
                applicationContext.getBean(ProdProductionReplyStandardsInsert.class).insert();
                break;
            case "32":
                applicationContext.getBean(ProdProductionReplyStandardMapInsert.class).insert();
                break;
            case "33":
                applicationContext.getBean(ProdProductionStandardsInsert.class).insert();
                break;
            // 生产计划
            case "16":
                applicationContext.getBean(ProdProductionPlanExport.class).export();
                break;
            case "22":
                applicationContext.getBean(ProdProductionPlanMigration.class).reader(new File(ProdProductionPlanExport.INPUT_PRODUCTION_PLAN_TXT));
                break;
            case "34":
                applicationContext.getBean(ProdProductionPlanInsert.class).insert();
                break;
            // 生产任务
            case "11":
                applicationContext.getBean(ProdTopicContentTaskExport.class).export();
                break;
            case "23":
                applicationContext.getBean(ProdTopicContentTaskMigration.class).reader(new File(ProdTopicContentTaskExport.INPUT_TOPIC_CONTENT_TASK_TXT));
                break;
            case "35":
                applicationContext.getBean(ProdTopicContentTaskInsert.class).insert();
                break;
            // 生产任务配置
            case "17":
                applicationContext.getBean(ProdTopicTaskConfigExport.class).export();
                break;
            case "24":
                applicationContext.getBean(ProdTopicTaskConfigMigration.class).reader(new File(ProdTopicTaskConfigExport.INPUT_TOPIC_TASK_CONFIG_TXT));
                break;
            case "36":
                applicationContext.getBean(ProdTopicTaskConfigInsert.class).insert();
                break;
            // 帖子
            case "15":
                applicationContext.getBean(TopicExtExport.class).export();
                break;
            case "13":
                applicationContext.getBean(ProdTopicExport.class).export();
                break;
            case "25":
                applicationContext.getBean(ProdTopicsMigration.class).reader(new File(ProdTopicExport.OUTPUT_FILE));
                break;
            case "37":
                applicationContext.getBean(ProdTopicsInsert.class).insert();
                break;
            // 帖子任务映射表
            case "28":
                applicationContext.getBean(ProdTopicTaskMapMigration.class).reader(new File(ProdTopicsMigration.OUTPUT_TOPIC_ID_REPLY_TASK_ID_AUTH_TASK_ID_TXT));
                break;
            case "42":
                applicationContext.getBean(ProdTopicTaskMapInsert.class).insert();
                break;
            // 答题工单
            case "38":
                applicationContext.getBean(ProdReplyOrderInsert.class).insert();
                break;
            // 回复
            case "12":
                applicationContext.getBean(ProdReplyExport.class).export();
                break;
            case "27":
                applicationContext.getBean(ProdReplyMigration.class).reader(new File("input/IssuePost.txt"));
                break;
            case "41":
                applicationContext.getBean(ProdReplyInsert.class).insert();
                break;
            // 回复审核工单
            case "14":
                applicationContext.getBean(ProdReplyAuditOrderExport.class).export();
                break;
            case "40":
                applicationContext.getBean(ProdReplyAuditOrderInsert.class).insert();
                break;
            // 授权工单
            case "39":
                applicationContext.getBean(ProdAuthOrderInsert.class).insert();
                break;
            // 帖子操作日志，暂时不迁
            case "26":
                applicationContext.getBean(ProdTopicsOperateLogMigration.class).reader(new File("input/AuthTopicsHandleLog.txt"));
                break;
            default:
                throw new RuntimeException("请选择type");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Main.applicationContext = applicationContext;
    }
}
