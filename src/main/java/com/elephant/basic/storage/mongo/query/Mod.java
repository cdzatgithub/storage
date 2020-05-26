package com.elephant.basic.storage.mongo.query;

import org.springframework.data.mongodb.core.query.Criteria;

/**
 * @author chendongzhi
 * @date 18:072018/9/27 0027
 * @description TODO(补充描述)
 */
public class Mod extends QueryItem {
    private String key;
    private Number value;
    private Number remainder;

    public Mod(String key, Number value, Number remainder) {
        this.key = key;
        this.value = value;
        this.remainder = remainder;
    }
    public Mod(){}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Number getValue() {
        return value;
    }

    public void setValue(Number value) {
        this.value = value;
    }

    public Number getRemainder() {
        return remainder;
    }

    public void setRemainder(Number remainder) {
        this.remainder = remainder;
    }

    @Override
    public Criteria join() {
        return new Criteria(this.key).mod(this.value,this.remainder);
    }
}
