package com.gsitm.ustra.sample.redis;

import com.gsitm.ustra.java.test.UstraSpringTest;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.*;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@EnableAutoConfiguration
@ComponentScan("com.gsitm.ustra.sample.redis")
public class RedisTemplateTest extends UstraSpringTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Before
    public void deleteAll() {
        // 모든 key-value 삭제
        redisTemplate.delete(redisTemplate.keys("*"));
    }

    @Test
    public void testForList() {
        ListOperations listOperations = redisTemplate.opsForList();

        String key = "listKey";
        String newKey = "newListKey";

        // 왼쪽부터 데이터 저장
        listOperations.leftPush(key, "A");
        listOperations.leftPushAll(key, "B", "C", "D", "E", "F");
        // 실행결과 : [F, E, D, C, B, A]

        // 맨 왼쪽 데이터 삭제
        listOperations.leftPop(key);
        // 실행결과 : [E, D, C, B, A]

        // 맨 오른쪽 데이터 삭제
        listOperations.rightPop(key);
        // 실행결과 : [E, D, C, B]

        // 데이터 수정 listKey[2]인 C를 X 로 변경
        listOperations.set(key, 2, "X");
        // 실행결과 : [E, D, X, B]

        // listKey[0] ~ listKey[1] 까지 남기고 나머지 삭제
        listOperations.trim(key, 0, 1);
        // 실행결과 : [E, D]

        // 오른쪽 데이터를 꺼내서 다른 key에 저장
        listOperations.rightPopAndLeftPush(key, newKey);
        // 실행결과 key : [E] newKey: [D]

        // 데이터 조회
        System.out.println(listOperations.range(key, 0, -1));
        System.out.println(listOperations.range(newKey, 0, -1));

    }

    @Test
    public void testSet() {
        SetOperations setOperations = redisTemplate.opsForSet();
        String key = "setKey";
        String otherKey = "otherKey";
        String destKey = "destKey";

        // 저장
        setOperations.add(key, "apple", "banana", "orange");
        setOperations.add(otherKey, "cherry", "banana", "peach");

        // 조회
        setOperations.members(key);
        setOperations.members(otherKey);
        // 실행결과 key: [orange, banana, apple] , otherKey : [banana, cherry, peach]

        // 삭제
        setOperations.remove(key, "apple");
        // 실행결과 key: [orange, banana]

        // key, otherKey 의 members 를 비교해서 다른 멤버를 destKey에 저장
        setOperations.differenceAndStore(key, otherKey, destKey);
        // 실행결과 destKey: [orange]
        System.out.println(setOperations.members(destKey));

    }

    @Test
    public void testHash() {
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
        System.out.println(hashOperations.get(key, "id"));      // id1
        // 조회-multi
        System.out.println(hashOperations.multiGet(key, map.keySet())); // [tester, 20, tester@test.com]
        // 조회-entries
        System.out.println(hashOperations.entries(key));    // {nickname=foo, id=id1, name=tester, age=20, email=tester@test.com}
        // 조회-모든 value
        System.out.println(hashOperations.values(key));     // [foo, id1, tester, 20, tester@test.com]
        // 조회-key 값에 대한 field가 있는지 조회
        hashOperations.hasKey(key, "name");
        System.out.println(hashOperations.hasKey(key, "name")); // true

        // 수정
        hashOperations.put(key, "name", "newTester");
        System.out.println(hashOperations.entries(key));    //  {nickname=foo, id=id1, name=newTester, age=20, email=tester@test.com}

        // 삭제
        hashOperations.delete(key, "id");
        // 삭제-multi
        hashOperations.delete(key, "name", "age");

        // 결과출력
        System.out.println(hashOperations.entries(key));    // {nickname=foo, email=tester@test.com}

    }

    @Test
    public void testForString() {
        // given
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String key = "stringKey";

        // when
        valueOperations.set(key, "otherValue");

        // then
        String value = (String) valueOperations.get(key);
        assertThat(value).isEqualTo("otherValue");
    }

}