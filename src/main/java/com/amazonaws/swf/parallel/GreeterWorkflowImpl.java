package com.amazonaws.swf.parallel;

import com.amazonaws.services.simpleworkflow.flow.core.Promise;

public class GreeterWorkflowImpl implements GreeterWorkflow {
    //GreeterActivitiesClient was generated from {@link com.amazonaws.samples.GreeterActivities}.
   private GreeterActivitiesClient operations = new GreeterActivitiesClientImpl();

   public void greet() {
     Promise<String> name = operations.getName();
     Promise<String> greeting = operations.getGreeting();
     operations.say(greeting, name);
   }
}
