package com.amazonaws.swf.parallel;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;

@ActivityRegistrationOptions(defaultTaskScheduleToStartTimeoutSeconds = 300,
                             defaultTaskStartToCloseTimeoutSeconds = 10)
@Activities(version="5.0")

public interface GreeterActivities {
    String getName();

    String getGreeting();

    void say(String greeting, String name);
    
    boolean validate();
    
    void trueAction(String msg);
    
    void falseFaction(String msg);

    void daemonAct(String param);

    void finalize(String msg);
}
