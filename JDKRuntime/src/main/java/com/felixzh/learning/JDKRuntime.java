package com.felixzh.learning;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author FelixZh
 */
public class JDKRuntime {
    public static void main(String[] args) {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        System.out.println(availableProcessors + "," + freeMemory + "," + totalMemory + "," + maxMemory);

        String cmd = "ipconfig";
        runtimeExec(cmd);
    }

    public static void runtimeExec(String cmd) {
        runtimeExec(cmd, null);
    }

    public static void runtimeExec(String cmd, String[] env) {
        Process process;
        BufferedReader brInfo = null;
        BufferedReader brError = null;
        String line;
        try {
            process = Runtime.getRuntime().exec(cmd, env);
            brInfo = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            while ((line = brInfo.readLine()) != null) {
                System.out.println(line);
            }

            brError = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8));
            while ((line = brError.readLine()) != null) {
                System.out.println(line);
            }

            int code = process.waitFor();
            if (code == 0) {
                System.out.println("Success");
            } else {
                System.out.println("Failed");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (brInfo != null) {
                try {
                    brInfo.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (brError != null) {
                try {
                    brError.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
