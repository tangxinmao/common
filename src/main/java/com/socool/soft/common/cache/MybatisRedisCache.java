package com.socool.soft.common.cache;

import org.apache.ibatis.cache.Cache;
import org.apache.log4j.Logger;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Mybatis二级缓存(Redis版)
 */
public class MybatisRedisCache implements Cache {
    /**
     * 默认缓存时间1天
     */
    private static final int DEFAULT_CACHE_TIME = 30 * 3600 * 24;
    private static Logger log = Logger.getLogger(MybatisRedisCache.class);
    private String id;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public MybatisRedisCache(String id) {
        log.info("[MybatisRedisCache] -> " + id);
        this.id = id;
    }
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        key = id + ":" + key;
        log.info("put -> key: " + replaceBlank(key.toString()) + " ; value: " + value);
        RedisService.Instance().setObject(key.toString(), value, DEFAULT_CACHE_TIME);
        RedisService.Instance().addList(id, key.toString());
    }

    @Override
    public Object getObject(Object key) {
        key = id + ":" + key;
        Object value = RedisService.Instance().getObject(key.toString());
        log.info("get -> key: " + replaceBlank(key.toString()) + " ; value: " + value);
        return value;
    }

    @Override
    public Object removeObject(Object key) {
        key = id + ":" + key;
        log.info("remove -> key: " + key);
        RedisService.Instance().delete(key.toString());
        return null;
    }

    @Override
    public void clear() {
        log.info("clear -> id: " + id);
        RedisService.Instance().deletePrefix(id);
    }

    @Override
    public int getSize() {
        int size = RedisService.Instance().size(id);
        log.info("size: " + size);
        return size;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}