package io.dedovicnermin.cpperftesting.config;

import io.dedovicnermin.cpperftesting.PerfTopology;
import lombok.Data;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@Data
public class SConfig {

    @Value("${bootstrap.servers}")
    private String bootstrapServers;

    @Value("${input.topic}")
    private String inputTopic;

    @Value("${output.topic}")
    private String outputTopic;

    @Value("${truststore.location}")
    private String truststoreLocation;

    @Value("${truststore.password}")
    private String truststorePassword;

    @Value("${keystore.location}")
    private String keystoreLocation;

    @Value("${keystore.password}")
    private String keystorePassword;

    @Value("${key.password}")
    private String keyPassword;

    @Value("${group.id}")
    private String groupId;

    @Value("${app.id}")
    private String applicationId;

    @Value("${stream.thread.count}")
    private Integer threadCount;

    @Value("${optimize.streams}")
    private String optimizeStreams;

    private static final String SSL_PROTOCOL = "SSL";

    @Bean
    public Properties properties() {
        final Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, getApplicationId());
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServers());
        properties.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, getThreadCount());
        properties.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE_V2);
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);

        properties.put(ConsumerConfig.GROUP_ID_CONFIG, getGroupId());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SSL_PROTOCOL);
        properties.put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, SslConfigs.DEFAULT_SSL_KEYSTORE_TYPE);
        properties.put(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, SslConfigs.DEFAULT_SSL_KEYSTORE_TYPE);
        properties.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG, "");
        properties.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, keystoreLocation);
        properties.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, keystorePassword);
//        properties.put(SslConfigs.SSL_KEYSTORE_KEY_CONFIG, keyPassword);
        properties.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, truststoreLocation);
        properties.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, truststorePassword);

        return properties;
    }

    @Bean
    public KafkaStreams kafkaStreams() {
        return new KafkaStreams(
                PerfTopology.topology(getInputTopic(), getOutputTopic()),
                properties()
        );
    }




}
