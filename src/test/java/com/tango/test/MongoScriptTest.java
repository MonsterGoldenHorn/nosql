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
                 {find:"dbz",
                   filter:{"age":28}
                 }
                """);
        System.out.println(document);
    }

    @Test
    void update(){
        Document document = mongoTemplate.executeCommand("""
                 {"update":"dbz",
                   "filter":{"age":28}
                 }
                """);
        System.out.println(document);
    }

    @Test
    void exec(){
        Document document = mongoTemplate.executeCommand("""
                {
                        "aggregate": "orders",
                           "pipeline":[
                            {
                                $lookup:\s
                                {
                                    from: "inventory",
                                    localField: "item",
                                    foreignField: "sku",
                                    as: "inventory_docs"
                                }
                            },
                            {
                                $project:\s
                                {
                                    _id: 0,
                                    item: 1,
                                    price: 1,
                                    'inventory_docs.instock': 1
                                }
                            },
                            {
                                $addFields: {
                                    "inventory_docs.item": "$item",
                                    "inventory_docs.price": "$price",
                                   \s
                                }
                            },
                            {
                                $unwind: "$inventory_docs"
                            },
                            {
                                $replaceRoot: {
                                    newRoot: "$inventory_docs"
                                }
                            },
                            {
                                $match: {
                                    "instock": {
                                        $exists: true
                                    }
                                }
                            }
                        ],
                           cursor: {}
                        }
                """);
        System.out.println(document);
    }
}
