package com.tango.test;

import com.tango.nosql.config.Boot;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;

/**
 * @description
 * @date 2022/7/29 9:57
 */
@SpringBootTest(classes = Boot.class)
public class MongoScriptTest {

    @Resource
    MongoTemplate mongoTemplate;

    /**
     * 查询
     * https://www.mongodb.com/docs/manual/reference/command/find/#mongodb-dbcommand-dbcmd.find
     */
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
