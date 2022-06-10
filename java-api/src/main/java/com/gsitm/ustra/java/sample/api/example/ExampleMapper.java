package com.gsitm.ustra.java.sample.api.example;

import com.gsitm.ustra.java.data.domains.PaginationList;
import com.gsitm.ustra.java.data.domains.PaginationRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExampleMapper {

    List<ExampleModel> selectAll(ExampleModel.Criteria criteria);
    PaginationList<ExampleModel> getCodes(PaginationRequest pageBound);
}
