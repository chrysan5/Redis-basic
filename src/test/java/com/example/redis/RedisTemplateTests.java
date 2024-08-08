package com.example.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

//RedisTemplate 로 레디스 연동
@SpringBootTest
public class RedisTemplateTests {
    @Autowired
    private StringRedisTemplate stringRedisTemplate; 
    //StringRedisTemplate는 redisTemplate을 상속받음. redis와 소통하기 위한 템플릿의 모음
    //여기서 String은 레디스의 문자열을 의미하지 않는다. 레디스와 소통 위한 자바의 자료형을 의미함 -> 레디스의 여러가지 타입을 자바의 문자열과 연결된다는 의미임.

    //=> 모든 데이터를 자바의 문자열로 주고받을시 간단히 StringRedisTemplate를 쓰면 되고
    // 다른 상세한 redis의 작업을 하려면 redisConfig에서 redisTemplate을 빈으로 등록 후 -> valueOperatinos 메서드 등을 쓰면됨

    @Test
    public void stringOpsTest() {
        ///레디스의 문자열 조작을 위한 클래스
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        //지금 redisTemplate에 설정된 타입을 바탕으로 redis 문자열 조작을 할거다 란 의미.
        //레디스 명령어 SET, GET을 메서드 형태로 보관하고 있는 것
        //첫번째 KEY:자바의 자료형, 두번째 VALUE:자바의 자료형
        //그러니까 simplekey가 자바로 올때, simplevalue가 자바로 올때 자료형을 의미함
        
        ops.set("simplekey", "simplevalue");
        System.out.println(ops.get("simplekey"));

        //집합을 조작하기 위한 클래스
        SetOperations<String, String> setOps = stringRedisTemplate.opsForSet();
        setOps.add("hobbies", "games"); //SADD 명령어와 같다.
        setOps.add("hobbies", "coding", "drink", "games");
        System.out.println(setOps.size("hobbies"));

        stringRedisTemplate.expire("hobbies", 10, TimeUnit.SECONDS); //10초뒤 삭제
        stringRedisTemplate.delete("simplekey");
    }



    //RedisTemplate 사용----------------------------------------------------------------------
    @Autowired
    private RedisTemplate<String, ItemDto> itemRedisTemplate;

    @Test
    public void itemRedisTemplateTest() {
        ValueOperations<String, ItemDto> ops = itemRedisTemplate.opsForValue();
        ops.set("my:keyboard", ItemDto.builder()
                .name("Mechanical Keyboard")
                .price(300000)
                .description("Expensive 😢")
                .build());
        System.out.println(ops.get("my:keyboard"));

        ops.set("my:mouse", ItemDto.builder()
                .name("mouse mice")
                .price(100000)
                .description("Expensive 😢")
                .build());
        System.out.println(ops.get("my:mouse"));
    }

    //SetOperations, HashOperations 등등 있다.,
}