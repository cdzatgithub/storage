package com.elephant.basic.storage.mongo.query;

import org.springframework.data.mongodb.core.query.Criteria;

/**
 * @author chendongzhi
 * @date 18:072018/9/27 0027
 * @description TODO(补充描述)
 */
public class Type extends QueryItem {
    public Type(String key, int type) {
        this.key = key;
        this.type = type;
    }

    public Type() {
    }

    private String key;
    private int type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public Criteria join() {
        return new Criteria(this.key).type(this.type);
    }
}
