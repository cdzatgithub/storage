package com.elephant.basic.storage.mongo.query;

import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.core.query.Criteria;

/**
 * @author chendongzhi
 * @date 18:072018/9/27 0027
 * @description TODO(补充描述)
 */
public class WithinSphere extends QueryItem {
    public WithinSphere(String key, Circle circle) {
        this.key = key;
        this.circle = circle;
    }

    public WithinSphere() {
    }

    private String key;
    private Circle circle;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    @Override
    public Criteria join() {
        return new Criteria(this.key).withinSphere(this.circle);
    }
}
