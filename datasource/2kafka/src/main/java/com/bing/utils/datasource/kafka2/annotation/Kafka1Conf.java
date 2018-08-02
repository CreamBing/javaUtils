package com.bing.utils.datasource.kafka2.annotation;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@ConfigurationProperties(prefix = "kafka1")
@Configuration
@Data
public class Kafka1Conf {

    private static final Logger LOGGER = LoggerFactory.getLogger(Kafka1Conf.class);

    private String consumerBootstrapServers;
    private String groupId;
    private String produceBootstrapServers;

    @PostConstruct
    private void printConf(){
        LOGGER.info("\nkafka1 配置加载完成:" +
                "\n\tconsumer-bootstrap-servers[{}]" +
                "\n\tgroup-id[{}]" +
                "\n\tproduce-bootstrap-servers[{}]",consumerBootstrapServers,groupId,produceBootstrapServers);
    }
}
