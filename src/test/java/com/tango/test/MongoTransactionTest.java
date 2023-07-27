package com.tango.test;

import com.tango.nosql.config.Boot;
import org.bson.Document;
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
//    @Transactional(value = "mongoTransactionManager", rollbackFor = Exception.class)
    void test() throws Exception {
        Document document = mongoTemplate.executeCommand("""
                 { "insert":"dd",
                   "documents": [ { "_id": 1, "user": "abc1234", "status": "A" } ]
                 }
                """);
        System.out.println(document);
//        throw new Exception("xx");
    }

    @Test
//    @Transactional(value = "mongoTransactionManager", rollbackFor = Exception.class)
    void test1() throws Exception {
//        String dd = mongoTemplate.insert(""{ "_id": 2, "user": "abc123", "status": "A" }"", "dd");
//        System.out.println(dd);
//        throw new Exception("xx");
    }

}
