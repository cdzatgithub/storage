package com.elephant.basic.storage.mongo.config;

import com.elephant.basic.storage.mongo.properties.MongoOptionsProperties;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chendongzhi
 * @date 15:182019/6/27 0027
 * @description TODO(补充描述)
 */
@Configuration
@ConditionalOnClass({MongoClient.class})
@EnableConfigurationProperties({MongoOptionsProperties.class})
public class CustomMongoAutoConfiguration {

    @Bean
    public MongoClientOptions options(MongoOptionsProperties properties){
        return MongoClientOptions.builder().connectionsPerHost(properties.getMaxConnectionsPerHost())
                .minConnectionsPerHost(properties.getMinConnectionsPerHost())
                .maxConnectionIdleTime(properties.getMaxConnectionIdleTime())
                .maxConnectionLifeTime(properties.getMaxConnectionLifeTime())
                .maxWaitTime(properties.getMaxWaitTime())
                .connectTimeout(properties.getConnectTimeout())
                .alwaysUseMBeans(properties.isAlwaysUseMBeans())
                .heartbeatConnectTimeout(properties.getHeartbeatConnectTimeout())
                .heartbeatFrequency(properties.getHeartbeatFrequency())
                .heartbeatSocketTimeout(properties.getHeartbeatSocketTimeout())
                .localThreshold(properties.getLocalThreshold())
                .minHeartbeatFrequency(properties.getMinHeartbeatFrequency())
                .requiredReplicaSetName(properties.getRequiredReplicaSetName())
                .serverSelectionTimeout(properties.getServerSelectionTimeout())
                .socketTimeout(properties.getSocketTimeout())
                .sslEnabled(properties.isSslEnabled())
                .sslInvalidHostNameAllowed(properties.isSslInvalidHostNameAllowed())
                .threadsAllowedToBlockForConnectionMultiplier(properties.getThreadsAllowedToBlockForConnectionMultiplier()).build();
    }
}
