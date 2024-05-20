package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;



@Configuration
public class StorageConfig1 {
	
	

	    @Bean
	    public AmazonS3 amazonS3() {
	        BasicAWSCredentials awsCreds = new BasicAWSCredentials("zBAM362R2tqtgy6leKN5qm7b02HeWG", "XvcJODF4BdFG3jGOeyVNugLuJUPdu6");

	        return AmazonS3ClientBuilder.standard()
	                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
	                        "https://s3proxy.decentrally.cloud", "us-east-1"))
	                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
	                .withPathStyleAccessEnabled(true)  
	                .build();
	    }
	}

	


