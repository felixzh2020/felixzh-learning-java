package com.felixzh.CommonUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author felixzh
 */
public class GetHostPortFromAdderssString {

    // This matches URIs of formats: host:port and protocol:\\host:port
    // IPv6 is supported with [ip] pattern
    private static final Pattern HOST_PORT_PATTERN = Pattern.compile(".*?\\[?([0-9a-zA-Z\\-%._:]*)\\]?:([0-9]+)");

    private static final Pattern VALID_HOST_CHARACTERS = Pattern.compile("([0-9a-zA-Z\\-%._:]*)");

    public static void main(String[] args) {
        System.out.println(getHost("127.0.0.1:8000"));
        System.out.println(getHost("PLAINTEXT://mydomain.com:8080"));
        System.out.println(getHost("PLAINTEXT://MyDomain.com:8080"));
        System.out.println(getHost("PLAINTEXT://My_Domain.com:8080"));
        System.out.println(getHost("[::1]:1234"));
        System.out.println(getHost("PLAINTEXT://[2001:db8:85a3:8d3:1319:8a2e:370:7348]:5678"));
        System.out.println(getHost("PLAINTEXT://[2001:DB8:85A3:8D3:1319:8A2E:370:7348]:5678"));
        System.out.println(getHost("PLAINTEXT://[fe80::b1da:69ca:57f7:63d8%3]:5678"));

        System.out.println(getPort("127.0.0.1:8000").intValue());
        System.out.println(getPort("mydomain.com:8080").intValue());
        System.out.println(getPort("MyDomain.com:8080").intValue());
        System.out.println(getPort("[::1]:1234").intValue());
        System.out.println(getPort("[2001:db8:85a3:8d3:1319:8a2e:370:7348]:5678").intValue());
        System.out.println(getPort("[2001:DB8:85A3:8D3:1319:8A2E:370:7348]:5678").intValue());
        System.out.println(getPort("[fe80::b1da:69ca:57f7:63d8%3]:5678").intValue());

        System.out.println(validHostPattern("127.0.0.1"));
        System.out.println(validHostPattern("mydomain.com"));
        System.out.println(validHostPattern("MyDomain.com"));
        System.out.println(validHostPattern("My_Domain.com"));
        System.out.println(validHostPattern("::1"));
        System.out.println(validHostPattern("2001:db8:85a3:8d3:1319:8a2e:370"));
    }

    /**
     * Extracts the hostname from a "host:port" address string.
     *
     * @param address address string to parse
     * @return hostname or null if the given address is incorrect
     */
    public static String getHost(String address) {
        Matcher matcher = HOST_PORT_PATTERN.matcher(address);
        return matcher.matches() ? matcher.group(1) : null;
    }

    /**
     * Extracts the port number from a "host:port" address string.
     *
     * @param address address string to parse
     * @return port number or null if the given address is incorrect
     */
    public static Integer getPort(String address) {
        Matcher matcher = HOST_PORT_PATTERN.matcher(address);
        return matcher.matches() ? Integer.parseInt(matcher.group(2)) : null;
    }

    /**
     * Basic validation of the supplied address. checks for valid characters
     *
     * @param address hostname string to validate
     * @return true if address contains valid characters
     */
    public static boolean validHostPattern(String address) {
        return VALID_HOST_CHARACTERS.matcher(address).matches();
    }

}
