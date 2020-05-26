package com.elephant.basic.storage.mongo.service;

import com.elephant.basic.storage.mongo.query.MongodbQuery;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;

import java.io.InputStream;

/**
 * @author chendongzhi
 * @date 17:572019/3/30 0030
 * @description TODO(补充描述)
 */
public interface GridFsClient {
    /**
     * 保存文件
     * @param content
     * @param fileName
     * @return
     */
    ObjectId save(InputStream content, String fileName);

    /**
     * 保存文件
     * @param content
     * @param fileName
     * @param contentType
     * @return
     */
    ObjectId save(InputStream content, String fileName, String contentType);

    /**
     * 保存文件
     * @param content
     * @param fileName
     * @param contentType
     * @param metaData
     * @return
     */
    ObjectId save(InputStream content, String fileName, String contentType, Object metaData);

    /**
     * 保存文件
     * @param content
     * @param fileName
     * @param metaData
     * @return
     */
    ObjectId save(InputStream content, String fileName, Object metaData);

    /**
     * 保存文件
     * @param content
     * @param metaData
     * @return
     */
    ObjectId save(InputStream content, Object metaData);

    ObjectId save(InputStream content, String fileName, String contentType, Integer chunkSizeBytes);

    ObjectId save(InputStream content, String fileName, String contentType, Integer chunkSizeBytes, Object metaData);

    ObjectId save(InputStream content, String fileName, Integer chunkSizeBytes, Object metaData);

    ObjectId save(InputStream content, Integer chunkSizeBytes, Object metaData);

    /**
     * 保存文件
     * @param content
     * @param fileName
     * @return
     */
    ObjectId save(byte[] content, String fileName);

    /**
     * 保存文件
     * @param content
     * @param fileName
     * @param contentType
     * @return
     */
    ObjectId save(byte[] content, String fileName, String contentType);

    /**
     * 保存文件
     * @param content
     * @param fileName
     * @param contentType
     * @param metaData
     * @return
     */
    ObjectId save(byte[] content, String fileName, String contentType, Object metaData);

    /**
     * 保存文件
     * @param content
     * @param fileName
     * @param metaData
     * @return
     */
    ObjectId save(byte[] content, String fileName, Object metaData);

    /**
     * 保存文件
     * @param content
     * @param metaData
     * @return
     */
    ObjectId save(byte[] content, Object metaData);

    ObjectId save(byte[] content, String fileName, Integer chunkSizeBytes);

    ObjectId save(byte[] content, String fileName, String contentType, Integer chunkSizeBytes);

    ObjectId save(byte[] content, String fileName, String contentType, Integer chunkSizeBytes, Object metaData);

    ObjectId save(byte[] content, String fileName, Integer chunkSizeBytes, Object metaData);

    ObjectId save(byte[] content, Integer chunkSizeBytes, Object metaData);

    /**
     * 根据id获取文件
     * @param id
     * @return
     */
    GridFSFile getOne(String id);
    /**
     * 获取单个文件
     * @param mongodbQuery
     * @return
     */
    GridFSFile getOne(MongodbQuery mongodbQuery);

    /**
     * 获取文件列表
     * @param mongodbQuery
     * @return
     */
    GridFSFindIterable query(MongodbQuery mongodbQuery);
}
