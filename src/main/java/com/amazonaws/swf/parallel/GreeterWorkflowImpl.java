package com.amazonaws.swf.parallel;

import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import org.apache.log4j.Logger;

import static com.amazonaws.swf.parallel.Helper.println;

public class GreeterWorkflowImpl implements GreeterWorkflow {
    // GreeterActivitiesClient was generated from {@link com.amazonaws.samples.GreeterActivities}.
    private GreeterActivitiesClient operations = new GreeterActivitiesClientImpl();
    private Logger logger = Logger.getLogger(GreeterWorkflowImpl.class);

    public void greet() {
        logger.info("Workflow started");
        Promise<String> name = operations.getName();
        Promise<String> greeting = operations.getGreeting();
        Promise<Void> greet = operations.say(greeting, name);
        Promise<Boolean> lt50 = operations.validate(greet);
        Promise<Void> c = condition(lt50);
        operations.finalize(Promise.asPromise("Finalizing"), c);
        daemonTask();
    }

    @Asynchronous
    public Promise<Void> condition(Promise<Boolean> lt50) {

        Boolean v = lt50.get();

        println(">>>>>>>async method condition(): " + v.toString());

        if (v.booleanValue() == true) {
            return operations.trueAction("TRUE");
        }
        return operations.falseFaction("FALSE");
    }

    @Asynchronous(daemon = true)
    public void daemonTask(Promise<?>... waitFor) {
        logger.info("daemonTask running");
        operations.daemonAct("testDaemon");
    }
}
