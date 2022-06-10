package com.gsitm.ustra.java.sample.cache.eh;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EhCacheRepository {
  
  List<EhCacheModel> selectAll(EhCacheModel.Criteria criteria);
}
