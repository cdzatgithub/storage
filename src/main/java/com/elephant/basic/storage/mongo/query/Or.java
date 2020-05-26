package com.elephant.basic.storage.mongo.query;

import com.mongodb.BasicDBList;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;

import java.util.Collection;

/**
 * @author chendongzhi
 * @date 18:072018/9/27 0027
 * @description TODO(补充描述)
 */
public class Or extends QueryItem {
    public Or() {
    }

    public Or(QueryItem... queryBodies) {
        this.queryBodies = queryBodies;
    }
    public Or(Collection<QueryItem> queryBodies) {
        this.queryBodies = queryBodies.toArray(new QueryItem[0]);
    }
    private QueryItem[] queryBodies;
    private String key="$or";
    public QueryItem[] getQueryBodies() {
        return queryBodies;
    }
    public QueryItem join(QueryItem queryItem){
        ArrayUtils.addAll(this.queryBodies,((Or)queryItem).getQueryBodies());
        return this;
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
        Criteria orCriteria=new Criteria(key);
        return orCriteria.is(createCriteriaList(criterias));
    }
}
