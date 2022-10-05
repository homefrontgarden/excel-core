package com.djs;

import com.djs.actuator.ReplaceExecutor;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String path = "D:\\文件";
        String target = "2021年10月8日实施";
        String source = "2023年10月8日实施";
        new ReplaceExecutor(path,target,source).execute();
    }
}
