package com.elephant.basic.storage.mongo.service;

import com.elephant.basic.storage.mongo.bulk.UpdateItem;
import com.elephant.basic.storage.mongo.bulk.WriteItem;
import com.elephant.basic.storage.mongo.query.MongodbQuery;
import com.elephant.basic.storage.mongo.update.MongodbUpdateFeatures;
import com.elephant.basic.storage.mongo.util.MongodbUtil;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.convert.QueryMapper;
import org.springframework.data.mongodb.core.convert.UpdateMapper;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.data.mongodb.util.MongoClientVersion;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import static org.springframework.data.mongodb.core.query.SerializationUtils.serializeToJsonSafely;

/**
 * @author chendongzhi
 * @date 10:062018/9/4 0004
 * @description mongodb 业务实现类
 */
public class MongodbClientImpl<T> implements MongodbClient<T> {
    private static final Method WAS_ACKNOWLEDGED_METHOD = ReflectionUtils.findMethod(WriteResult.class, "wasAcknowledged");
    private static final Method GET_PERSISTENT_ENTITY_METHOD = ReflectionUtils.findMethod(MongoTemplate.class, "getPersistentEntity", Class.class);
    private static final Method INCREASE_VERSION_FOR_UPDATE_IF_NECESSARY_METHOD = ReflectionUtils.findMethod(MongoTemplate.class, "increaseVersionForUpdateIfNecessary", MongoPersistentEntity.class, Update.class);
    private static final Method PREPARE_WRITE_CONCERN = ReflectionUtils.findMethod(MongoTemplate.class, "prepareWriteConcern", MongoAction.class);
    private static final Field QUERY_MAPPER_FIELD = ReflectionUtils.findField(MongoTemplate.class, "queryMapper");
    private static final Field UPDATE_MAPPER_FIELD = ReflectionUtils.findField(MongoTemplate.class, "updateMapper");
    private static final Field WRITE_CONCERN_FIELD = ReflectionUtils.findField(MongoTemplate.class, "writeConcern");
    private static final Method DB_OBJECT_CONTAINS_VERSION_PROPERTY = ReflectionUtils.findMethod(MongoTemplate.class, "dbObjectContainsVersionProperty", DBObject.class, MongoPersistentEntity.class);
    private static final Method HANDLE_ANY_WRITE_RESULT_ERRORS = ReflectionUtils.findMethod(MongoTemplate.class, "handleAnyWriteResultErrors", WriteResult.class, DBObject.class, MongoActionOperation.class);

    private static final Logger logger = LoggerFactory.getLogger(MongodbClientImpl.class);

    private MongoTemplate mongoTemplate;

    @Autowired
    public MongodbClientImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public int insert(Collection<T> documents, Class<T> clazz) {
        try {
            mongoTemplate.insert(documents, clazz);
        } catch (DuplicateKeyException e) {
            logger.error("mongodb insert error：", e);
            return 0;
        }
        return documents.size();
    }

    @Override
    public int insert(T document) {
        try {
            mongoTemplate.insert(document);
        } catch (DuplicateKeyException e) {
            logger.error("mongodb insert error：", e);
            return 0;
        }
        return 1;
    }

    @Override
    public List<T> find(MongodbQuery mongodbQuery, Class<T> clazz) {
        Query query = new Query();
        for (CriteriaDefinition criteriaDefinition : mongodbQuery.catries()) {
            query.addCriteria(criteriaDefinition);
        }
        if (mongodbQuery.getSort() != null) {
            query.with(mongodbQuery.getSort());
        }
        if (mongodbQuery.getSkip() > 0) {
            query.skip(mongodbQuery.getSkip());
        }
        if (mongodbQuery.getLimit() > 0) {
            query.limit(mongodbQuery.getLimit());
        }
        return mongoTemplate.find(query, clazz, mongodbQuery.getCollectionName());
    }

    @Override
    public T findOne(MongodbQuery mongodbQuery, Class<T> clazz) {
        Query query = new Query();
        for (CriteriaDefinition criteriaDefinition : mongodbQuery.catries()) {
            query.addCriteria(criteriaDefinition);
        }
        if (mongodbQuery.getSort() != null) {
            query.with(mongodbQuery.getSort());
        }
        return mongoTemplate.findOne(query, clazz, mongodbQuery.getCollectionName());
    }

    @Override
    public long delete(String id, Class<T> clazz) {
        return mongoTemplate.remove(new Query(new Criteria("_id").is(id)), clazz).getDeletedCount();
    }

    @Override
    public long delete(String id, Class<T> clazz, String collectionName) {
        return mongoTemplate.remove(new Query(new Criteria("_id").is(id)), clazz, collectionName).getDeletedCount();
    }

