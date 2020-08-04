package com.felixzh.Enum;

public class EnumTest {
    public static void main(String[] args) {
        System.out.println(Status.values().length);
        System.out.println(Status.success);
        System.out.println(Status.fail);
        System.out.println(Status.valueOf("fail"));
        try {
            System.out.println(Status.valueOf("fails"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        for (Status status : Status.values()) {
            System.out.println("==" + status);
            if (status.toString().equals("fail")) {
                System.out.println(true);
            }
        }
    }
}
