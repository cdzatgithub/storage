package com.elephant.basic.storage.mongo.update;

/**
 * @author chendongzhi
 * @date 13:252018/8/31 0031
 * @description 字段更新方式
 */
public enum FieldUpdateModel {

    /**
     * Sets the value of a field in a document.
     */
    SET,
    /**
     * Sets the value of a field if an update results in an insert of a document. Has no effect on update operations that modify existing documents
     */
    SET_ON_INSERT,
    /**
     * Removes the specified field from a document
     */
    UNSET,
    /**
     * Increments the value of the field by the specified amount.
     */
    INC,
    /**
     * Only updates the field if the specified value is less than the existing field value.
     */
    MIN,
    /**
     * Only updates the field if the specified value is greater than the existing field value.
     */
    MAX,
    /**
     * Multiplies the value of the field by the specified amount.
     */
    MUL,
    /**
     * Renames a field.
     */
    RENAME,
    /**
     * Sets the value of a field to current date, either as a Date or a Timestamp.
     */
    CURRENT_DATE,
    /**
     * Adds an item to an array.
     */
    PUSH,
    /**
     * Removes all array elements that match a specified query.
     */
    PULL,
    /**
     * Removes the first or last item of an array.
     */
    POP,
    /**
     * Removes all matching values from an array.
     */
    PULL_ALL,
    /**
     * Adds elements to an array only if they do not already exist in the set.
     */
    ADD_TO_SET
}
