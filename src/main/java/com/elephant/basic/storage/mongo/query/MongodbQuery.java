package com.elephant.basic.storage.mongo.query;

import com.elephant.basic.storage.BasicQuery;
import com.elephant.basic.storage.mongo.query.QueryItem;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;

import java.util.*;

/**
 * @author chendongzhi
 * @date 10:022018/9/4 0004
 * @description TODO(补充描述)
 */
public class MongodbQuery extends BasicQuery<QueryItem[]> {

    /**
     * 集合名称
     */
    private String collectionName;

    public MongodbQuery() {

    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public MongodbQuery(Collection<QueryItem> queryItem, int skip, int limit, Sort sort) {
        super(queryItem.toArray(new QueryItem[0]), skip, limit, sort);
    }

    public MongodbQuery(Collection<QueryItem> queryItem, Sort sort) {
        super(queryItem.toArray(new QueryItem[0]),sort);
    }
    public MongodbQuery( Sort sort,QueryItem... queryItem) {
        super(queryItem,sort);
    }

    public MongodbQuery(int skip, int limit, Sort sort,QueryItem... queryItem) {
        super(queryItem, skip, limit, sort);
    }
    public MongodbQuery(String collectionName, QueryItem... queryItem) {
        super(queryItem);
        this.collectionName = collectionName;
    }

    public Collection<CriteriaDefinition> catries() {
        List<CriteriaDefinition> criteriaList=new ArrayList<>();
        Map<String,List<QueryItem>> criterias = new HashMap<>();
        for(QueryItem queryItem : queryBody){
            List<QueryItem> items=criterias.get(queryItem.getKey())==null?new ArrayList<>():criterias.get(queryItem.getKey());
            items.add(queryItem);
            criterias.put(queryItem.getKey(),items);
        }
        for (String key: criterias.keySet()) {
            QueryItem queryItem=null;
                for(QueryItem item:criterias.get(key)){
                    if(queryItem==null){
                        queryItem=item;
                    }else{
                        queryItem.join(item);
                    }
                }
                criteriaList.add(queryItem.join());
        }
        return criteriaList;
    }
}
