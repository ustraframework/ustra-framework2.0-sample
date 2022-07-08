package com.gsitm.ustra.java.sample.api.example;

import java.util.List;

import com.gsitm.ustra.java.data.domains.PaginationList;
import com.gsitm.ustra.java.data.domains.PaginationRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/example")
public class ExampleController {

    @Autowired
    private ExampleService exampleService;

    @GetMapping("/common/code")
    public List<ExampleModel> getAll(String useYn) {

        ExampleModel.Criteria criteria = new ExampleModel.Criteria();
        criteria.setUseYn(useYn);

        return this.exampleService.getAll(criteria);
    }

    @PostMapping("/common/code/page")
    public PaginationList<ExampleModel> getCodesByPage(@RequestBody PaginationRequest request) {
        return this.exampleService.getCodes(request);
    }
    /* example for testing getCodesByPage() method using REST Client in VSCode:
POST http://localhost:8981/api/example/common/code/page HTTP/1.1
Content-Type: application/json

{
	"currentPage": 5,
	"pageSize": 5,
	"orders": [
		{ "name": "CD_NM", "direction": "ASC" }
	]
}
     */
}
