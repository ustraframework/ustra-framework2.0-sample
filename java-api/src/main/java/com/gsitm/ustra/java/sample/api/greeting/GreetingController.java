package com.gsitm.ustra.java.sample.api.greeting;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api("Greetings API")
@RestController
@RequestMapping("/api/greeting")
public class GreetingController {
    @GetMapping
    @ApiOperation(value = "Greetings", notes = "Greetings from U.STRA")
    @ResponseBody
    public String getGreeting() {
        return "Hello, U.STRA!";
    }
}