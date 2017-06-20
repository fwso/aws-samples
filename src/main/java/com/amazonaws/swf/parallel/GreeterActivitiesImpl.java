package com.amazonaws.swf.parallel;

import org.apache.log4j.Logger;

import java.util.concurrent.ThreadLocalRandom;

import static com.amazonaws.swf.parallel.Helper.println;

public class GreeterActivitiesImpl implements GreeterActivities {

    private Logger logger = Logger.getLogger(GreeterActivitiesImpl.class);

    public String getName() {
        logger.info("Started: getName() activity");
        println(":Start: getName()");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        println(":Done : getName()");
        return "World!";
    }

    public String getGreeting() {
        println("Start: getGreeting()");
        println(":Done : getGreeting()");
        return "Hello ";
    }

    public void say(String greeting, String name) {
        println(greeting + name);
    }

    public boolean validate() {
        int r = ThreadLocalRandom.current().nextInt(0, 100);
        println("Rand : " + r);
        if (r > 50) {
            return true;
        }
        return false;
    }

    public void trueAction(String msg) {
        println("Good, > 50, " + msg);
    }

    public void falseFaction(String msg) {
        println("OK, <= 50, " + msg);
    }

    public void daemonAct(String param) {
        logger.info("daemonAct() activity running with parameters: " + param);
    }

    public void finalize(String msg) {
        println("finalize: " + msg + ",  this should be the last step.");
    }
}
