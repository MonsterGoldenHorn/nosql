package com.tango.test;

import com.tango.nosql.config.Boot;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @description
 * @date 2022/8/8 14:46
 */
@SpringBootTest(classes = Boot.class)
public class MongoTransactionTest {

    @Resource
    MongoTemplate mongoTemplate;

    @Test
    @Transactional(value = "mongoTransactionManager", rollbackFor = Exception.class)
    void test() throws Exception {
        mongoTemplate.executeCommand("""
                 {  "insert":"dd",
                   "documents": [ { "_id": 1, "user": "abc123", "status": "A" } ]
                 }
                """);
//        throw new Exception("xx");
    }

}
