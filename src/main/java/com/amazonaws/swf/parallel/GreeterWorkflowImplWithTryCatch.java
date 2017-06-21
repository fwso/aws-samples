package com.amazonaws.swf.parallel;

import com.amazonaws.services.simpleworkflow.flow.DecisionContextProvider;
import com.amazonaws.services.simpleworkflow.flow.DecisionContextProviderImpl;
import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.amazonaws.services.simpleworkflow.flow.core.Settable;
import com.amazonaws.services.simpleworkflow.flow.core.TryCatchFinally;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

public class GreeterWorkflowImplWithTryCatch implements GreeterWorkflow {

    private GreeterActivitiesClient operations = new GreeterActivitiesClientImpl();
    private Logger logger = Logger.getLogger(GreeterWorkflowImpl.class);

    private DecisionContextProvider decisionContext = new DecisionContextProviderImpl();

    private String id;
    public void greet(final String id) {
        logger.info("Workflow started(doTry): " + id);
        this.id = id;
        final Settable<Throwable> failure = new Settable<>();
        new TryCatchFinally() {

            @Override
            protected void doTry() throws Throwable {
                Promise<String> name = operations.getName();
                Promise<String> greeting = operations.getGreeting();
                Promise<Void> greet = operations.say(greeting, name);
                Promise<Boolean> isOK = operations.validate(greet);
                Promise<Void> c = condition(isOK);
                daemonTask(id);
            }

            @Override
            protected void doCatch(Throwable throwable) throws Throwable {
                logger.error(id + " FAILED: " + throwable.getMessage());
                failure.set(throwable);
            }

            @Override
            protected void doFinally() throws Throwable {
                logger.info(id + " doFinally()");
                if (!failure.isReady()) {
                    failure.set(null);
                }
                operations.finalize(Promise.asPromise("Finalizing"));
            }
        };

        workflowFailed(failure);
    }

    @Asynchronous
    public void workflowFailed(Promise<Throwable> failure) {
        Throwable throwable = failure.get();

        if (throwable != null) {
            try {
                logger.error(id + " workflow failed with following exception:");
                ObjectMapper mapper = new ObjectMapper();
                String exceptionInString = mapper.writeValueAsString(throwable.getStackTrace());
                logger.error(exceptionInString);
            } catch (JsonProcessingException e) {
                logger.error(id + " JSON Exception: " + e.getMessage());
            }
        } else {
            logger.info(id + " SUCCEED with no exception");
        }
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
