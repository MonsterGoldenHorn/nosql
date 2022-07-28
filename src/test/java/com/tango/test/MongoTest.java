package com.tango.test;

import com.tango.nosql.config.Boot;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;

/**
 * @description
 * @date 2022/7/23 15:30
 */
@SpringBootTest(classes = Boot.class)
public class MongoTest {
    @Resource
    MongoTemplate mongoTemplate;

    @Test
    void test(){
        Document document = mongoTemplate.executeCommand("{age:28},{ name:1}");
        System.out.println(document);
    }

}
