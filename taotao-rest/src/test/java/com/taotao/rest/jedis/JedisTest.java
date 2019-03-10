package com.taotao.rest.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

public class JedisTest {

    /**
     * 单机版
     */
    @Test
    public void testJedisSingle(){
        //创建一个Jedis对象
        Jedis jedis = new Jedis("172.16.184.135",6379);
        //调用Jedis对象的方法,方法名称和redis的命令一样
        jedis.set("key1","jedis test");
        String key1 = jedis.get("key1");
        System.out.println(key1);
        //关闭jedis
        jedis.close();
    }


    /**
     * 使用连接池
     */
    @Test
    public void testJedisPool(){
        //创建连接池
        JedisPool pool = new JedisPool("172.16.184.135",6379);
        //从连接池中获得Jedis对象
        Jedis jedis = pool.getResource();
        String key1 = jedis.get("key1");
        System.out.println(key1);
        //关闭jedis
        jedis.close();
        pool.close();
    }

    /**
     * 测试集群redis
     */
    @Test
    public void testJedisCluster(){
        Set<HostAndPort> set = new HashSet<>();
        set.add(new HostAndPort("172.16.184.135",7001));
        set.add(new HostAndPort("172.16.184.135",7002));
        set.add(new HostAndPort("172.16.184.135",7003));
        set.add(new HostAndPort("172.16.184.135",7004));
        set.add(new HostAndPort("172.16.184.135",7005));
        set.add(new HostAndPort("172.16.184.135",7006));

        JedisCluster jedisCluster = new JedisCluster(set);

        jedisCluster.set("key1","1000");

        String key1 = jedisCluster.get("key1");
        System.out.println(key1);
        jedisCluster.close();
    }

    /**
     * 单机版测试
     * <p>Title: testSpringJedisSingle</p>
     * <p>Description: </p>
     */
    @Test
    public void testSpringJedisSingle() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JedisPool pool = (JedisPool) applicationContext.getBean("redisClient");
        Jedis jedis = pool.getResource();
        String string = jedis.get("key1");
        System.out.println(string);
        jedis.close();
        pool.close();
    }

    @Test
    public void testSpringJedisCluster() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JedisCluster jedisCluster =  (JedisCluster) applicationContext.getBean("redisClient");
        String string = jedisCluster.get("key1");
        System.out.println(string);
        jedisCluster.close();
    }
}
