package com.mmall.common;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @program: mmall
 * @author: yanrui
 * @description
 * @create: 2019-11-25 16:25
 */
@Slf4j
public class RedisPoolUtil {

    public static Long expire(String key, int exTime) {
        Jedis jedis;
        Long result;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.expire(key, exTime);
        } catch (Exception e) {
            log.error("expire key:{} ", key);
            return null;
        }
        return result;
    }


    public static String setex(String key, String value, int exTime) {
        Jedis jedis;
        String result;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.setex(key, exTime, value);
        } catch (Exception e) {
            log.error("setex key:{} value:{}", key, value);
            return null;
        }
        return result;
    }

    public static String set(String key, String value) {
        Jedis jedis;
        String result;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("set key:{} value:{}", key, value);
            return null;
        }
        return result;
    }

    public static String get(String key) {
        Jedis jedis;
        String result;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get key:{} ", key);
            return null;
        }
        return result;
    }
    public static Long del(String key) {
        Jedis jedis;
        Long result;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("del key:{} ", key);
            return null;
        }
        return result;
    }

}
