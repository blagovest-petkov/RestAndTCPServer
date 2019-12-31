package com.example.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class L {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static void log(String tag, String message) {
        System.out.println(sdf.format(new Date()) + ": " + tag + ": " + message);
    }
}
