package net.ask39;

import net.ask39.prod_topics.service.impl.ProdTopicsMigration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用入口
 *
 * @author zhangzheng
 * @date 2020-10-28 17:21
 **/
@SpringBootApplication
public class Main implements CommandLineRunner {
    public static void main(String[] args) {
        new SpringApplication(Main.class).run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        new ProdTopicsMigration("sql/prod_topics.sql", "C:/Users/zhangzheng/Desktop/39/prod_topics.txt").migration();
    }
}
