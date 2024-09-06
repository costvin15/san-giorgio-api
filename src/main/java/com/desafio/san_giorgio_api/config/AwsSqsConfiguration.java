package com.desafio.san_giorgio_api.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class AwsSqsConfiguration {
    @Value("${aws.endpoint.url}")
    private String endpoint;

    @Value("${aws.accesskey.id}")
    private String accessKey;

    @Value("${aws.accesskey.secret}")
    private String secretAccessKey;

    @Value("${aws.region}")
    private String region;

    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        var credentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create(this.accessKey, this.secretAccessKey));
        return SqsAsyncClient.builder()
            .credentialsProvider(credentialsProvider)
            .endpointOverride(URI.create(this.endpoint))
            .region(Region.of(region))
            .build();
    }    
}
