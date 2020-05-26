package com.elephant.basic.storage.mongo.query;

import org.springframework.data.mongodb.core.query.Criteria;

/**
 * @author chendongzhi
 * @date 18:072018/9/27 0027
 * @description TODO(补充描述)
 */
public class Exists extends QueryItem {

    public Exists(String key, boolean exists) {
        this.key = key;
        this.exists = exists;
    }

    public Exists(){}

    private String key;
    private boolean exists;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    @Override
    public Criteria join() {
        return new Criteria(this.key).exists(this.exists);
    }
}
