package io.bastioncore.core;

import groovy.transform.CompileStatic;

import java.security.MessageDigest;

@CompileStatic
public class Utils {

    static MessageDigest digest;
    static {
        try{ digest = MessageDigest.getInstance("SHA-256"); }catch(Exception e){}
    }


    public static String byteArrayToHexString(byte[] b){
        StringBuilder result = new StringBuilder();
        for (int i=0; i < b.length; i++)
            result.append(Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 ));
        return result.toString();
    }
    public static String hash(String data){
        return byteArrayToHexString(digest.digest(data.getBytes()));
    }
}
