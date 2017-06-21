package com.amazonaws.swf.parallel;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClient;
//import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClientBuilder;
import com.amazonaws.services.simpleworkflow.flow.ActivityWorker;
import com.amazonaws.services.simpleworkflow.flow.WorkflowWorker;
import org.apache.log4j.Logger;
import org.codehaus.plexus.logging.AbstractLogger;

public class GreeterWorkflowWorker {

    protected static Logger logger = Logger.getLogger(GreeterWorkflowWorker.class);

    public static void main(String[] args) throws Exception {

        boolean withErrorHandling = false;

        if (args.length > 0 && args[0].equals("--with-error-handler")) {
            logger.info("Start with error handling");
            withErrorHandling = true;
        }

        AmazonSimpleWorkflow service = Helper.getSWFClient();

        String domain = Helper.getDomain();
        String taskListToPoll = "HelloWorldParallelList";

        WorkflowWorker wfw = new WorkflowWorker(service, domain, taskListToPoll);
        if (withErrorHandling) {
            wfw.addWorkflowImplementationType(GreeterWorkflowImplWithTryCatch.class);
        } else {
            wfw.addWorkflowImplementationType(GreeterWorkflowImpl.class);
        }
        wfw.start();
    }
}
