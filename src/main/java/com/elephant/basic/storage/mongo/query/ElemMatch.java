package com.elephant.basic.storage.mongo.query;

import org.springframework.data.mongodb.core.query.Criteria;

/**
 * @author chendongzhi
 * @date 18:072018/9/27 0027
 * @description TODO(补充描述)
 */
public class ElemMatch extends QueryItem {
    public ElemMatch(String key, QueryItem queryItem) {
        this.key = key;
        this.queryItem = queryItem;
    }
    public ElemMatch(){}


    private String key;
    private QueryItem queryItem;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public QueryItem getQueryItem() {
        return queryItem;
    }

    public void setQueryItem(QueryItem queryItem) {
        this.queryItem = queryItem;
    }

    @Override
    public Criteria join() {
        return new Criteria(this.key).elemMatch(this.queryItem.join());
    }
}
