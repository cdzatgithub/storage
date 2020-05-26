package com.elephant.basic.storage.mongo.update;

import com.elephant.basic.storage.UpdateFeatures;

/**
 * @author chendongzhi
 * @date 10:022018/9/4 0004
 * @description mongodb update 类型
 */
public class MongodbUpdateFeatures extends UpdateFeatures<FieldUpdateModel> {
    public FieldUpdateModel getFeature(String field){
        return super.get(field);
    }

    public void setFeature(String field,FieldUpdateModel feature){
        super.put(field,feature);
    }
}
