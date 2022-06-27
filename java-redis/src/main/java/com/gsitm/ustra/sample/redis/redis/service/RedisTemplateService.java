package com.gsitm.ustra.sample.redis.redis.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Service
public class RedisTemplateService {

    private RedisTemplate<String, String> redisTemplate;

    public void deleteAll() {
        // 모든 key-value 삭제
        redisTemplate.delete(redisTemplate.keys("*"));
    }

    public void testForList() {
        deleteAll();
        ListOperations listOperations = redisTemplate.opsForList();

        String key = "key";
        String newKey = "newKey";

        // 왼쪽부터 데이터 저장
        listOperations.leftPush(key, "A");
        listOperations.leftPushAll(key, "B", "C", "D", "E", "F");
        log.info("Push: " + listOperations.range(key, 0, -1));
        // 실행결과 : [F, E, D, C, B, A]

        // 맨 왼쪽 데이터 삭제
        listOperations.leftPop(key);
        // 실행결과 : [E, D, C, B, A]
        // 맨 오른쪽 데이터 삭제
        listOperations.rightPop(key);
        // 실행결과 : [E, D, C, B]
        log.info("Pop: " + listOperations.range(key, 0, -1));

        // 데이터 수정 listKey[2]인 C를 X 로 변경
        listOperations.set(key, 2, "X");
        // 실행결과 : [E, D, X, B]
        log.info("Set: " + listOperations.range(key, 0, -1));

        // listKey[0] ~ listKey[1] 까지 남기고 나머지 삭제
        listOperations.trim(key, 0, 1);
        // 실행결과 : [E, D]
        log.info("trim: " + listOperations.range(key, 0, -1));

        // 오른쪽 데이터를 꺼내서 다른 key에 저장
        listOperations.rightPopAndLeftPush(key, newKey);
        // 실행결과 key : [E] newKey: [D]
        log.info("key: " + listOperations.range(key, 0, -1));
        log.info("newKey: " + listOperations.range(newKey, 0, -1));
    }

    public void testForSet() {
        deleteAll();

        SetOperations setOperations = redisTemplate.opsForSet();
        String key = "setKey";
        String otherKey = "otherKey";
        String destKey = "destKey";

        // 저장
        setOperations.add(key, "apple", "banana", "orange");
        setOperations.add(otherKey, "cherry", "banana", "peach");

        // 조회
        log.info("add: " + setOperations.members(key));
        log.info("add: " + setOperations.members(otherKey));
        // 실행결과 key: [orange, banana, apple] , otherKey : [banana, cherry, peach]

        // 삭제
        setOperations.remove(key, "apple");
        log.info("remove: " + setOperations.members(otherKey));
        // 실행결과 key: [orange, banana]

        // key, otherKey 의 members 를 비교해서 다른 멤버를 destKey에 저장
        setOperations.differenceAndStore(key, otherKey, destKey);
        // 실행결과 destKey: [orange]
        log.info("differenceAndStore: " + setOperations.members(destKey));

    }

    public void testForHash() {
        deleteAll();

        HashOperations hashOperations = redisTemplate.opsForHash();
        String key = "hashKey";
        Map<String, Object> map = new HashMap<>();
        map.put("name", "tester");
        map.put("age", "20");
        map.put("email", "tester@test.com");

        // 저장
        hashOperations.put(key, "nickname", "foo");
        hashOperations.put(key, "id", "id1");
        // 저장-multi
        hashOperations.putAll(key, map);

        // 조회
        log.info("get: " + hashOperations.get(key, "id"));      // id1
        // 조회-multi
        log.info("multiGet: " + hashOperations.multiGet(key, map.keySet())); // [tester, 20, tester@test.com]
        // 조회-entries
        log.info("entries: " + hashOperations.entries(key));    // {nickname=foo, id=id1, name=tester, age=20, email=tester@test.com}
        // 조회-모든 value
        log.info("values: " + hashOperations.values(key));     // [foo, id1, tester, 20, tester@test.com]
        // 조회-key 값에 대한 field가 있는지 조회
        hashOperations.hasKey(key, "name");
        log.info("hasKey: " + hashOperations.hasKey(key, "name")); // true

        // 수정
        hashOperations.put(key, "name", "newTester");
        log.info("put: " + hashOperations.entries(key));    //  {nickname=foo, id=id1, name=newTester, age=20, email=tester@test.com}

        // 삭제
        hashOperations.delete(key, "id");
        // 삭제-multi
        hashOperations.delete(key, "name", "age");
        log.info("delete: " + hashOperations.entries(key));    // {nickname=foo, email=tester@test.com}

    }

    public void testForString() {
        deleteAll();

        // given
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String key = "testStringKey";

        // when
        valueOperations.set(key, "testStringValue");

        // then
        String value = (String) valueOperations.get(key);
        log.info("value : " + value);
    }

}
