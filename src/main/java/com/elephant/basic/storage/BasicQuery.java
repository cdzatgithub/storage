package com.elephant.basic.storage;

import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * @author chendongzhi
 * @date 9:512018/9/4 0004
 * @description 搜索信息通用类
 */
public abstract class BasicQuery<T> implements Serializable {

    protected BasicQuery(){

    }
    public BasicQuery(T queryBody,int skip,int limit,Sort sort){
        this.queryBody=queryBody;
        this.skip=skip;
        this.limit=limit;
        this.sort=sort;
    }

    public BasicQuery(T queryBody){
        this.queryBody=queryBody;
    }

    public BasicQuery(T queryBody,Sort sort){
        this(queryBody,0,0,sort);
    }

    public BasicQuery(T queryBody,int skip,int limit,String sortKey,Sort.Direction direction){
        this.queryBody=queryBody;
        this.skip=skip;
        this.limit=limit;
        if(sortKey!=null){
            if(direction==null){
                direction=Sort.Direction.ASC;
            }
            this.sort=new Sort(direction,sortKey);
        }
    }
    public BasicQuery( T queryBody,String sortKey, Sort.Direction direction){
        this(queryBody,0,0,sortKey,direction);
    }
    public BasicQuery(T queryBody, String sortKey){
        this(queryBody,0,0,sortKey,Sort.Direction.ASC);
    }
    protected T queryBody;
    /**
     * 跳过条数
     */
    protected int skip;
    /**
     * 查询条数
     */
    protected int limit;

    protected Sort sort;

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public T getQueryBody() {
        return queryBody;
    }

    public void setQueryBody(T queryBody) {
        this.queryBody = queryBody;
    }
}
