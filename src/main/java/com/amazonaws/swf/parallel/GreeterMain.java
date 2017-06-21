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
        String domain = "swfdemo";

        logger.info("Workflow ID: " + id);

        AmazonSimpleWorkflow service = Helper.getSWFClient();

        GreeterWorkflowClientExternalFactory factory = new GreeterWorkflowClientExternalFactoryImpl(service, domain);
        GreeterWorkflowClientExternal greeter = factory.getClient(id);
        greeter.greet(id);
    }
}
