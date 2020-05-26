package com.elephant.basic.storage.mongo.query;


import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBList;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;

import java.io.Serializable;

/**
 * @author chendongzhi
 * @date 18:062018/9/27 0027
 * @description TODO(补充描述)
 */
public abstract class QueryItem implements Serializable {
    public abstract String getKey();

    public abstract Criteria join();
    public QueryItem join(QueryItem queryItem){
        throw new UnsupportedOperationException();
    }
    public String toString(){
        return JSON.toJSONString(this);
    }
    protected BasicDBList createCriteriaList(Criteria[] criteria) {
        BasicDBList bsonList = new BasicDBList();
        Criteria[] var3 = criteria;
        int var4 = criteria.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Criteria c = var3[var5];
            bsonList.add(c.getCriteriaObject());
        }

        return bsonList;
    }
}
