package com.bing.utils.datasource.kafka.annotation;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@ConfigurationProperties(prefix = "spring.kafka.producer")
@Configuration
@Data
public class KafkaProduceProperties {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProduceProperties.class);

    private String bootstrapServers;

    @PostConstruct
    private void printConf(){
        LOGGER.info("\nkafka 生产加载完成:" +
                "\n\tbootstrap-servers[{}]",bootstrapServers);
    }
}
