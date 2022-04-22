package PutclubHelper.cache;

public interface RedisCache {
    public boolean set(String key, Object value);
    public Object get(String key);
}
