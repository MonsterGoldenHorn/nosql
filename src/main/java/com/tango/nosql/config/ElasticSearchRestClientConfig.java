package com.tango.nosql.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * @author tango
 */
public class ElasticSearchRestClientConfig extends AbstractElasticsearchConfiguration {

    @Value("${elasticsearch.uri}")
    private String esIp;

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(esIp)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
 
}