    @Override
    public long delete(MongodbQuery mongodbQuery) {
        Query query = new Query();
        for (CriteriaDefinition item : mongodbQuery.catries()) {
            query.addCriteria(item);
        }
        return mongoTemplate.remove(query, mongodbQuery.getCollectionName()).getDeletedCount();
    }

    @Override
    public long delete(MongodbQuery mongodbQuery, Class<T> clazz) {
        Query query = new Query();
        for (CriteriaDefinition item : mongodbQuery.catries()) {
            query.addCriteria(item);
        }
        return mongoTemplate.remove(query, clazz, mongodbQuery.getCollectionName()).getDeletedCount();
    }

    @Override
    public T find(String id, Class<T> clazz) {
        return mongoTemplate.findOne(new Query(new Criteria("_id").is(id)), clazz);
    }

    @Override
    public <C> MapReduceResults<C> mapReduce(String map, String reduce, String collectionName, Class<C> outPutClass) {
        return mongoTemplate.mapReduce(collectionName, map, reduce, outPutClass);
    }

    @Override
    public <C> List<C> aggregate(List<AggregationOperation> operations, String collectionName, Class<C> outPutClass) {
        Aggregation aggregation = Aggregation.newAggregation(operations);
        logger.debug("mongodb聚合语句：{}", aggregation.toString());
        return mongoTemplate.aggregate(aggregation, collectionName, outPutClass).getMappedResults();
    }

    @Override
    public long upsert(MongodbQuery mongodbQuery, T document, Class<T> clazz, boolean updateNull, MongodbUpdateFeatures features) {
        try {
            return mongoTemplate.upsert(MongodbUtil.buildQuery(mongodbQuery.catries()), MongodbUtil.buildUpdate(document, clazz, updateNull, features), clazz, mongodbQuery.getCollectionName()).getModifiedCount();
        } catch (DuplicateKeyException e) {
            logger.warn("mongodb中已存在该数据，不保存，执行更新操作:{}", document);
            return mongoTemplate.updateFirst(MongodbUtil.buildQuery(mongodbQuery.catries()), MongodbUtil.buildUpdate(document, clazz, updateNull, features), clazz, mongodbQuery.getCollectionName()).getModifiedCount();
        }
    }

    @Override
    public long update(MongodbQuery mongodbQuery, T document, Class<T> clazz, boolean multi, boolean updateNull) {
        return update(mongodbQuery, document, clazz, multi, updateNull, null);
    }

    @Override
    public long update(MongodbQuery mongodbQuery, T document, Class<T> clazz, boolean multi, boolean updateNull, MongodbUpdateFeatures features) {
        if (multi) {
            return mongoTemplate.updateMulti(MongodbUtil.buildQuery(mongodbQuery.catries()), MongodbUtil.buildUpdate(document, clazz, updateNull, features), clazz, mongodbQuery.getCollectionName()).getModifiedCount();
        } else {
            return mongoTemplate.updateFirst(MongodbUtil.buildQuery(mongodbQuery.catries()), MongodbUtil.buildUpdate(document, clazz, updateNull, features), clazz, mongodbQuery.getCollectionName()).getModifiedCount();
        }
    }

