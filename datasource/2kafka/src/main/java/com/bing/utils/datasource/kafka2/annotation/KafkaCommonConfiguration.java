package com.bing.utils.datasource.kafka2.annotation;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaCommonConfiguration {

    @Autowired
    private Kafka1Conf kafka1Conf;

	@Autowired
	private Kafka2Conf kafka2Conf;

	@Bean
    public ProducerFactory<String, String> producerFactory1() {
	Map<String, Object> props = new HashMap<>();
	props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka1Conf.getProduceBootstrapServers());
	props.put(ProducerConfig.RETRIES_CONFIG, 0);
	props.put(ProducerConfig.BATCH_SIZE_CONFIG, 100);
	props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
	props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 104857600);
	props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public ProducerFactory<String, String> producerFactory2() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka2Conf.getProduceBootstrapServers());
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 100);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 104857600);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Primary
    @Bean(name = "kafkaTemplate1")
    @Qualifier("kafkaTemplate1")
    public KafkaTemplate<String, String> kafkaTemplate1() {
	return new KafkaTemplate<>(producerFactory1());
    }

    @Bean(name = "kafkaTemplate2")
    @Qualifier("kafkaTemplate2")
    public KafkaTemplate<String, String> kafkaTemplate2() {
        return new KafkaTemplate<>(producerFactory2());
    }


    @Bean
    public Map<String, Object> consumerProperties1() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka1Conf.getConsumerBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafka1Conf.getGroupId());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 15000);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 5000);
        return props;
    }

    @Bean
    public Map<String, Object> consumerProperties2() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka2Conf.getConsumerBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafka2Conf.getGroupId());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 15000);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 5000);
        return props;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory1() {
	return new DefaultKafkaConsumerFactory<>(consumerProperties1());
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory2() {
        return new DefaultKafkaConsumerFactory<>(consumerProperties2());
    }

    @Bean(name = "jsonKafkaListenerContainerFactory1")
    @Qualifier("jsonKafkaListenerContainerFactory1")
    public ConcurrentKafkaListenerContainerFactory<String, String> jsonKafkaListenerContainerFactory1() {
	ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
	factory.setConsumerFactory(consumerFactory1());
	factory.setBatchListener(true);
	factory.setMessageConverter(new StringJsonMessageConverter());
	return factory;
    }

    @Bean(name = "jsonKafkaListenerContainerFactory2")
    @Qualifier("jsonKafkaListenerContainerFactory2")
    public ConcurrentKafkaListenerContainerFactory<String, String> jsonKafkaListenerContainerFactory2() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory2());
        factory.setBatchListener(true);
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }
}
