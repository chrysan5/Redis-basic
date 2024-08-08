package com.example.redis;

// package, import 생략

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("item") //@Entity 대신 적어줌. "item"은 redis 내부에서 key를 구분하는 용도로 사용된다.
public class Item implements Serializable {
    //id를 String으로 하면 uuid가 자동으로 배정된다. 레디스는 임시로 쓰기때문임.
    //그래서 대부분은 String으로 해서 uuid로 진행하지만 long으로 해도 문제되진 않음
    @Id
    private Long id;
    private String name;
    private String description;
    private Integer price;
}

//원래 레디스는 이렇게 안쓰는데 엔티티랑 비교하기 위해 엔티티와 비슷한 형식으로 만들었다.