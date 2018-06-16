package im.hdy.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

@Component
public class RedisUtils {
    @Autowired
    private static JedisPool pool;

    public static void set(String key, String value) {
        RedisUtilHelp redis = new RedisUtilHelp(pool.getResource(), key);
        redis.set(value, true);
    }

    public static String get(String key) {
        RedisUtilHelp redis = new RedisUtilHelp(pool.getResource(), key);
        return redis.get(true);
    }

    public static void setAndExpire(String key, String value, int times) {
        RedisUtilHelp redis = new RedisUtilHelp(pool.getResource(), key);
        redis.setAndExpire(value, times, true);
    }

    public static boolean isExist(String key){
        RedisUtilHelp redis = new RedisUtilHelp(pool.getResource(), key);
        boolean exist = redis.exist();
        redis.close();
        return exist;
    }
}
