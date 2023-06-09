package com.example.demo.global.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3Config {

    @Bean
    fun amazonS3Client(): AmazonS3Client {
        val awsCreds = BasicAWSCredentials("AKIAXVMZ2J7QN3NAIS6O", "rlV6IhlMIYGZwmWWqUoQTNCjZX+c3+LzwHljm/74")
        return AmazonS3ClientBuilder.standard()
            .withRegion("ap-northeast-2")
            .withCredentials(AWSStaticCredentialsProvider(awsCreds))
            .build() as AmazonS3Client
    }
}