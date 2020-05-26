package com.elephant.basic.storage.mongo.query;

import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Collection;

/**
 * @author chendongzhi
 * @date 18:072018/9/27 0027
 * @description TODO(补充描述)
 */
public class All extends QueryItem {
    private String key;
    private Collection obj;

    public All(String key, Collection obj) {
        this.key = key;
        this.obj = obj;
    }

    public All(){

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Collection getObj() {
        return obj;
    }

    public void setObj(Collection obj) {
        this.obj = obj;
    }

    @Override
    public Criteria join() {
        return new Criteria(this.key).all(this.obj);
    }
}
