package com.felixzh.DateUtils;

public class TimeStamp {
    public static void main(String[] args) {
        System.out.println(millisToStringShort(123456789));
        System.out.println(millisToStringShort(12345678));
        System.out.println(millisToStringShort(123467));
        System.out.println(millisToStringShort(12346));
        System.out.println(millisToStringShort(123));
    }

    public static String millisToStringShort(long timeStamp) {
        StringBuffer sb = new StringBuffer();
        long millis = 1;
        long seconds = 1000 * millis;
        long minutes = 60 * seconds;
        long hours = 60 * minutes;
        long days = 24 * hours;
        String unit = "";
        if (timeStamp / days >= 1) {
            sb.append((int) (timeStamp / days) + "d ");
            unit = unit.equals("") ? "h" : unit;
        }
        if (timeStamp % days / hours >= 1) {
            sb.append((int) (timeStamp % days / hours) + "h ");
            unit = unit.equals("") ? "m" : unit;
        }
        if (timeStamp % days % hours / minutes >= 1) {
            sb.append((int) (timeStamp % days % hours / minutes) + "m ");
            unit = unit.equals("") ? "s" : unit;
        }
        if (timeStamp % days % hours % minutes / seconds >= 1) {
            sb.append((int) (timeStamp % days % hours % minutes / seconds) + "s ");
        }
        if (timeStamp % days % hours % minutes % seconds / millis >= 1) {
            sb.append((int) (timeStamp % days % hours % minutes % seconds / millis) + "ms");
        }
        return String.format("%s%s", unit.equals("") ? sb.toString() : sb.toString().split(unit)[0], unit);
    }
}
