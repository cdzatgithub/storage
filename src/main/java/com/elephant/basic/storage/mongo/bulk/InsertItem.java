package com.elephant.basic.storage.mongo.bulk;

import org.springframework.data.mongodb.core.BulkOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chendongzhi
 * @date 17:572018/9/17 0017
 * @description TODO(补充描述)
 */
public class InsertItem<T> extends WriteItem<T> {
    public InsertItem(){

    }
    private T document;
    private List<T> documents;

    private InsertItem(T document) {
        this.document = document;
    }

    private InsertItem(List<T> documents) {
        this.documents = documents;
    }

    public T getDocument() {
        return document;
    }

    public void setDocument(T document) {
        this.document = document;
    }

    public List<T> getDocuments() {
        return documents;
    }

    public void setDocuments(List<T> documents) {
        this.documents = documents;
    }
    public void add(T document){
        if(this.documents==null){
            documents=new ArrayList<>();
        }
        documents.add(document);
    }

    public static <T> InsertItem<T> newInstance(T document){
        return new InsertItem<>(document);
    }
    public static <T> InsertItem<T> newInstance(List<T> documents){
        return new InsertItem<>(documents);
    }

    @Override
    public BulkOperations join(BulkOperations ops,Class<T> clazz) {
        if(this.document!=null){
            ops.insert(document);
        }
        if(this.documents!=null&&!this.documents.isEmpty()){
            ops.insert(documents);
        }
        return ops;
    }
}
