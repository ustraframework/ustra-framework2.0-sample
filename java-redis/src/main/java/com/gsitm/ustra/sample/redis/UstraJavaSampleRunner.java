package com.gsitm.ustra.sample.redis;

import com.gsitm.ustra.java.mvc.app.ServletApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.io.IOException;

@SpringBootApplication
@EnableRedisRepositories(value = "com.gsitm.ustra.sample.redis", enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP)
public class UstraJavaSampleRunner extends ServletApplicationRunner {
    public static void main(String[] args) throws IOException {
        ServletApplicationRunner.run(UstraJavaSampleRunner.class, args);
    }
}
