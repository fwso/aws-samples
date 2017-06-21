package com.amazonaws.swf.parallel;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClientBuilder;

import java.util.Date;

public class Helper {
    public static void println(String msg) {
        System.out.println(Long.toString((new Date()).getTime()/1000) + ": " + msg);
    }

    public static AmazonSimpleWorkflow getSWFClient() {

        String swfAccessId = System.getenv("AWS_ACCESS_KEY_ID");
        String swfSecretKey = System.getenv("AWS_SECRET_KEY");

        String region = System.getenv("AWS_SWF_REGION");
        String endpoint = System.getenv("AWS_SWF_ENDPOINT");

        AWSCredentials awsCredentials = new BasicAWSCredentials(swfAccessId, swfSecretKey);
        AWSCredentialsProvider awsCredentialsProvider = new StaticCredentialsProvider(awsCredentials);

        ClientConfiguration config = new ClientConfiguration().withSocketTimeout(70*1000);

        AmazonSimpleWorkflow service = AmazonSimpleWorkflowClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withClientConfiguration(config)
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
                .build();
        return service;
    }

    public static String getDomain() {
        return System.getenv("AWS_SWF_DOMAIN");
    }
}
