package ru.avk;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by ipopkov on 02/04/16.
 */
@Configuration
@EnableJpaRepositories(basePackages="ru.avk.repository")
@EntityScan(basePackages="ru.avk.domain")
@ComponentScan(basePackages = "ru.avk")
@EnableAutoConfiguration
public class BaseDataConfig {
}
