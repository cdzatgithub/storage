package com.elephant.basic.storage.mongo.query;

import org.springframework.data.mongodb.core.query.Criteria;

/**
 * @author chendongzhi
 * @date 18:072018/9/27 0027
 * @description TODO(补充描述)
 */
public class MinDistance extends QueryItem {
    private String key;
    private double minDistance;

    public MinDistance(String key, double minDistance) {
        this.key = key;
        this.minDistance = minDistance;
    }
    public MinDistance(){}
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    @Override
    public Criteria join() {
        return new Criteria(this.key).minDistance(this.minDistance);
    }
}
