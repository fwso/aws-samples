package com.amazonaws.swf.parallel;

import java.util.Date;

public class GreeterActivitiesImpl implements GreeterActivities {

    public String getName() {
        System.out.println(Long.toString((new Date()).getTime()/1000) + ":Start: getName()");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Long.toString((new Date()).getTime()/1000) + ":Done : getName()");
        return "World!";
    }

    public String getGreeting() {
        System.out.println(Long.toString((new Date()).getTime()/1000) + ":Start: getGreeting()");
        System.out.println(Long.toString((new Date()).getTime()/1000) + ":Done : getGreeting()");
        return "Hello ";
    }

    public void say(String greeting, String name) {
        String ts = Long.toString((new Date()).getTime()/1000);
        System.out.println(ts + ":" + greeting + name);
    }

}
