package com.elephant.basic.storage.mongo.bulk;

import com.elephant.basic.storage.mongo.query.MongodbQuery;
import com.elephant.basic.storage.mongo.util.MongodbUtil;
import org.springframework.data.mongodb.core.BulkOperations;

/**
 * @author chendongzhi
 * @date 17:532018/9/17 0017
 * @description TODO(补充描述)
 */
public class DeleteItem<T> extends WriteItem<T> {
    public DeleteItem(){

    }
    private MongodbQuery query;

    private DeleteItem(MongodbQuery query){
        this.query=query;
    }
    public MongodbQuery getQuery() {
        return query;
    }

    public void setQuery(MongodbQuery query) {
        this.query = query;
    }

    public static DeleteItem newInstance(MongodbQuery query){
        return new DeleteItem(query);
    }

    @Override
    public BulkOperations join(BulkOperations ops,Class<T> clazz) {
        return ops.remove(MongodbUtil.buildQuery(this.getQuery().catries()));
    }
}
