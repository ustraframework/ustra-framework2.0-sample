package com.gsitm.ustra.java.sample.cache.caffeine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CaffeineCacheService {

  public static final String CAFFEINE_CACHE = "USTRA-CAFFEINE-CACHE";
  
  @Autowired private CaffeineCacheRepository caffeineCacheRepository;

  @Cacheable(value=CAFFEINE_CACHE)
  public List<CaffeineCacheModel> getAll(CaffeineCacheModel.Criteria criteria) {
    return this.caffeineCacheRepository.selectAll(criteria);
  }

  @CacheEvict(value=CAFFEINE_CACHE, allEntries = true)
  public String clearCache(CaffeineCacheModel.Criteria criteria) {
    String strReturn = "\"" + CAFFEINE_CACHE + "\" Cache cleared.";
    return strReturn;
  }
}
