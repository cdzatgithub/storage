package com.elephant.basic.storage.mongo.query;

import org.springframework.data.mongodb.core.query.Criteria;

/**
 * @author chendongzhi
 * @date 18:072018/9/27 0027
 * @description TODO(补充描述)
 */
public class Ne extends QueryItem {
    private String key;
    private Object obj;

    public Ne(String key, Object obj) {
        this.key = key;
        this.obj = obj;
    }
    public Ne(){}
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public Criteria join() {
        return new Criteria(this.key).ne(this.obj);
    }
}
