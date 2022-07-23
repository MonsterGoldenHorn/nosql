package com.tango.test;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;

/**
 * @description
 * @date 2022/7/23 15:30
 */
@SpringBootTest
public class MongoTest {
    @Resource
    MongoTemplate mongoTemplate;

    @Test
    void test(){
        mongoTemplate.executeCommand("");
    }

}
