package com.gsitm.ustra.java.sample.cache.eh;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EhCacheService {

  public static final String EH_CACHE = "ustraEhCache";
  
  @Autowired private EhCacheRepository ehCacheRepository;

  @Cacheable(value=EH_CACHE, cacheManager = "eh-test")
  public List<EhCacheModel> getAll(EhCacheModel.Criteria criteria) {

    return this.ehCacheRepository.selectAll(criteria);
  }

  @CacheEvict(value=EH_CACHE, allEntries = true)
  public String clearCache(EhCacheModel.Criteria criteria) {

    String strReturn = "\"" + EH_CACHE + "\" Cache cleared.";
    return strReturn;
  }
}
