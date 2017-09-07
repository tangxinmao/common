package com.socool.soft.common.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.socool.soft.common.util.Springfactory;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Component
public class RedisService {
//    private static Logger log = LoggerFactory.getLogger(RedisService.class);
    @Autowired
    private ShardedJedisPool pool;

    public static RedisService Instance() {
        return Springfactory.getBeanForClass(RedisService.class);
    }

    private ShardedJedis getRedisClient() {
        ShardedJedis shardJedis = null;
        try {
            shardJedis = pool.getResource();
        } catch (Exception e) {
            e.printStackTrace();
            if (shardJedis != null) {
                shardJedis.close();
            }
        }
        return shardJedis;
    }

    public int set(String key, String value, int timeout) {
        ShardedJedis jedis = getRedisClient();
        try {
            jedis.set(key, value);
            jedis.expire(key, timeout);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return 1;
    }

    public String get(String key) {
        ShardedJedis jedis = getRedisClient();
        try {
            return jedis.get(key);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }

    protected int setObject(String key, Object value, int timeout) {
        ShardedJedis jedis = getRedisClient();
        try {
            jedis.set(key.getBytes(), SerializeUtil.serialize(value));
            jedis.expire(key.getBytes(), timeout);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return 1;
    }

    protected Object getObject(String key) {
        ShardedJedis jedis = getRedisClient();
        try {
            byte[] bytes = jedis.get(key.getBytes());
            if(bytes != null) {
                return SerializeUtil.unserialize(bytes);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }

    protected int addList(String key, String value) {
        ShardedJedis jedis = getRedisClient();
        try {
            jedis.lpush(key, value);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return 1;
    }

    protected void delete(String key) {
        ShardedJedis jedis = getRedisClient();
        try {
            jedis.del(key.getBytes());
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    protected void deletePrefix(String keyPrefix) {
        ShardedJedis jedis = getRedisClient();
        try {
            List<String> keys = jedis.lrange(keyPrefix, 0, -1);
            for(String key : keys) {
                jedis.del(key.getBytes());
            }
            jedis.del(keyPrefix);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    protected int size(String keyPrefix) {
        ShardedJedis jedis = getRedisClient();
        try {
            return jedis.lrange(keyPrefix, 0, -1).size();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return 0;
    }
}
