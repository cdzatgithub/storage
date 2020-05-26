package com.elephant.basic.storage;

import java.util.Collection;
import java.util.List;

/**
 * @author chendongzhi
 * @date 9:502018/9/4 0004
 * @description 存储介质通用操作接口
 */
public interface CommonService<T,Q extends BasicQuery,F extends UpdateFeatures> {
    /**
     * 根据id查询
     * @param id
     * @return
     */
    T find(String id, Class<T> clazz);
    /**
     * 查询列表
     * @param query
     * @return
     */
    List<T> find(Q query, Class<T> clazz);

    /**
     * 根据查询条件查询一条
     * @param query
     * @return
     */
    T findOne(Q query, Class<T> clazz);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    long delete(String id, Class<T> clazz);
    /**
     * 根据id删除
     * @param id
     * @return
     */
    long delete(String id, Class<T> clazz, String collectionName);

    /**
     * 根据查询条件删除
     * @param query
     * @return
     */
    long delete(Q query, Class<T> clazz);
    /**
     * 根据查询条件删除
     * @param query
     * @return
     */
    long delete(Q query);

    /**
     * 插入
     * @param document
     * @return
     */
    int insert(T document);

    /**
     * 批量插入
     * @param documents
     * @return
     */
    int insert(Collection<T> documents, Class<T> clazz);

    /**
     *
     * @param query
     * @param document
     * @param clazz
     * @param multi
     * @param updateNull
     * @param features
     * @return
     */
    long update(Q query, T document, Class<T> clazz, boolean multi, boolean updateNull, F features);

    /**
     *
     * @param id
     * @param document
     * @param clazz
     * @param multi
     * @param updateNull
     * @return
     */
    long update(Q id, T document, Class<T> clazz, boolean multi, boolean updateNull);

    /**
     *
     * @param query
     * @param document
     * @param clazz
     * @param updateNull
     * @param features
     * @return
     */
    long upsert(Q query, T document, Class<T> clazz, boolean updateNull, F features);

    /**
     * 更新
     * @param document
     * @return
     */
    long update(Q query, T document, Class<T> clazz, boolean upsert, boolean multi, boolean updateNull, F features);


    /**
     * 根据id判断是否存在
     * @param id
     * @return
     */
    boolean exist(String id, Class<T> clazz);

    /**
     * 根据查询条件判断是否存在
     * @param query
     * @return
     */
    boolean exist(Q query);

}
