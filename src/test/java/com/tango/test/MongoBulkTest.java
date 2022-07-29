package com.tango.test;

import com.tango.nosql.config.Boot;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;

/**
 * 批量操作
 * @description
 * @date 2022/7/29 17:12
 */
@SpringBootTest(classes = Boot.class)
public class MongoBulkTest {

    @Resource
    MongoTemplate mongoTemplate;

    @Test
    void test(){
        Document document = mongoTemplate.executeCommand("""
                 {"find":"dbz",
                   "filter":{"age":28}
                 }
                """);
        System.out.println(document);
    }

}
