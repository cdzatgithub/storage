package com.elephant.basic.storage.mongo.query;

import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.query.Criteria;

/**
 * @author chendongzhi
 * @date 18:072018/9/27 0027
 * @description TODO(补充描述)
 */
public class Alike extends QueryItem {
    private String key;
    private Example sample;
    public Alike(){

    }
    public Alike(String key, Example sample) {
        this.key = key;
        this.sample = sample;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Example getSample() {
        return sample;
    }

    public void setSample(Example sample) {
        this.sample = sample;
    }

    @Override
    public Criteria join() {
        return new Criteria(this.key).alike(this.sample);
    }
}
