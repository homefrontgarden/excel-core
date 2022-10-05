package com.djs;

public class ReplaceTest {
    public static void main(String[] args) {
        String sr = "0.maven配置\n" +
                "1.maven先启动memcached\n" +
                "2.按顺序启动tomcat   18000";
        System.out.println(sr.replaceAll("maven","djs"));
        System.out.println("分割线-----------------------------------------------");
        System.out.println(sr.replace("maven","djs"));
    }
}
