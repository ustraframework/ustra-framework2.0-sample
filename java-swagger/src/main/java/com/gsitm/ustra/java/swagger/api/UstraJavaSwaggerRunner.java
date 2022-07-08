package com.gsitm.ustra.java.swagger.api;

import java.io.IOException;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gsitm.ustra.java.mvc.app.ServletApplicationRunner;

@SpringBootApplication
public class UstraJavaSwaggerRunner extends ServletApplicationRunner {
    public static void main(String[] args) throws IOException {
        ServletApplicationRunner.run(UstraJavaSwaggerRunner.class, args);
    }
}