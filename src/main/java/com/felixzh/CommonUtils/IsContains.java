package com.felixzh.CommonUtils;

import org.apache.commons.lang.ArrayUtils;

public class IsContains {
    public static void main(String[] args) {
        String superUsers = "User:kafka;User:admin";
        System.out.println(ArrayUtils.contains(superUsers.split(";"), "User:admin"));
    }
}
