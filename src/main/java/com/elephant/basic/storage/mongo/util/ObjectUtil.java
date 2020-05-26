package com.elephant.basic.storage.mongo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author chendongzhi
 * @date 10:452018/9/28 0028
 * @description TODO(补充描述)
 */
public class ObjectUtil implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(ObjectUtil.class);

    /**
     * 序列化对象
     * @param object
     * @return
     */
    public static byte[] serializa(Object object) {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            return bos.toByteArray();
        } catch (Exception e) {
            logger.error("序列化对象失败：", e);
            return null;
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 反序列化对象
     * @param bytes
     * @return
     */
    public static Object deSerializa(byte[] bytes) {
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (Exception e) {
            logger.error("反序列化对象失败：", e);
            return null;
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
