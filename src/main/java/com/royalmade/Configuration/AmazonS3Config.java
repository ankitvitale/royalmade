package com.royalmade.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@Configuration
public class AmazonS3Config {

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.US_EAST_1) // This is required but will be ignored by the custom endpoint
                .endpointOverride(URI.create("https://resallingo.sfo3.digitaloceanspaces.com"))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(
                        "DO801HN6N6FKZKQ3L3VU",
                        "qFRmTSCDIQ5rZF23qTs+/8U8ks4TLVgaxSGui18pNmk")))
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
                .build();
    }
}