package com.company.tsp_solver.utils;

import java.util.Date;
import java.util.Random;

public class Utils {
    public final static String resourcePath = "src/main/resources";
    public final static  String packagePath = "com/company/tsp_solver";
    public final static Random random = new Random(new Date().getTime());
    public static String buildPathToResource(String file) {
        return resourcePath + packagePath + file;
    }
}
