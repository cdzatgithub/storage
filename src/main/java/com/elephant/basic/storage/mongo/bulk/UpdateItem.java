package com.elephant.basic.storage.mongo.bulk;

import com.elephant.basic.storage.mongo.query.MongodbQuery;
import com.elephant.basic.storage.mongo.update.MongodbUpdateFeatures;
import com.elephant.basic.storage.mongo.util.MongodbUtil;
import org.springframework.data.mongodb.core.BulkOperations;

/**
 * @author chendongzhi
 * @date 17:122018/9/17 0017
 * @description TODO(补充描述)
 */
public class UpdateItem<T> extends WriteItem<T> {
    public UpdateItem(){

    }
    private MongodbQuery query;
    private T document;
    private MongodbUpdateFeatures features;
    private boolean multi;
    private boolean upsert;
    private boolean updateNull;

    private UpdateItem(MongodbQuery query, T document, MongodbUpdateFeatures features, boolean multi, boolean upsert, boolean updateNull) {
        this.query = query;
        this.document = document;
        this.features = features;
        this.multi = multi;
        this.upsert = upsert;
        this.updateNull = updateNull;
    }

    public MongodbQuery getQuery() {
        return query;
    }

    public void setQuery(MongodbQuery query) {
        this.query = query;
    }

    public T getDocument() {
        return document;
    }

    public void setDocument(T document) {
        this.document = document;
    }

    public boolean isMulti() {
        return multi;
    }

    public void setMulti(boolean multi) {
        this.multi = multi;
    }

    public boolean isUpsert() {
        return upsert;
    }

    public void setUpsert(boolean upsert) {
        this.upsert = upsert;
    }

    public boolean isUpdateNull() {
        return updateNull;
    }

    public void setUpdateNull(boolean updateNull) {
        this.updateNull = updateNull;
    }

    public MongodbUpdateFeatures getFeatures() {
        return features;
    }

    public void setFeatures(MongodbUpdateFeatures features) {
        this.features = features;
    }

    @Override
    public String toString() {
        return "UpdateItem{" +
                "query=" + query +
                ", document=" + document +
                ", multi=" + multi +
                ", upsert=" + upsert +
                ", updateNull=" + updateNull +
                '}';
    }
    public static <T> UpdateItem<T> newInstance(MongodbQuery query,T document,MongodbUpdateFeatures features,boolean multi,boolean upsert,boolean updateNull){
        return new UpdateItem<>(query,document,features,multi,upsert,updateNull);    }
    public static <T> UpdateItem<T> newInstance(MongodbQuery query,T document,MongodbUpdateFeatures features,boolean upsert,boolean updateNull){
        return new UpdateItem<>(query,document,features,true,upsert,updateNull);    }
    public static <T> UpdateItem<T> newInstance(MongodbQuery query,T document,MongodbUpdateFeatures features,boolean updateNull){
        return new UpdateItem<>(query,document,features,false,false,updateNull);    }

    @Override
    public BulkOperations join(BulkOperations ops,Class<T> clazz) {
        if(this.isUpsert()){
            //true true
            ops.upsert(MongodbUtil.buildQuery(this.getQuery().catries()),MongodbUtil.buildUpdate(this.getDocument(),clazz,this.isUpdateNull(),this.getFeatures()));
        }else if(this.isMulti()){
            //false true
            ops.updateMulti(MongodbUtil.buildQuery(this.getQuery().catries()),MongodbUtil.buildUpdate(this.getDocument(),clazz,this.isUpdateNull(),this.getFeatures()));
        } else{
            //false false
            ops.updateOne(MongodbUtil.buildQuery(this.getQuery().catries()),MongodbUtil.buildUpdate(this.getDocument(),clazz,this.isUpdateNull(),this.getFeatures()));
        }
        return ops;
    }
}
