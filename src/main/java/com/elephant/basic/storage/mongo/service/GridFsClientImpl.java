package com.elephant.basic.storage.mongo.service;

import com.elephant.basic.storage.mongo.config.CustomGridFsTemplate;
import com.elephant.basic.storage.mongo.query.MongodbQuery;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author chendongzhi
 * @date 15:332019/3/30 0030
 * @description TODO(补充描述)
 */
public class GridFsClientImpl implements GridFsClient {

    private CustomGridFsTemplate customGridFsTemplate;

    @Autowired
    public GridFsClientImpl(CustomGridFsTemplate customGridFsTemplate) {
        this.customGridFsTemplate = customGridFsTemplate;
    }

    @Override
    public ObjectId save(InputStream content, String fileName) {
        return customGridFsTemplate.store(content, fileName);
    }

    @Override
    public ObjectId save(InputStream content, String fileName, String contentType) {
        return customGridFsTemplate.store(content, fileName, contentType);
    }

    @Override
    public ObjectId save(InputStream content, String fileName, String contentType, Object metaData) {
        return customGridFsTemplate.store(content, fileName, contentType, metaData);
    }

    @Override
    public ObjectId save(InputStream content, String fileName, Object metaData) {
        return customGridFsTemplate.store(content, fileName, metaData);
    }

    @Override
    public ObjectId save(InputStream content, Object metaData) {
        return customGridFsTemplate.store(content, metaData);
    }

    @Override
    public ObjectId save(InputStream content, String fileName, String contentType, Integer chunkSizeBytes) {
        return save(content, fileName, contentType, chunkSizeBytes, null);
    }

    @Override
    public ObjectId save(InputStream content, String fileName, Integer chunkSizeBytes, Object metaData) {
        return save(content, fileName, null, chunkSizeBytes, metaData);
    }

    @Override
    public ObjectId save(InputStream content, Integer chunkSizeBytes, Object metaData) {
        return save(content, null, chunkSizeBytes, metaData);
    }

    @Override
    public ObjectId save(InputStream content, String fileName, String contentType, Integer chunkSizeBytes, Object metaData) {
        return customGridFsTemplate.store(content, fileName, contentType, chunkSizeBytes, metaData);
    }

    @Override
    public ObjectId save(byte[] content, String fileName) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content);
        return save(inputStream, fileName);
    }

    @Override
    public ObjectId save(byte[] content, String fileName, String contentType) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content);
        return save(inputStream, fileName, contentType);
    }

    @Override
    public ObjectId save(byte[] content, String fileName, String contentType, Object metaData) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content);
        return save(inputStream, fileName, contentType, metaData);
    }

    @Override
    public ObjectId save(byte[] content, String fileName, Object metaData) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content);
        return save(inputStream, fileName, metaData);
    }

    @Override
    public ObjectId save(byte[] content, Object metaData) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content);
        return save(inputStream, metaData);
    }

    @Override
    public ObjectId save(byte[] content, String fileName, Integer chunkSizeBytes) {
        return save(content, fileName, chunkSizeBytes, null);
    }

    @Override
    public ObjectId save(byte[] content, String fileName, String contentType, Integer chunkSizeBytes) {
        return save(content, fileName, contentType, chunkSizeBytes, null);
    }

    @Override
    public ObjectId save(byte[] content, String fileName, String contentType, Integer chunkSizeBytes, Object metaData) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content);
        return save(inputStream, fileName, contentType, chunkSizeBytes, metaData);
    }

    @Override
    public ObjectId save(byte[] content, String fileName, Integer chunkSizeBytes, Object metaData) {
        return save(content, fileName, null, chunkSizeBytes, metaData);
    }

    @Override
    public ObjectId save(byte[] content, Integer chunkSizeBytes, Object metaData) {
        return save(content, null, chunkSizeBytes, metaData);
    }

    @Override
    public GridFSFile getOne(String id) {
        return customGridFsTemplate.findOne(new Query(new Criteria("_id").is(new ObjectId(id))));
    }

    @Override
    public GridFSFile getOne(MongodbQuery mongodbQuery) {
        Query query = new Query();
        for (CriteriaDefinition criteriaDefinition : mongodbQuery.catries()) {
            query.addCriteria(criteriaDefinition);
        }
        if (mongodbQuery.getSort() != null) {
            query.with(mongodbQuery.getSort());
        }
        return customGridFsTemplate.findOne(query);
    }

    @Override
    public GridFSFindIterable query(MongodbQuery mongodbQuery) {
        Query query = new Query();
        for (CriteriaDefinition criteriaDefinition : mongodbQuery.catries()) {
            query.addCriteria(criteriaDefinition);
        }
        if (mongodbQuery.getSort() != null) {
            query.with(mongodbQuery.getSort());
        }
        return customGridFsTemplate.find(query);
    }
}
