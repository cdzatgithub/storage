package com.elephant.basic.storage.mongo.service;

import com.elephant.basic.storage.CommonService;
import com.elephant.basic.storage.mongo.bulk.UpdateItem;
import com.elephant.basic.storage.mongo.bulk.WriteItem;
import com.elephant.basic.storage.mongo.query.MongodbQuery;
import com.elephant.basic.storage.mongo.update.MongodbUpdateFeatures;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;

import java.util.List;

/**
 * @author chendongzhi
 * @date 9:532018/9/4 0004
 * @description TODO(补充描述)
 */
public interface MongodbClient<T> extends CommonService<T,MongodbQuery,MongodbUpdateFeatures> {
    <C> MapReduceResults<C> mapReduce(String map, String reduce, String collectionName, Class<C> outPutClass);
    <C> List<C> aggregate(List<AggregationOperation> operations, String collectionName, Class<C> outPutClass);

    String getCollectionName(Class<T> clazz);

    MongoCollection<Document> getCollection(String collectionName);

    MongoCollection<Document> createCollection(Class<T> clazz);

    MongoCollection<Document> createCollection(String collectionName);

    MongoCollection<Document> createCollection(Class<T> clazz, CollectionOptions options);

    MongoCollection<Document> createCollection(String collectionName, CollectionOptions options);

    boolean collectionExist(String collectionName);

    boolean collectionExist(Class<T> clazz);

    com.mongodb.bulk.BulkWriteResult bulkUpdate(List<UpdateItem<T>> updates, Class<T> clazz);
    com.mongodb.bulk.BulkWriteResult bulkWrite(List<WriteItem> writes, Class<?> clazz);

    BulkWriteResult bulkWrite(BulkOperations bulkOperations);

    BulkOperations getBulkOperations(Class<T> clazz, BulkOperations.BulkMode bulkMode);
    <C> List<C> textSearch(String search,String collectionName,Class<C> clazz);
}