    @Override
    public long update(final MongodbQuery mongodbQuery, final T document, final Class<T> clazz, final boolean upsert, final boolean multi, final boolean updateNull, final MongodbUpdateFeatures features) {
        Query query = MongodbUtil.buildQuery(mongodbQuery.catries());
        UpdateDefinition update = MongodbUtil.buildUpdate(document, clazz, updateNull, features);
        Assert.notNull(mongodbQuery.getCollectionName(), "CollectionName must not be null!");
        Assert.notNull(query, "Query must not be null!");
        Assert.notNull(update, "Update must not be null!");
        return mongoTemplate.execute(mongodbQuery.getCollectionName(), new CollectionCallback<UpdateResult>() {
            public UpdateResult doInCollection(MongoCollection<Document> collection)
                    throws MongoException, DataAccessException {

                MongoPersistentEntity<?> entity = clazz == null ? null : (MongoPersistentEntity<?>) invokeMethod(GET_PERSISTENT_ENTITY_METHOD, mongoTemplate, clazz);
                invokeMethod(INCREASE_VERSION_FOR_UPDATE_IF_NECESSARY_METHOD, mongoTemplate, entity, update);

                UpdateOptions opts = new UpdateOptions();
                opts.upsert(upsert);
                Document queryObj = new Document();
                if (query != null) {

                    queryObj.putAll(((QueryMapper) MongodbUtil.getField(QUERY_MAPPER_FIELD, mongoTemplate)).getMappedObject(MongodbUtil.buildQuery(mongodbQuery.catries()).getQueryObject(), entity));
                    query.getCollation().map(Collation::toMongoCollation).ifPresent(opts::collation);
                }
                Document updateObj = ((UpdateMapper) MongodbUtil.getField(UPDATE_MAPPER_FIELD, mongoTemplate)).getMappedObject(update.getUpdateObject(), entity);

                if (multi && update.isIsolated() && !queryObj.containsKey("$isolated")) {
                    queryObj.put("$isolated", 1);
                }

                if (logger.isDebugEnabled()) {
                    logger.debug("Calling update using query: {} and update: {} in collection: {}",
                            serializeToJsonSafely(queryObj), serializeToJsonSafely(updateObj), mongodbQuery.getCollectionName());
                }

                MongoAction mongoAction = new MongoAction((WriteConcern) MongodbUtil.getField(WRITE_CONCERN_FIELD, mongoTemplate), MongoActionOperation.UPDATE, mongodbQuery.getCollectionName(), clazz, updateObj, queryObj);
                WriteConcern writeConcernToUse = (WriteConcern) invokeMethod(PREPARE_WRITE_CONCERN, mongoTemplate, mongoAction);
                collection = writeConcernToUse != null ? collection.withWriteConcern(writeConcernToUse) : collection;

                if (!UpdateMapper.isUpdateObject(updateObj)) {

                    ReplaceOptions replaceOptions = new ReplaceOptions();
                    replaceOptions.collation(opts.getCollation());
                    replaceOptions.upsert(opts.isUpsert());
                    return collection.replaceOne(queryObj, updateObj, replaceOptions);
                } else {
                    if (multi) {
                        return collection.updateMany(queryObj, updateObj, opts);
                    } else {
                        return collection.updateOne(queryObj, updateObj, opts);
                    }
                }
            }
        }).getModifiedCount();
    }

    @Override
    public String getCollectionName(Class<T> clazz) {
        return mongoTemplate.getCollectionName(clazz);
    }

    @Override
    public MongoCollection<Document> getCollection(String collectionName) {
        return mongoTemplate.getCollection(collectionName);
    }

    @Override
    public MongoCollection<Document> createCollection(Class<T> clazz) {
        return mongoTemplate.createCollection(clazz);
    }

    @Override
    public MongoCollection<Document> createCollection(String collectionName) {
        return mongoTemplate.createCollection(collectionName);
    }

    @Override
    public MongoCollection<Document> createCollection(Class<T> clazz, CollectionOptions options) {
        return mongoTemplate.createCollection(clazz, options);
    }

    @Override
    public MongoCollection<Document> createCollection(String collectionName, CollectionOptions options) {
        return mongoTemplate.createCollection(collectionName, options);
    }

    @Override
    public boolean collectionExist(String collectionName) {
        return mongoTemplate.collectionExists(collectionName);
    }

    @Override
    public boolean collectionExist(Class<T> clazz) {
        return mongoTemplate.collectionExists(clazz);
    }

    @Override
    public BulkWriteResult bulkUpdate(List<UpdateItem<T>> updates, Class<T> clazz) {
        BulkOperations ops = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, clazz);
        for (UpdateItem<T> item : updates) {
            item.join(ops, clazz);
        }
        return ops.execute();
    }

    @Override
    public BulkWriteResult bulkWrite(List<WriteItem> writes, Class<?> clazz) {
        BulkOperations ops = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, clazz);
        for (WriteItem item : writes) {
            item.join(ops, clazz);
        }
        return ops.execute();
    }

    @Override
    public BulkWriteResult bulkWrite(BulkOperations bulkOperations) {
        return bulkOperations.execute();
    }

    @Override
    public BulkOperations getBulkOperations(Class<T> clazz, BulkOperations.BulkMode bulkMode) {
        return this.mongoTemplate.bulkOps(bulkMode, clazz);
    }

    @Override
    public <C> List<C> textSearch(String search, String collectionName, Class<C> clazz) {
        Query query = new Query();
        query.addCriteria(new TextCriteria().matching(search));
        return mongoTemplate.find(query, clazz, collectionName);
    }

    @Override
    public boolean exist(String id, Class<T> clazz) {
        return mongoTemplate.exists(new Query(Criteria.where("id").is(id)), clazz);
    }

    @Override
    public boolean exist(MongodbQuery query) {
        return mongoTemplate.exists(MongodbUtil.buildQuery(query.catries()), query.getCollectionName());
    }

    private boolean wasAcknowledged(WriteResult writeResult) {
        return MongoClientVersion.isMongo3Driver() ? (Boolean) invokeMethod(WAS_ACKNOWLEDGED_METHOD, writeResult) : true;
    }


    private Object invokeMethod(Method method, Object target, Object... args) {
        method.setAccessible(true);
        return ReflectionUtils.invokeMethod(method, target, args);
    }
}
