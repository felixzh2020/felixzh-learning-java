package com.felixzh.learning;

/**
 * @author FelixZh
 */
public class JDKRuntimeForPgDump {
    public static void main(String[] args) {
        String host = "felixzh";
        String port = "5432";
        String username = "postgres";
        String password = "postgres";
        String database = "testdb";
        String path = "/opt/dump.sql";

        String cmd = String.format("pg_dump -h %s -p %s -U %s -f %s -w %s", host, port, username, path, database);
        String[] env = {"PGPASSWORD=" + password};
        JDKRuntime.runtimeExec(cmd, env);
    }
}
