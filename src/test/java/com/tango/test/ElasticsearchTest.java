package com.tango.test;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import javax.annotation.Resource;

/**
 * @description
 * @date 2022/7/23 15:41
 */
@SpringBootTest
public class ElasticsearchTest {
    @Resource
    ElasticsearchOperations elasticsearchOperations;
    @Resource
    ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Test
    void test(){}

}
