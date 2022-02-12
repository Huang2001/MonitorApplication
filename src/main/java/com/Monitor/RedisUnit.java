package com.Monitor;

import java.util.LinkedList;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUnit extends Thread {
    private static Jedis sendNumberJedis = null;
    private static JedisPool pool = null;
    private static final String channel = "numberlist";
    private static volatile LinkedList<String> messageList = new LinkedList();

    public RedisUnit() {
    }

    private static JedisPool getConnect() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxWaitMillis(1000000L);
        config.setMinIdle(3);
        config.setMinIdle(3);
        pool = new JedisPool(config, "1.117.251.143", 6379, 5000, "huanghengliangdonghualigongdaxue");
        return pool;
    }

    public static String get(String key) {
        while(true) {
            try {
                Jedis jedis = pool.getResource();
                String value = jedis.get(key);
                return value;
            } catch (Exception var5) {
                System.out.println("redis connect error");
                var5.printStackTrace();

                try {
                    pool = getConnect();
                } catch (Exception var4) {
                    System.out.println("重连失败");
                }
            }
        }
    }

    public static void addNumber(String message) {
        messageList.addFirst(message);
    }

    public void run() {
        this.setPriority(10);

        while(true) {
            String number = null;

            try {
                if (messageList.size() != 0) {
                    while(messageList.size() != 0) {
                        number = (String)messageList.peekLast();
                        sendNumberJedis.publish("numberlist", number);
                        messageList.pollLast();
                    }
                } else {
                    sendNumberJedis.ping();
                }
            } catch (Exception var5) {
                while(true) {
                    try {
                        getConnect();
                        sendNumberJedis = pool.getResource();
                        break;
                    } catch (Exception var4) {
                        var4.printStackTrace();
                    }
                }
            }
        }
    }

    static {
        try {
            getConnect();
        } catch (Exception var1) {
        }

    }
}
