package com.gsitm.ustra.sample.redis.redis.controller;

import com.gsitm.ustra.sample.redis.redis.model.Person;
import com.gsitm.ustra.sample.redis.redis.repository.RedisRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * redis sample Controller
 *
 * @author youngran.kwon@gsitm.com
 */
@Slf4j
@RequestMapping(value = "/redis")
@RestController
@AllArgsConstructor
public class RedisController {

    private RedisRepository redisRepository;

    @PostMapping("/setPerson")
    public void setPerson(@RequestBody Person person) {
        redisRepository.save(new Person(person.getId(), person.getName(), person.getAge()));
    }

    @GetMapping("/getPerson")
    public Person getPerson(@RequestParam(value = "id") String id) {
        Optional<Person> person = redisRepository.findById(id);
        return person.get();
    }

}
