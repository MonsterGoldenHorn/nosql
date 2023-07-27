package com.tango.nosql.config.service;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @description
 * @date 2022/8/9 9:52
 */
@Service
public class MongoService {
    @Resource
    MongoTemplate mongoTemplate;
    @Transactional(value = "mongoTransactionManager", rollbackFor = Exception.class)
    public void test() throws Exception {
        mongoTemplate.executeCommand("""
                 { "insert":"dd",
                   "documents": [ { "_id": 1, "user": "abc123", "status": "A" } ]
                 }
                """);
        throw new Exception("xx");
    }
}
