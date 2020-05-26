package com.elephant.basic.storage.mongo.query;

import org.springframework.data.mongodb.core.query.Criteria;

/**
 * @author chendongzhi
 * @date 18:072018/9/27 0027
 * @description TODO(补充描述)
 */
public class MaxDistance extends QueryItem {
    private String key;
    private double maxDistance;

    public MaxDistance(String key, double maxDistance) {
        this.key = key;
        this.maxDistance = maxDistance;
    }
    public MaxDistance(){}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
    }

    @Override
    public Criteria join() {
        return new Criteria(this.key).maxDistance(this.maxDistance);
    }
}
