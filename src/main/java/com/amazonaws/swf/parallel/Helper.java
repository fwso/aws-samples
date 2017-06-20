package com.amazonaws.swf.parallel;

import java.util.Date;

public class Helper {
    public static void println(String msg) {
        System.out.println(Long.toString((new Date()).getTime()/1000) + ": " + msg);
    }
}
