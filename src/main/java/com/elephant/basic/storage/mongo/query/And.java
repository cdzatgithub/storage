package com.elephant.basic.storage.mongo.query;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Collection;

/**
 * @author chendongzhi
 * @date 18:072018/9/27 0027
 * @description TODO(补充描述)
 */
public class And extends QueryItem {
    public And(QueryItem... queryBodies) {
        this.queryBodies = queryBodies;
    }
    public And(){

    }
    public And(Collection<QueryItem> queryBodies){
        this.queryBodies=queryBodies.toArray(new QueryItem[0]);
    }

    private QueryItem[] queryBodies;
    private String key="$and";

    public QueryItem join(QueryItem queryItem){
        ArrayUtils.addAll(this.queryBodies,((And)queryItem).getQueryBodies());
        return this;
    }

    public QueryItem[] getQueryBodies() {
        return queryBodies;
    }

    public void setQueryBodies(QueryItem[] queryBodies) {
        this.queryBodies = queryBodies;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Criteria join() {
        Criteria[] criterias=new Criteria[queryBodies.length];
        int i=0;
        for(QueryItem queryItem :queryBodies){
            criterias[i]= queryItem.join();
            i++;
        }
        Criteria andCriteria=new Criteria(key);
        return andCriteria.is(createCriteriaList(criterias));
    }
}
