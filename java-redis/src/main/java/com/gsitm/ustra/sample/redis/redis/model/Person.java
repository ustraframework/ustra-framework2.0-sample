package com.gsitm.ustra.sample.redis.redis.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

/**
 * redis sample model
 *
 * @author youngran.kwon@gsitm.com
 */
@Getter
@Setter
@NoArgsConstructor
@RedisHash(value = "person", timeToLive = -1L)
public class Person {

    @Id
    private String id;
    private String name;
    private Integer age;
    private LocalDateTime createDt;

    public Person(String id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.createDt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", createDt=" + createDt +
                '}';
    }
}
