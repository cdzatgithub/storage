package com.elephant.basic.storage.mongo.query;

import org.springframework.data.mongodb.core.query.Criteria;

/**
 * @author chendongzhi
 * @date 18:072018/9/27 0027
 * @description TODO(补充描述)
 */
public class Size extends QueryItem {
    private String key;
    private int size;

    public Size(String key, int size) {
        this.key = key;
        this.size = size;
    }

    public Size() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public Criteria join() {
        return new Criteria(this.key).size(this.size);
    }
}
