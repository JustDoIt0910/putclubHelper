package PutclubHelper.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository("redisCache")
public class RedisCacheManager implements RedisCache {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, (String)value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public String get(String key) {
        return (String)redisTemplate.opsForValue().get(key);
    }
}
