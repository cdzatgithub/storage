package com.elephant.basic.storage;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author chendongzhi
 * @date 9:552018/9/4 0004
 * @description TODO(补充描述)
 */
public class UpdateFeatures<T> extends HashMap<String,T> {
    public T getFeature(String field){
        return super.get(field);
    }

    public void setFeature(String field,T feature){
        super.put(field,feature);
    }
}
