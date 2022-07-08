package com.gsitm.ustra.java.swagger.api.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("Configure Code Swagger with U.STRA Framework")
@RestController
@RequestMapping("/api/example")
public class ExampleController {

    @PostMapping("/common/code")
    @ApiOperation(value = "전체 코드 목록 조회", notes = "<strong>코드 전체 목록</string>을 반환")
    public List<ExampleModel> getAll(@ApiParam("사용 여부") String useYn) {
        ExampleModel model1 = new ExampleModel("GRP_CD_000", "DTL_CD_000", "CD_NM_0000" );
        ExampleModel model2 = new ExampleModel("GRP_CD_001", "DTL_CD_001", "CD_NM_0001" );
        ExampleModel model3 = new ExampleModel("GRP_CD_002", "DTL_CD_002", "CD_NM_0002" );
        ExampleModel model4 = new ExampleModel("GRP_CD_003", "DTL_CD_003", "CD_NM_0003" );
        ExampleModel model5 = new ExampleModel("GRP_CD_004", "DTL_CD_004", "CD_NM_0004" );

        List<ExampleModel> list = new ArrayList<ExampleModel>();

        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);

        return list;
    }

    @PostMapping("/common/code/map")
    @ApiOperation(value = "Map 반환 API", notes = "Map을 반환하는 API")
    public Map<String, Object> getMap()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("map1", 1);
        map.put("map2", 2);
        map.put("map1000", 1000);

        return map;
    }
}
