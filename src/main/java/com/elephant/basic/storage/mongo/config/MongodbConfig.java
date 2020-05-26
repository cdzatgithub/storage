package com.elephant.basic.storage.mongo.config;

import com.elephant.basic.storage.mongo.service.GridFsClient;
import com.elephant.basic.storage.mongo.service.GridFsClientImpl;
import com.elephant.basic.storage.mongo.service.MongodbClient;
import com.elephant.basic.storage.mongo.service.MongodbClientImpl;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author chendongzhi
 * @date 10:482018/9/4 0004
 * @description TODO(补充描述)
 */
@Configuration
@ConditionalOnClass({MongoClient.class, MongoTemplate.class})
@Import({MongodbClientImpl.class, GridFsClientImpl.class})
public class MongodbConfig {

    @Autowired
    private MongodbClientImpl mongodbClient;

    @Autowired
    private GridFsClientImpl gridFsClient;

    @Bean
    @ConditionalOnMissingBean(MongodbClient.class)
    public MongodbClient mongodbClient() {
        return mongodbClient;
    }

    @Bean
    @ConditionalOnMissingBean(GridFsClient.class)
    public GridFsClient gridFsClient() {
        return gridFsClient;
    }
}
