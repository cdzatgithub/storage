package com.elephant.basic.storage.mongo.config;

import com.mongodb.ClientSessionOptions;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoDatabase;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author chendongzhi
 * @date 11:032019/7/9 0009
 * @description 自定义gfs 自动配置
 */
@Configuration
@ConditionalOnClass(MongoClient.class)
@Import(MongoDataAutoConfiguration.class)
@AutoConfigureAfter(MongoDataAutoConfiguration.class)
public class GridFsAutoConfiguration {
    private final MongoProperties properties;

    public GridFsAutoConfiguration(MongoProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public CustomGridFsTemplate customGridFsTemplate(MongoDbFactory mongoDbFactory,
                                         MongoTemplate mongoTemplate) {
        return new CustomGridFsTemplate(
                new GridFsAutoConfiguration.GridFsMongoDbFactory(mongoDbFactory, this.properties),
                mongoTemplate.getConverter());
    }

    private static class GridFsMongoDbFactory implements MongoDbFactory {
        private final MongoDbFactory mongoDbFactory;
        private final MongoProperties properties;

        GridFsMongoDbFactory(MongoDbFactory mongoDbFactory, MongoProperties properties) {
            Assert.notNull(mongoDbFactory, "MongoDbFactory must not be null");
            Assert.notNull(properties, "Properties must not be null");
            this.mongoDbFactory = mongoDbFactory;
            this.properties = properties;
        }

        public MongoDatabase getDb() throws DataAccessException {
            String gridFsDatabase = this.properties.getGridFsDatabase();
            return StringUtils.hasText(gridFsDatabase) ? this.mongoDbFactory.getDb(gridFsDatabase) : this.mongoDbFactory.getDb();
        }

        public MongoDatabase getDb(String dbName) throws DataAccessException {
            return this.mongoDbFactory.getDb(dbName);
        }

        public PersistenceExceptionTranslator getExceptionTranslator() {
            return this.mongoDbFactory.getExceptionTranslator();
        }

        /** @deprecated */
        @Deprecated
        public DB getLegacyDb() {
            return this.mongoDbFactory.getLegacyDb();
        }

        public ClientSession getSession(ClientSessionOptions options) {
            return this.mongoDbFactory.getSession(options);
        }

        public MongoDbFactory withSession(ClientSession session) {
            return this.mongoDbFactory.withSession(session);
        }
    }
}
