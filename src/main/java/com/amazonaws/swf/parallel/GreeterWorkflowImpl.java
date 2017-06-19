package com.amazonaws.swf.parallel;

import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;

public class GreeterWorkflowImpl implements GreeterWorkflow {
    // GreeterActivitiesClient was generated from {@link
    // com.amazonaws.samples.GreeterActivities}.
    private GreeterActivitiesClient operations = new GreeterActivitiesClientImpl();

    public void greet() {
        Promise<String> name = operations.getName();
        Promise<String> greeting = operations.getGreeting();
        Promise<Void> greet = operations.say(greeting, name);
        Promise<Boolean> lt50 = operations.validate(greet);
        Promise<Boolean> c = condition(lt50);
        operations.finalize(Promise.asPromise("Finalizing"), c);
    }

    @Asynchronous
    public Promise<Boolean> condition(Promise<Boolean> lt50) {
        
        try {
            Boolean v = lt50.get();
            
            System.out.println(">>>>>>>>>>>>>>>" + v.toString());
        } catch (Exception e) {
            
        }
        
        return lt50;
    }
}
