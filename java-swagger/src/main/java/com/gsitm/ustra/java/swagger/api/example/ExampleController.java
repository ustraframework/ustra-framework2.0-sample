package com.gsitm.ustra.java.swagger.api.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Example API", description = "Configure Code Swagger with U.STRA Framework")
@RestController
@RequestMapping("/api/example")
public class ExampleController {

    @PostMapping("/common/code")
    @Operation(summary = "전체 코드 목록 조회", description = "<strong>코드 전체 목록</strong>을 반환")
    public List<ExampleModel> getAll(@Parameter(description = "사용 여부") String useYn) {
        List<ExampleModel> list = new ArrayList<>();
        list.add(new ExampleModel("GRP_CD_000", "DTL_CD_000", "CD_NM_0000"));
        list.add(new ExampleModel("GRP_CD_001", "DTL_CD_001", "CD_NM_0001"));
        list.add(new ExampleModel("GRP_CD_002", "DTL_CD_002", "CD_NM_0002"));
        list.add(new ExampleModel("GRP_CD_003", "DTL_CD_003", "CD_NM_0003"));
        list.add(new ExampleModel("GRP_CD_004", "DTL_CD_004", "CD_NM_0004"));

        return list;
    }

    @PostMapping("/common/code/map")
    @Operation(summary = "Map 반환 API", description = "Map을 반환하는 API")
    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("map1", 1);
        map.put("map2", 2);
        map.put("map1000", 1000);

        return map;
    }
}