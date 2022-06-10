package com.gsitm.ustra.java.sample.cache.caffeine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cache/caffeine")
public class CaffeineCacheController {
  
  @Autowired CaffeineCacheService caffeineCacheService;

  @GetMapping()
  public List<CaffeineCacheModel> getAll(String useYn) {
    CaffeineCacheModel.Criteria criteria = new CaffeineCacheModel.Criteria();
    criteria.setUseYn(useYn);

    return this.caffeineCacheService.getAll(criteria);
  }

  @GetMapping("/clear-cache")
  public String clearCache(String useYn) {
    CaffeineCacheModel.Criteria criteria = new CaffeineCacheModel.Criteria();
    criteria.setUseYn(useYn);

    return this.caffeineCacheService.clearCache(criteria);
  }
}
