package com.amazonaws.swf.parallel;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClientBuilder;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class GreeterMain {

    private static Logger logger = Logger.getLogger(GreeterMain.class);

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("At least one parameter is required.");
            System.exit(1);
        }

        //PropertyConfigurator.configure("log4j.properties");


        String id = args[0];

        logger.info("Workflow ID: " + id);

        ClientConfiguration config = new ClientConfiguration().withSocketTimeout(70*1000);

        String swfAccessId = System.getenv("AWS_ACCESS_KEY_ID");
        String swfSecretKey = System.getenv("AWS_SECRET_KEY");
        AWSCredentials awsCredentials = new BasicAWSCredentials(swfAccessId, swfSecretKey);
        AWSCredentialsProvider awsCredentialsProvider = new StaticCredentialsProvider(awsCredentials);

        String domain = "swfdemo";
        String region = "eu-west-1";
        String endpoint = "https://swf.eu-west-1.amazonaws.com";
        //AmazonSimpleWorkflow service = new AmazonSimpleWorkflowClient(awsCredentials, config);
        //service.setEndpoint("https://swf.eu-west-1.amazonaws.com");

        AmazonSimpleWorkflow service = AmazonSimpleWorkflowClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withClientConfiguration(config)
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
                .build();


        GreeterWorkflowClientExternalFactory factory = new GreeterWorkflowClientExternalFactoryImpl(service, domain);
        GreeterWorkflowClientExternal greeter = factory.getClient(id);
        greeter.greet();
    }
}
