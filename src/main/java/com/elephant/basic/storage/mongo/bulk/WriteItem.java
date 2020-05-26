package com.elephant.basic.storage.mongo.bulk;

import org.springframework.data.mongodb.core.BulkOperations;

import java.io.Serializable;

/**
 * @author chendongzhi
 * @date 17:522018/9/17 0017
 * @description TODO(补充描述)
 */
public abstract class WriteItem <T> implements Serializable {
    public abstract BulkOperations join(BulkOperations ops,Class<T> clazz);
    private boolean needMerge=false;

    public boolean isNeedMerge() {
        return needMerge;
    }

    public void setNeedMerge(boolean needMerge) {
        this.needMerge = needMerge;
    }
}
