package com.mmall.util;

import com.mmall.common.RedisShardedPool;
import com.mmall.common.RedisShardedPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

/**
 * @program: mmall
 * @author: yanrui
 * @description
 * @create: 2019-11-25 16:25
 */
@Slf4j
public class RedisShardedPoolUtil {

    public static Long expire(String key, int exTime) {
        ShardedJedis jedis;
        Long result;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.expire(key, exTime);
        } catch (Exception e) {
            log.error("expire key:{} ", key);
            return null;
        }
        return result;
    }


    public static String setex(String key, String value, int exTime) {
        ShardedJedis jedis;
        String result;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.setex(key, exTime, value);
        } catch (Exception e) {
            log.error("setex key:{} value:{}", key, value);
            return null;
        }
        return result;
    }

    public static Long setnx(String key, String value) {
        ShardedJedis jedis;
        Long result;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.setnx(key, value);
        } catch (Exception e) {
            log.error("setex key:{} value:{}", key, value);
            return null;
        }
        return result;
    }

    public static String set(String key, String value) {
        ShardedJedis jedis;
        String result;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("set key:{} value:{}", key, value);
            return null;
        }
        return result;
    }

    public static String getset(String key, String value) {
        ShardedJedis jedis;
        String result;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.getSet(key, value);
        } catch (Exception e) {
            log.error("set key:{} value:{}", key, value);
            return null;
        }
        return result;
    }

    public static String get(String key) {
        ShardedJedis jedis;
        String result;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get key:{} ", key);
            return null;
        }
        return result;
    }
    public static Long del(String key) {
        ShardedJedis jedis;
        Long result;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("del key:{} ", key);
            return null;
        }
        return result;
    }

}
