package com.eksirsanat.ir.Action;

import android.content.Context;
import android.content.SharedPreferences;

public class Get_Token {


    public static String getToken(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("info",0);
        String token=sharedPreferences.getString("token",null);
        if (token==null){
            //return "134t";
            return null;
        }
        return token;
    }
}
