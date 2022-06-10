package com.gsitm.ustra.java.sample.cache.caffeine;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CaffeineCacheRepository {
  
  List<CaffeineCacheModel> selectAll(CaffeineCacheModel.Criteria criteria);
}
