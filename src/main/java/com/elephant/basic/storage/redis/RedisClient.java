package com.elephant.basic.storage.redis;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisListCommands;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.*;


/**
 * @author: chendognzhi
 * @Date: 2017年10月21日
 * @Description: redis服务实现类
 */

@Component("redisClient")
public class RedisClient implements IRedisClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisClient.class);
    private static final String LOGGER_MSG = "redis 服务实现类";
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    public RedisClient(RedisTemplate redisTemplate){
        this.redisTemplate=redisTemplate;
    }
    public final static String CHARSET = "UTF-8";

    @Override
    public Boolean expire(final String key, final int seconds) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.expire(key.getBytes(CHARSET), seconds);
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                    return false;
                }
            }
        });
    }

    @Override
    public boolean set(final String key, final String value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    connection.set(key.getBytes(CHARSET), value.getBytes(CHARSET));
                    return true;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                    return false;
                }
            }

        });
    }

    @Override
    public boolean exists(String key) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.exists(key.getBytes(CHARSET));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                    return false;
                }
            }
        });
    }

    @Override
    public boolean set(final String key, final Object value, final int seconds) {
        String objectJson = JSONObject.toJSONString(value);
        return set(key, objectJson, seconds);
    }

    @Override
    public boolean setNX(final String key, final String value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.setNX(key.getBytes(CHARSET), value.getBytes(CHARSET));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                    return false;
                }
            }

        });
    }

    @Override
    public boolean set(String key, String value, int seconds) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    connection.set(key.getBytes(CHARSET), value.getBytes(CHARSET));
                    if (seconds > 0) {
                        connection.expire(key.getBytes(CHARSET), seconds);
                    }
                    return true;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                    return false;
                }
            }

        });
    }

    @Override
    public boolean set(final String key, Object value) {
        String objectJson = JSONObject.toJSONString(value);
        return set(key, objectJson);
    }

    @Override
    public boolean del(final String key) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    connection.del(key.getBytes(CHARSET));
                    return true;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                    return false;
                }
            }
        });
    }

    @Override
    public <T> T get(final String key, Class<T> clazz) {
        String value = get(key);
        return StringUtils.isBlank(value) ? null : JSONObject.parseObject(value, clazz);
    }

    @Override
    public boolean setBit(String key, long offset, boolean value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.setBit(key.getBytes(CHARSET), offset, value);
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public String getRange(String key, long start, long end) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    byte[] bs = connection.getRange(key.getBytes(CHARSET), start, end);
                    if (bs != null) {
                        return new String(bs, CHARSET);
                    }
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public String getSet(String key, String value) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    byte[] bs = connection.getSet(key.getBytes(CHARSET), value.getBytes(CHARSET));
                    if (bs != null) {
                        return new String(bs, CHARSET);
                    }
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public Boolean getBit(String key, long offset) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.getBit(key.getBytes(CHARSET), offset);

                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }


    @Override
    public boolean setEx(String key, int seconds, String value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    connection.setEx(key.getBytes(CHARSET), seconds, value.getBytes(CHARSET));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public boolean setRange(String key, long offset, String value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    connection.setRange(key.getBytes(CHARSET), value.getBytes(CHARSET), offset);

                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public long strLen(String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.strLen(key.getBytes(CHARSET));

                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public boolean pSetEx(String key, String value, long milliseconds) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    connection.pSetEx(key.getBytes(CHARSET), milliseconds, value.getBytes(CHARSET));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public long iNcr(String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {

                    return connection.incr(key.getBytes(CHARSET));

                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public long incrBy(String key, long increment) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {

                    return connection.incrBy(key.getBytes(CHARSET), increment);

                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public double incrByFloat(String key, double increment) {
        return redisTemplate.execute(new RedisCallback<Double>() {
            @Override
            public Double doInRedis(RedisConnection connection) throws DataAccessException {
                try {

                    return connection.incrBy(key.getBytes(CHARSET), increment);

                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0.0;
            }
        });
    }

    @Override
    public long decr(String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {

                    return connection.decr(key.getBytes(CHARSET));

                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public long decrBy(String key, long increment) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {

                    return connection.decrBy(key.getBytes(CHARSET), increment);

                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public long append(String key, String value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {

                    return connection.append(key.getBytes(CHARSET), value.getBytes(CHARSET));

                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public String get(final String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    byte[] bs = connection.get(key.getBytes(CHARSET));
                    if (bs != null) {
                        return new String(bs, CHARSET);
                    }
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public String bLPop(String key, Integer timeout) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {

                    List<byte[]> result = connection.bLPop(timeout, key.getBytes(CHARSET));
                    return result == null || result.isEmpty() ? null : new String(result.get(0), CHARSET);

                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public String bRPop(String key, Integer timeout) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {

                    List<byte[]> result = connection.bRPop(timeout, key.getBytes(CHARSET));
                    return result == null || result.isEmpty() ? null : new String(result.get(0), CHARSET);

                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public String lIndex(String key, Long index) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {

                    byte[] result = connection.lIndex(key.getBytes(CHARSET), index);
                    return result == null ? null : new String(result, CHARSET);

                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public long lInsert(String key, RedisListCommands.Position where, String pivot, String value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {

                    return connection.lInsert(key.getBytes(CHARSET), where, pivot.getBytes(CHARSET), value.getBytes(CHARSET));

                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public Long lLen(String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {

                    return connection.lLen(key.getBytes(CHARSET));

                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public String lPop(String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    byte[] result = connection.lPop(key.getBytes(CHARSET));
                    return result == null ? null : new String(result, CHARSET);
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public long lPush(String key, String... value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    List<byte[]> values = new ArrayList<>();
                    for (String item : value) {
                        values.add(item.getBytes(CHARSET));
                    }
                    return connection.lPush(key.getBytes(CHARSET), values.toArray(new byte[0][0]));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public long lPushX(String key, String value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.lPushX(key.getBytes(CHARSET), value.getBytes(CHARSET));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public List<String> lRange(String key, long start, long stop) {
        return redisTemplate.execute(new RedisCallback<List<String>>() {
            @Override
            public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    List resultBytes = connection.lRange(key.getBytes(CHARSET), start, stop);
                    if (resultBytes != null && !resultBytes.isEmpty()) {
                        for (int i = 0; i < resultBytes.size(); i++) {
                            resultBytes.set(i, new String((byte[]) resultBytes.get(i), CHARSET));
                        }
                    }
                    return resultBytes;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public long lRem(String key, long count, String value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.lRem(key.getBytes(CHARSET), count, value.getBytes(CHARSET));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public boolean lSet(String key, long index, String value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    connection.lSet(key.getBytes(CHARSET), index, value.getBytes(CHARSET));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public boolean lTrim(String key, long start, long stop) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    connection.lTrim(key.getBytes(CHARSET), start, stop);
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public String rPop(String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    byte[] bs = connection.rPop(key.getBytes(CHARSET));
                    return bs == null ? null : new String(bs, CHARSET);
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public long rPush(String key, String... value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    List<byte[]> values = new ArrayList<>();
                    for (String item : value) {
                        values.add(item.getBytes(CHARSET));
                    }
                    return connection.rPush(key.getBytes(CHARSET), values.toArray(new byte[0][0]));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public long rPushX(String key, String value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.rPushX(key.getBytes(CHARSET), value.getBytes(CHARSET));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public long hDel(String key, String... field) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    List<byte[]> fields = new ArrayList<>();
                    for (String item : field) {
                        fields.add(item.getBytes(CHARSET));
                    }
                    return connection.hDel(key.getBytes(CHARSET), fields.toArray(new byte[0][0]));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public boolean hExists(String key, String field) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.hExists(key.getBytes(CHARSET), field.getBytes(CHARSET));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return false;
            }
        });
    }

    @Override
    public String hGet(String key, String field) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    byte[] result = connection.hGet(key.getBytes(CHARSET), field.getBytes(CHARSET));
                    return result == null ? null : new String(result, CHARSET);
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public Map<String, String> hGetAll(String key) {
        return redisTemplate.execute(new RedisCallback<Map<String, String>>() {
            @Override
            public Map<String, String> doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    Map<byte[], byte[]> byteResult = connection.hGetAll(key.getBytes(CHARSET));
                    Map<String, String> result = new HashMap<>();
                    if (byteResult != null && !byteResult.isEmpty()) {
                        for (Map.Entry<byte[], byte[]> item : byteResult.entrySet()) {
                            result.put(new String(item.getKey(), CHARSET), new String(item.getValue(), CHARSET));
                        }
                    }
                    return result;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public long hIncrBy(String key, String field, long increment) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.hIncrBy(key.getBytes(CHARSET), field.getBytes(CHARSET), increment);
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public double hIncrByFoloat(String key, String field, double increment) {
        return redisTemplate.execute(new RedisCallback<Double>() {
            @Override
            public Double doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.hIncrBy(key.getBytes(CHARSET), field.getBytes(CHARSET), increment);
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0.0;
            }
        });
    }

    @Override
    public Set<String> hKeys(String key) {
        return redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    Set<byte[]> byteResult = connection.hKeys(key.getBytes(CHARSET));
                    Set<String> result = new HashSet<>();
                    if (byteResult != null && !byteResult.isEmpty()) {
                        for (byte[] item : byteResult) {
                            result.add(new String(item, CHARSET));
                        }
                    }
                    return result;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public long hLen(String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.hLen(key.getBytes(CHARSET));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public List<String> hMGet(String key, String... field) {
        return redisTemplate.execute(new RedisCallback<List<String>>() {
            @Override
            public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    List<byte[]> fields = new ArrayList<>();
                    for (String item : field) {
                        fields.add(item.getBytes(CHARSET));
                    }
                    List<byte[]> byteResult = connection.hMGet(key.getBytes(CHARSET), fields.toArray(new byte[0][0]));
                    List<String> result = new ArrayList<>();
                    if (byteResult != null && !byteResult.isEmpty()) {
                        for (byte[] item : byteResult) {
                            result.add(new String(item, CHARSET));
                        }
                    }
                    return result;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public boolean hMSet(String key, Map<String, String> items) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    Map<byte[], byte[]> fields = new HashMap<>();
                    for (Map.Entry<String, String> item : items.entrySet()) {
                        fields.put(item.getKey().getBytes(CHARSET), item.getValue().getBytes(CHARSET));
                    }
                    connection.hMSet(key.getBytes(CHARSET), fields);
                    return true;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return false;
            }
        });
    }

    @Override
    public boolean hSet(String key, String field, String value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    connection.hSet(key.getBytes(CHARSET), field.getBytes(CHARSET), value.getBytes(CHARSET));
                    return true;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return false;
            }
        });
    }

    @Override
    public boolean hSetNx(String key, String field, String value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.hSetNX(key.getBytes(CHARSET), field.getBytes(CHARSET), value.getBytes(CHARSET));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return false;
            }
        });
    }

    @Override
    public List<String> hVals(String key) {
        return redisTemplate.execute(new RedisCallback<List<String>>() {
            @Override
            public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    List<byte[]> byteResult = connection.hVals(key.getBytes(CHARSET));
                    List<String> result = new ArrayList<>();
                    if (byteResult != null && !byteResult.isEmpty()) {
                        for (byte[] item : byteResult) {
                            result.add(new String(item, CHARSET));
                        }
                    }
                    return result;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public Cursor<Map.Entry<byte[], byte[]>> hScan(String key, String pattern, long count) {
        return redisTemplate.execute(new RedisCallback<Cursor<Map.Entry<byte[], byte[]>>>() {
            @Override
            public Cursor<Map.Entry<byte[], byte[]>> doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    ScanOptions options = ScanOptions.scanOptions().match(pattern).count(count).build();
                    return connection.hScan(key.getBytes(CHARSET), options);

                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public long sAdd(String key, String... members) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    List<byte[]> values = new ArrayList<>();
                    for (String member : members) {
                        values.add(member.getBytes(CHARSET));
                    }
                    return connection.sAdd(key.getBytes(CHARSET), values.toArray(new byte[0][0]));

                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public long sCard(String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {

                    return connection.sCard(key.getBytes(CHARSET));

                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public boolean sIsMember(String key, String member) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.sIsMember(key.getBytes(CHARSET), member.getBytes(CHARSET));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return false;
            }
        });
    }

    @Override
    public Set<String> sMembers(String key) {
        return redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    Set<byte[]> resultBytes = connection.sMembers(key.getBytes(CHARSET));
                    Set<String> result = new HashSet<>();
                    if (resultBytes != null && !resultBytes.isEmpty()) {
                        for (byte[] b : resultBytes) {
                            result.add(new String(b, CHARSET));
                        }
                    }
                    return result;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public String sPop(String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    byte[] result = connection.sPop(key.getBytes(CHARSET));
                    return result == null ? null : new String(result, CHARSET);
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public List<String> sRandMember(String key, Long count) {
        return redisTemplate.execute(new RedisCallback<List<String>>() {
            @Override
            public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    List<byte[]> bytesResult = connection.sRandMember(key.getBytes(CHARSET), count);
                    List<String> result = new ArrayList<>();
                    if (bytesResult != null && !bytesResult.isEmpty()) {
                        for (byte[] bs : bytesResult) {
                            result.add(new String(bs, CHARSET));
                        }
                    }
                    return result;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public String sRandMember(String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    byte[] bytes = connection.sRandMember(key.getBytes(CHARSET));
                    return bytes == null ? null : new String(bytes, CHARSET);
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public long sRem(String key, String... member) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    List<byte[]> members = new ArrayList<>();
                    for (String item : member) {
                        members.add(item.getBytes(CHARSET));
                    }
                    return connection.sRem(key.getBytes(CHARSET), members.toArray(new byte[0][0]));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public Cursor<byte[]> sScan(String key, String cursor, String pattern, long count) {
        return redisTemplate.execute(new RedisCallback<Cursor<byte[]>>() {
            @Override
            public Cursor<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
                ScanOptions options = ScanOptions.scanOptions().count(count).match(pattern).build();
                try {
                    return connection.sScan(key.getBytes(CHARSET), options);
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public long zAdd(String key, Set<RedisZSetCommands.Tuple> tuples) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.zAdd(key.getBytes(CHARSET), tuples);
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public boolean zAdd(String key, String value, double score) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.zAdd(key.getBytes(CHARSET), score, value.getBytes(CHARSET));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return false;
            }
        });
    }

    @Override
    public long zCard(String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.zCard(key.getBytes(CHARSET));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public long zCount(String key, double min, double max) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.zCount(key.getBytes(CHARSET), min, max);
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public double zIncrBy(String key, double increment, String member) {
        return redisTemplate.execute(new RedisCallback<Double>() {
            @Override
            public Double doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.zIncrBy(key.getBytes(CHARSET), increment, member.getBytes(CHARSET));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0.0;
            }
        });
    }

    @Override
    public Set<String> zRange(String key, long start, long stop) {
        return redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    Set<byte[]> bytesResult = connection.zRange(key.getBytes(CHARSET), start, stop);
                    Set<String> result = new LinkedHashSet<>();
                    if (bytesResult != null && !bytesResult.isEmpty()) {
                        for (byte[] bs : bytesResult) {
                            result.add(new String(bs, CHARSET));
                        }
                    }
                    return result;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public Set zRange(String key, long start, long stop, Class clazz) {
        return redisTemplate.execute(new RedisCallback<Set>() {
            @Override
            public Set doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    Set<byte[]> bytesResult = connection.zRange(key.getBytes(CHARSET), start, stop);
                    Set result = new LinkedHashSet();
                    if (bytesResult != null && !bytesResult.isEmpty()) {
                        for (byte[] bs : bytesResult) {
                            result.add(JSONObject.parseObject(bs, clazz));
                        }
                    }
                    return result;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public Set<String> zRangeByLex(String key) {
        return redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    Set<byte[]> bytesResult = connection.zRangeByLex(key.getBytes(CHARSET));
                    Set<String> result = new LinkedHashSet<>();
                    if (bytesResult != null && !bytesResult.isEmpty()) {
                        for (byte[] bs : bytesResult) {
                            result.add(new String(bs, CHARSET));
                        }
                    }
                    return result;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public Set<String> zRangeByLex(String key, String min, String max) {
        return redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    Set<byte[]> bytesResult = connection.zRangeByLex(key.getBytes(CHARSET), RedisZSetCommands.Range.range().gte(min).lte(max));
                    Set<String> result = new LinkedHashSet<>();
                    if (bytesResult != null && !bytesResult.isEmpty()) {
                        for (byte[] bs : bytesResult) {
                            result.add(new String(bs, CHARSET));
                        }
                    }
                    return result;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public Set<String> zRangeByLex(String key, String min, String max, int count, int offset) {
        return redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    Set<byte[]> bytesResult = connection.zRangeByLex(key.getBytes(CHARSET), RedisZSetCommands.Range.range().gte(min).lte(max), RedisZSetCommands.Limit.limit().count(count).offset(offset));
                    Set<String> result = new LinkedHashSet<>();
                    if (bytesResult != null && !bytesResult.isEmpty()) {
                        for (byte[] bs : bytesResult) {
                            result.add(new String(bs, CHARSET));
                        }
                    }
                    return result;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public Set<String> zRangeByScore(String key, double min, double max) {
        return redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    Set<byte[]> bytesResult = connection.zRangeByScore(key.getBytes(CHARSET), min, max);
                    Set<String> result = new LinkedHashSet<>();
                    if (bytesResult != null && !bytesResult.isEmpty()) {
                        for (byte[] bs : bytesResult) {
                            result.add(new String(bs, CHARSET));
                        }
                    }
                    return result;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public Set zRangeByScore(String key, double min, double max, Class<?> clazz) {
        return redisTemplate.execute(new RedisCallback<Set>() {
            @Override
            public Set doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    Set<byte[]> bytesResult = connection.zRangeByScore(key.getBytes(CHARSET), min, max);
                    Set result = new LinkedHashSet<>();
                    if (bytesResult != null && !bytesResult.isEmpty()) {
                        for (byte[] bs : bytesResult) {
                            result.add(JSONObject.parseObject(bs, clazz));
                        }
                    }
                    return result;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public Set<String> zRangeByScore(String key, double min, double max, long count, long offset) {
        return redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    Set<byte[]> bytesResult = connection.zRangeByScore(key.getBytes(CHARSET), min, max, offset, count);
                    Set<String> result = new LinkedHashSet<>();
                    if (bytesResult != null && !bytesResult.isEmpty()) {
                        for (byte[] bs : bytesResult) {
                            result.add(new String(bs, CHARSET));
                        }
                    }
                    return result;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public long zRank(String key, String member) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.zRank(key.getBytes(CHARSET), member.getBytes(CHARSET));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public long zRem(String key, String... member) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    List<byte[]> members = new ArrayList<>();
                    for (String item : member) {
                        members.add(item.getBytes(CHARSET));
                    }
                    return connection.zRem(key.getBytes(CHARSET), members.toArray(new byte[0][0]));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public long zRemRangeByRank(String key, long start, long stop) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.zRemRange(key.getBytes(CHARSET), start, stop);
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public long zRemRangeByScore(String key, double min, double max) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.zRemRangeByScore(key.getBytes(CHARSET), min, max);
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public Set<String> zRevRange(String key, long start, long stop) {
        return redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    Set<byte[]> bytesResult = connection.zRevRange(key.getBytes(CHARSET), start, stop);
                    Set<String> result = new LinkedHashSet<>();
                    if (bytesResult != null && !bytesResult.isEmpty()) {
                        for (byte[] bs : bytesResult) {
                            result.add(new String(bs, CHARSET));
                        }
                    }
                    return result;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public Set zRevRange(String key, long start, long stop, Class clazz) {
        return redisTemplate.execute(new RedisCallback<Set>() {
            @Override
            public Set doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    Set<byte[]> bytesResult = connection.zRevRange(key.getBytes(CHARSET), start, stop);
                    Set result = new LinkedHashSet<>();
                    if (bytesResult != null && !bytesResult.isEmpty()) {
                        for (byte[] bs : bytesResult) {
                            result.add(JSONObject.parseObject(bs, clazz));
                        }
                    }
                    return result;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public Set<String> zRevRangeByScore(String key, double min, double max) {
        return redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    Set<byte[]> bytesResult = connection.zRevRangeByScore(key.getBytes(CHARSET), RedisZSetCommands.Range.range().gte(min).lte(max));
                    Set<String> result = new LinkedHashSet<>();
                    if (bytesResult != null && !bytesResult.isEmpty()) {
                        for (byte[] bs : bytesResult) {
                            result.add(new String(bs, CHARSET));
                        }
                    }
                    return result;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public Set<String> zRevRangeByScore(String key, double min, double max, int offset, int count) {
        return redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    Set<byte[]> bytesResult = connection.zRevRangeByScore(key.getBytes(CHARSET), RedisZSetCommands.Range.range().gte(min).lte(max), RedisZSetCommands.Limit.limit().offset(offset).count(count));
                    Set<String> result = new LinkedHashSet<>();
                    if (bytesResult != null && !bytesResult.isEmpty()) {
                        for (byte[] bs : bytesResult) {
                            result.add(new String(bs, CHARSET));
                        }
                    }
                    return result;
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }

    @Override
    public long zRevRank(String key, String member) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.zRevRank(key.getBytes(CHARSET), member.getBytes(CHARSET));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0L;
            }
        });
    }

    @Override
    public double zScore(String key, String member) {
        return redisTemplate.execute(new RedisCallback<Double>() {
            @Override
            public Double doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.zScore(key.getBytes(CHARSET), member.getBytes(CHARSET));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return 0.0;
            }
        });
    }

    @Override
    public Cursor<RedisZSetCommands.Tuple> zScan(String key, String ursor, String pattern, long count) {
        return redisTemplate.execute(new RedisCallback<Cursor<RedisZSetCommands.Tuple>>() {
            @Override
            public Cursor<RedisZSetCommands.Tuple> doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return connection.zScan(key.getBytes(CHARSET), ScanOptions.scanOptions().match(pattern).count(count).build());
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("{},异常，异常信息：{}", LOGGER_MSG, e.getMessage(), e);
                }
                return null;
            }
        });
    }
}