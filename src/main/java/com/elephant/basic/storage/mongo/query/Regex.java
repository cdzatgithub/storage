package com.elephant.basic.storage.mongo.query;

import org.springframework.data.mongodb.core.query.Criteria;

/**
 * @author chendongzhi
 * @date 18:072018/9/27 0027
 * @description TODO(补充描述)
 */
public class Regex extends QueryItem {
    private String key;
    private String value;

    public Regex(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Regex() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Criteria join() {
        return new Criteria(this.key).regex(this.value);
    }
}
