package com.shaul.saidikaV3.utils;

public class ConvertionUtils {
    public static Integer getInt(String str) {
        int value=0;
        try{
            value=Integer.parseInt(str);
        }catch (Exception es){}
        return value;
    }
}
