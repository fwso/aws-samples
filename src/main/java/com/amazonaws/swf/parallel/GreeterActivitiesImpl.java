package com.amazonaws.swf.parallel;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

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

    public boolean validate() {
        int r = ThreadLocalRandom.current().nextInt(0, 100);
        System.out.println(Long.toString((new Date()).getTime()/1000) + ":Rand : " + r);
        if (r > 50) {
            return true;
        }
        return false;
    }

    public void trueAction(String msg) {
        System.out.println(Long.toString((new Date()).getTime()/1000) + ":Good, > 50, " + msg);
    }

    public void falseFaction(String msg) {
        System.out.println(Long.toString((new Date()).getTime()/1000) + ":OK, <= 50, " + msg);
    }
    
    public void finalize(String msg) {
        System.out.println(Long.toString((new Date()).getTime()/1000) + ", FINAL: " + msg + ",  this should be the last step.");
    }

}
