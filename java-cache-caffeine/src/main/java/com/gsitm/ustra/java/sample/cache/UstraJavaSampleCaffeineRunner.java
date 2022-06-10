package com.gsitm.ustra.java.sample.cache;

import java.io.IOException;

import com.gsitm.ustra.java.mvc.app.ServletApplicationRunner;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UstraJavaSampleCaffeineRunner extends ServletApplicationRunner {

  public static void main(String[] args) throws IOException {
    ServletApplicationRunner.run(UstraJavaSampleCaffeineRunner.class, args);
  }
}
