package com.gsitm.ustra.java.sample.api.example;

import com.gsitm.ustra.java.data.domains.PaginationList;
import com.gsitm.ustra.java.data.domains.PaginationRequest;
import com.gsitm.ustra.java.data.mybatis.proxy.MapperManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExampleService {

    @Autowired private ExampleMapper exampleMapper;

    public List<ExampleModel> getAll(ExampleModel.Criteria criteria) {
        return this.exampleMapper.selectAll(criteria);
    }

    public PaginationList<ExampleModel> getCodes(PaginationRequest request) {
        return MapperManager.get(ExampleMapper.class).getCodes(request);
    }
}
