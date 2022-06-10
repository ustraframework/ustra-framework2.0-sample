package com.gsitm.ustra.java.sample.cache.eh;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cache/eh")
public class EhCacheController {
  
  @Autowired EhCacheService ehCacheService;

  @GetMapping()
  public List<EhCacheModel> getAll(String useYn) {
    EhCacheModel.Criteria criteria = new EhCacheModel.Criteria();
    criteria.setUseYn(useYn);

    return this.ehCacheService.getAll(criteria);
  }

  @GetMapping("/clear-cache")
  public String clearCache(String useYn) {
    EhCacheModel.Criteria criteria = new EhCacheModel.Criteria();
    criteria.setUseYn(useYn);

    return this.ehCacheService.clearCache(criteria);
  }
}
