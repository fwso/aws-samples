package com.amazonaws.swf.parallel;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;

@ActivityRegistrationOptions(defaultTaskScheduleToStartTimeoutSeconds = 300,
                             defaultTaskStartToCloseTimeoutSeconds = 10)
@Activities(version="5.0")

public interface GreeterActivities {
    public String getName();

    public String getGreeting();

    public void say(String greeting, String name);
    
    public boolean validate();
    
    public void trueAction(String msg);
    
    public void falseFaction(String msg);
    
    public void finalize(String msg);
}
