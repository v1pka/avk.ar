package ru.avk.base.job;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.avk.base.job.service.ScheduledTask;
import ru.avk.base.job.service.impl.CronTaskExecutorService;

/**
 * Created by ipopkov on 02/04/16.
 */
@Configuration
@EnableAutoConfiguration
@EnableScheduling
@EnableWebMvc
public class BaseJobConfig {

    @Value("${job.cron}")
    private String cronExpression;

    @Bean
    InitializingBean initialize(CronTaskExecutorService jobRunner){
        return () -> {
            jobRunner.scheduleJob("0 */5 * * * *");
        };
    }

}
