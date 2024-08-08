package com.example.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    //RedisConnectionFactory는 yml에 넣은 정보를 바탕으로 만들어지게 된다.
    public RedisTemplate<String, ItemDto> itemRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, ItemDto> template = new RedisTemplate<>();  // 1
        template.setConnectionFactory(connectionFactory);                 // 2. 어떤 설정을 가지고 레디스와 연결할건지.
        template.setKeySerializer(RedisSerializer.string());              // 3-a. 키를 레디스로 넘겨줄때 어떻게 직렬화/역직-할건지 - 문자열을 사용하여.
        template.setValueSerializer(RedisSerializer.json());              // 3-b. 값은 json으로 직렬화함
        return template;                                                  // 4
    }
}