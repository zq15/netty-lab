package com.example.hello.util;

import java.util.UUID;

public class IDUtil {
    public static String randomId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().split("-")[0]);
    }
}
