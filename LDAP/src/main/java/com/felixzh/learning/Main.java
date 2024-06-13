package com.felixzh.learning;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 * @author FelixZh
 */
public class Main {
    public static void main(String[] args) {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://felixzh2:389");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");

        // 管理员
        //env.put(Context.SECURITY_PRINCIPAL, "cn=Manager,dc=felixzh,dc=com");
        //env.put(Context.SECURITY_CREDENTIALS, "felixzh");

        // 普通用户
        env.put(Context.SECURITY_PRINCIPAL, "uid=felixzhUser,ou=People,dc=felixzh,dc=com");
        env.put(Context.SECURITY_CREDENTIALS, "admin@123");

        DirContext ctx = null;
        try {
            ctx = new InitialDirContext(env);
            System.out.println("认证成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("认证失败");
        } finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                    //ignore
                }
            }
        }
    }
}