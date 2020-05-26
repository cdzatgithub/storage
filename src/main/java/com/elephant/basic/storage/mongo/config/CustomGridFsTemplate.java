package com.elephant.basic.storage.mongo.config;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.gridfs.GridFSFile;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.convert.QueryMapper;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.InputStream;

/**
 * @author chendongzhi
 * @date 10:592019/7/9 0009
 * @description 自定义GridFsTemplate
 */
public class CustomGridFsTemplate extends GridFsTemplate {
    static final String CONTENT_TYPE_FIELD = "_contentType";
    private final MongoDbFactory dbFactory;
    private final String bucket;
    private final MongoConverter converter;
    private final QueryMapper queryMapper;

    public CustomGridFsTemplate(MongoDbFactory dbFactory, MongoConverter converter) {
        this(dbFactory, converter, (String) null);
    }

    public CustomGridFsTemplate(MongoDbFactory dbFactory, MongoConverter converter, String bucket) {
        super(dbFactory, converter, bucket);
        this.dbFactory = dbFactory;
        this.converter = converter;
        this.bucket = bucket;
        this.queryMapper = new QueryMapper(converter);
    }

    public ObjectId store(InputStream content, String filename, String contentType, Integer chunkSizeBytes, Object metadata) {
        Bson dbObject = null;
        if (metadata != null) {
            dbObject = new BasicDBObject();
            this.converter.write(metadata, dbObject);
        }

        return this.store(content, filename, contentType, chunkSizeBytes, (Document) dbObject);
    }

    public ObjectId store(InputStream content, String filename, String contentType, Integer chunkSizeBytes, Document metadata) {
        Assert.notNull(content, "InputStream must not be null!");
        GridFSUploadOptions options = new GridFSUploadOptions();

        Document mData = new Document();

        if (StringUtils.hasText(contentType)) {
            mData.put(CONTENT_TYPE_FIELD, contentType);
        }

        if (metadata != null) {
            mData.putAll(metadata);
        }
        if (chunkSizeBytes != null) {
            options.chunkSizeBytes(chunkSizeBytes);
        }
        options.metadata(mData);
        return this.getGridFs().uploadFromStream(filename, content, options);
    }

    private GridFSBucket getGridFs() {
        MongoDatabase db = this.dbFactory.getDb();
        return this.bucket == null ? GridFSBuckets.create(db) : GridFSBuckets.create(db, bucket);
    }


}
