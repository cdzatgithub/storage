package com.elephant.basic.storage.mongo.query;

import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.query.Criteria;

/**
 * @author chendongzhi
 * @date 18:072018/9/27 0027
 * @description TODO(补充描述)
 */
public class Intersects extends QueryItem {
    private String key;
    private GeoJson geoJson;

    public Intersects(String key, GeoJson geoJson) {
        this.key = key;
        this.geoJson = geoJson;
    }
    public Intersects(){}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public GeoJson getGeoJson() {
        return geoJson;
    }

    public void setGeoJson(GeoJson geoJson) {
        this.geoJson = geoJson;
    }

    @Override
    public Criteria join() {
        return new Criteria(this.key).intersects(this.geoJson);
    }
}
