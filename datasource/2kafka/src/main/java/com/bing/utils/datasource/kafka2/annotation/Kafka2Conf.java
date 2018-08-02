package com.bing.utils.datasource.kafka2.annotation;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@ConfigurationProperties(prefix = "kafka2")
@Configuration
@Data
public class Kafka2Conf {

    private static final Logger LOGGER = LoggerFactory.getLogger(Kafka2Conf.class);

    private String consumerBootstrapServers;
    private String groupId;
    private String produceBootstrapServers;

    @PostConstruct
    private void printConf(){
        LOGGER.info("\nkafka2 配置加载完成:" +
                "\n\tconsumer-bootstrap-servers[{}]" +
                "\n\tgroup-id[{}]" +
                "\n\tproduce-bootstrap-servers[{}]",consumerBootstrapServers,groupId,produceBootstrapServers);
    }

}
