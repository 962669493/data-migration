package net.ask39;

import net.ask39.prod_production_plan.service.impl.ProdProductionPlanMigration;
import net.ask39.prod_production_standards.service.impl.ProdProductionStandardsMigration;
import net.ask39.prod_reply.service.impl.ProdReplyMigration;
import net.ask39.prod_topic_content_task.service.impl.ProdTopicContentTaskMigration;
import net.ask39.prod_topic_task_config.service.impl.ProdTopicTaskConfigMigration;
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
        String inputFilePath = System.getProperty("inputFilePath");
        //applicationContext.getBean(ProdProductionStandardsMigration.class).reader(new File(inputFilePath));
        applicationContext.getBean(ProdProductionPlanMigration.class).reader(new File(inputFilePath));
        //applicationContext.getBean(ProdTopicContentTaskMigration.class).reader(new File(inputFilePath));
        //applicationContext.getBean(ProdTopicTaskConfigMigration.class).reader(new File(inputFilePath));
        //applicationContext.getBean(ProdTopicsMigration.class).reader(new File(inputFilePath));
        //applicationContext.getBean(ProdTopicsOperateLogMigration.class).reader(new File(inputFilePath));
        //applicationContext.getBean(ProdReplyMigration.class).reader(new File(inputFilePath));

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Main.applicationContext = applicationContext;
    }
}
