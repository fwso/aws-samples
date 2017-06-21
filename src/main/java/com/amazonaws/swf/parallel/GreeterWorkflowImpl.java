package com.amazonaws.swf.parallel;

import com.amazonaws.services.simpleworkflow.flow.DecisionContextProvider;
import com.amazonaws.services.simpleworkflow.flow.DecisionContextProviderImpl;
import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

public class GreeterWorkflowImpl implements GreeterWorkflow {
    // GreeterActivitiesClient was generated from {@link com.amazonaws.samples.GreeterActivities}.
    private GreeterActivitiesClient operations = new GreeterActivitiesClientImpl();
    private Logger logger = Logger.getLogger(GreeterWorkflowImpl.class);

    private DecisionContextProvider decisionContext = new DecisionContextProviderImpl();

    private String id;


    public void greet(final String id) {
        logger.info("Workflow started: " + id);
        this.id = id;
        Promise<String> name = operations.getName();
        Promise<String> greeting = operations.getGreeting();
        Promise<Void> greet = operations.say(greeting, name);
        Promise<Boolean> isOK = operations.validate(greet);
        Promise<Void> c = condition(isOK);
        operations.finalize(Promise.asPromise("Finalizing"), c);
        daemonTask(id);
    }

    @Asynchronous
    public Promise<Void> condition(Promise<Boolean> isOK) {

        String id = decisionContext.getDecisionContext()
                .getWorkflowContext()
                .getWorkflowExecution()
                .getWorkflowId();
        logger.info(id + " asynchronous method as branch");

        Boolean v = isOK.get();

        if (v.booleanValue() == true) {
            return operations.trueAction("TRUE");
        }
        return operations.falseFaction("FALSE");
    }

    @Asynchronous(daemon = true)
    public void daemonTask(final String id) {
        logger.info(id + " daemonTask running");
        operations.daemonAct("testDaemon");
    }
}
