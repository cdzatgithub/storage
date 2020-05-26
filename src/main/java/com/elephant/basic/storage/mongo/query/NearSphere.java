package com.elephant.basic.storage.mongo.query;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;

/**
 * @author chendongzhi
 * @date 18:072018/9/27 0027
 * @description TODO(补充描述)
 */
public class NearSphere extends QueryItem {
    private String key;
    private Point point;

    public NearSphere(String key, Point point) {
        this.key = key;
        this.point = point;
    }
    public NearSphere(){}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public Criteria join() {
        return new Criteria(this.key).nearSphere(this.point);
    }
}
