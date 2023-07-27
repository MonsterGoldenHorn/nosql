package com.tango.nosql.config.controller;

import com.tango.nosql.config.service.MongoService;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description
 * @date 2022/8/9 9:44
 */
@RestController
public class MongoController {

    @Resource
    MongoService mongoService;

    @RequestMapping("test")
    void test()  {
        try {
            mongoService.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
