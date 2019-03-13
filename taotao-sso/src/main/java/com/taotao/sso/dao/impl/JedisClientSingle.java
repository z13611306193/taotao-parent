package com.taotao.sso.dao.impl;

import com.taotao.sso.dao.JedisClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

public class JedisClientSingle implements JedisClient {

    @Resource
    private JedisPool jedisPool;

    @Override
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String s = jedis.get(key);
        jedis.close();
        return s;
    }

    @Override
    public String set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        String s = jedis.set(key,value);
        jedis.close();
        return s;
    }

    @Override
    public String hget(String hkey, String key) {
        Jedis jedis = jedisPool.getResource();
        String s = jedis.hget(hkey,key);
        jedis.close();
        return s;
    }

    @Override
    public Long hset(String hkey, String key, String value) {
        Jedis jedis = jedisPool.getResource();
        Long s = jedis.hset(hkey,key,value);
        jedis.close();
        return s;
    }

    @Override
    public Long incr(String key) {
        Jedis jedis = jedisPool.getResource();
        Long s = jedis.incr(key);
        jedis.close();
        return s;
    }

    @Override
    public Long expire(String key, int second) {
        Jedis jedis = jedisPool.getResource();
        Long s = jedis.expire(key,second);
        jedis.close();
        return s;
    }

    @Override
    public Long ttl(String key) {
        Jedis jedis = jedisPool.getResource();
        Long s = jedis.ttl(key);
        jedis.close();
        return s;
    }

    @Override
    public Long del(String key) {
        Jedis jedis = jedisPool.getResource();
        Long s = jedis.del(key);
        jedis.close();
        return s;
    }

    @Override
    public Long hdel(String hkey, String key) {
        Jedis jedis = jedisPool.getResource();
        Long s = jedis.hdel(hkey,key);
        jedis.close();
        return s;
    }
}
