package com.bing.utils.datasource.kafka.annotation;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@ConfigurationProperties(prefix = "spring.kafka.consumer")
@Configuration
@Data
public class KafkaConfigProperties {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConfigProperties.class);

    private String bootstrapServers;
    private String groupId;

    @PostConstruct
    private void printConf(){
        LOGGER.info("\nkafka 配置加载完成:" +
                "\n\tbootstrap-servers[{}]" +
                "\n\tgroup-id[{}]",bootstrapServers,groupId);
    }
}
