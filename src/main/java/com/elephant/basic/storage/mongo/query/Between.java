package com.elephant.basic.storage.mongo.query;

import org.springframework.data.mongodb.core.query.Criteria;

/**
 * @author chendongzhi
 * @date 18:072018/9/27 0027
 * @description TODO(补充描述)
 */
public class Between extends QueryItem {
    private String key;
    private Object min;
    private Object max;
    private int mode;

    public Between(String key, Object min, Object max, int mode) {
        this.key = key;
        this.min = min;
        this.max = max;
        this.mode = mode;
    }

    public Between() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getMin() {
        return min;
    }

    public void setMin(Object min) {
        this.min = min;
    }

    public Object getMax() {
        return max;
    }

    public void setMax(Object max) {
        this.max = max;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public Criteria join() {
        if (this.min == null && this.max == null) {
            return null;
        }
        Criteria criteria = new Criteria(this.key);
        switch (mode) {
            case 0:
                if (this.min != null) {
                    criteria.gt(this.min);
                }
                if (this.max != null) {
                    criteria.lt(this.max);
                }
                break;
            case 1:
                if (this.min != null) {
                    criteria.gt(this.min);
                }
                if (this.max != null) {
                    criteria.lt(this.max);
                }
                break;
            case 2:
                if (this.min != null) {
                    criteria.gt(this.min);
                }
                if (this.max != null) {
                    criteria.lte(this.max);
                }
                break;
            case 3:
                if (this.min != null) {
                    criteria.gte(this.min);
                }
                if (this.max != null) {
                    criteria.lte(this.max);
                }
                break;
            default:
                return null;

        }
        return criteria;
    }
}
