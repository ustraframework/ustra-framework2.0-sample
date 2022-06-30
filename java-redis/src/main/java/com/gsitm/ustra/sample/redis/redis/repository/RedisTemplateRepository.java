package com.gsitm.ustra.sample.redis.redis.repository;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * redis sample repository
 *
 * @author youngran.kwon@gsitm.com
 */
@Repository
@AllArgsConstructor
public class RedisTemplateRepository {

    private RedisTemplate<String, String> redisTemplate;

    public void saveString(String key, String value) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    public String getString(String key) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String value = (String) valueOperations.get(key);
        return value;
    }

    public void saveSet(String key, String values) {
        SetOperations setOperations = redisTemplate.opsForSet();
        String[] tempStr = values.split("\\|");
        for (int i = 0; i < tempStr.length; i++) {
            setOperations.add(key, tempStr[i]);
        }
    }

    public Set<String> getSet(String key) {
        SetOperations setOperations = redisTemplate.opsForSet();
        Set<String> value = setOperations.members(key);
        return value;
    }

    public void saveHash(String key, String hashKey, String value) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key, hashKey, value);
    }

    public String getHash(String key, String hashKey) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        String value = (String) hashOperations.get(key, hashKey);
        return value;
    }

}
