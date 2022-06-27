package com.gsitm.ustra.sample.redis.redis.repository;

import com.gsitm.ustra.sample.redis.redis.model.Person;
import org.springframework.data.repository.CrudRepository;

/**
 * redis sample repository
 *
 * @author youngran.kwon@gsitm.com
 */
public interface RedisRepository extends CrudRepository<Person, String> {
}
