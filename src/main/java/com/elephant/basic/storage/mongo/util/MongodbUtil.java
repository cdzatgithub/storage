package com.elephant.basic.storage.mongo.util;

import com.elephant.basic.storage.mongo.update.MongodbUpdateFeatures;
import com.elephant.basic.storage.mongo.annotation.UpdateNull;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * @author chendongzhi
 * @date 18:122018/9/17 0017
 * @description TODO(补充描述)
 */
public class MongodbUtil {
    private static final String[] ID_FIELD_NAMES = new String[]{"id"};
    private static final Logger logger = LoggerFactory.getLogger(MongodbUtil.class);
    public static Query buildQuery(Collection<CriteriaDefinition> criteriaDefinitions) {
        Query q = new Query();
        for (CriteriaDefinition criteriaDefinition : criteriaDefinitions) {
            q.addCriteria(criteriaDefinition);
        }
        logger.debug("mongodb查询语句：{}",q.toString());
        return q;
    }

    public static <T> Update buildUpdate(T document, Class<T> clazz, boolean updateNull, MongodbUpdateFeatures features) {
        Update update = new Update();
        for (Field field : clazz.getDeclaredFields()) {
            if (ArrayUtils.contains(ID_FIELD_NAMES, field.getName())) {
                continue;
            }
            if (field.getModifiers() == 26) {
                //private static final
                continue;
            }
            field.setAccessible(true);
            Object value = getField(field, document);
            UpdateNull u = field.getAnnotation(UpdateNull.class);
            if ((updateNull && u != null && u.value()) || value != null) {
                if (features == null || features.get(field.getName()) == null) {
                    update.set(field.getName(), getField(field, document));
                } else {
                    switch (features.get(field.getName())) {
                        case INC:
                            if (getField(field, document) instanceof Number)
                                update.inc(field.getName(), (Number) getField(field, document));
                            else
                                throw new UnsupportedOperationException("inc 只支持 Number类型字段");
                            break;
                        case MAX:
                            update.max(field.getName(), getField(field, document));
                            break;
                        case MIN:
                            update.min(field.getName(), getField(field, document));
                            break;
                        case MUL:
                            if (getField(field, document) instanceof Number)
                                update.multiply(field.getName(), (Number) getField(field, document));
                            else
                                throw new UnsupportedOperationException("mul 只支持 Number类型字段");
                            break;
                        case POP:
                            //TODO 暂不支持
                            throw new UnsupportedOperationException("暂时不支持 pop操作");
                        case SET:
                            update.set(field.getName(), getField(field, document));
                            break;
                        case PULL:
                            //TODO 暂不支持
                            throw new UnsupportedOperationException("暂时不支持 PULL操作");
                        case PUSH:
                            //TODO 暂不支持
                            throw new UnsupportedOperationException("暂时不支持 PUSH操作");
                        case UNSET:
                            update.unset(field.getName());
                            break;
                        case RENAME:
                            //TODO 暂不支持
                            throw new UnsupportedOperationException("暂时不支持 RENAME操作");
                        case PULL_ALL:
                            //TODO 暂不支持
                            throw new UnsupportedOperationException("暂时不支持 PULL_ALL操作");
                        case ADD_TO_SET:
                            //TODO 暂不支持
                            throw new UnsupportedOperationException("暂时不支持 ADD_TO_SET操作");
                        case CURRENT_DATE:
                            update.currentDate(field.getName());
                            break;
                        case SET_ON_INSERT:
                            update.setOnInsert(field.getName(), getField(field, document));
                            break;
                        default:
                            throw new UnsupportedOperationException("错误的操作类型");

                    }
                }
            }
        }
        logger.debug("mongodb更新语句：{}",update.toString());
        return update;
    }
    public static Object getField(Field field, Object target) {
        field.setAccessible(true);
        return ReflectionUtils.getField(field, target);
    }
}
