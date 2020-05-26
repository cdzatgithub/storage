package com.elephant.basic.storage.mongo.query;

import org.springframework.data.geo.Shape;
import org.springframework.data.mongodb.core.query.Criteria;

/**
 * @author chendongzhi
 * @date 18:072018/9/27 0027
 * @description TODO(补充描述)
 */
public class Within extends QueryItem {
    public Within(String key, Shape shape) {
        this.key = key;
        this.shape = shape;
    }

    public Within() {
    }

    private String key;
    private Shape shape;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    @Override
    public Criteria join() {
        return new Criteria(this.key).within(this.shape);
    }
}
