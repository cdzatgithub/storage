package com.elephant.basic.storage.mongo.query;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Collection;

/**
 * @author chendongzhi
 * @date 18:072018/9/27 0027
 * @description TODO(补充描述)
 */
public class Nor extends QueryItem {
    private QueryItem[] queryBodies;

    public Nor() {
    }
    private String key="$nor";
    public Nor(QueryItem... queryBodies) {
        this.queryBodies = queryBodies;
    }
    public Nor(Collection<QueryItem> queryBodies) {
        this.queryBodies = queryBodies.toArray(new QueryItem[0]);
    }

    public QueryItem[] getQueryBodies() {
        return queryBodies;
    }

    public void setQueryBodies(QueryItem[] queryBodies) {
        this.queryBodies = queryBodies;
    }

    @Override
    public String getKey() {
        return null;
    }
    public QueryItem join(QueryItem queryItem){
        ArrayUtils.addAll(this.queryBodies,((Nor)queryItem).getQueryBodies());
        return this;
    }
    @Override
    public Criteria join() {
        Criteria[] criterias=new Criteria[queryBodies.length];
        int i=0;
        for(QueryItem queryItem :queryBodies){
            criterias[i]= queryItem.join();
            i++;
        }
        Criteria orCriteria=new Criteria(key);
        return orCriteria.is(createCriteriaList(criterias));
    }
}
