package com.eksirsanat.ir.Action;

import android.content.Context;

public class Convert_Dp_To_Px {


    public static  int convert_px_to_dp(float px, Context context){
        float t=context.getResources().getDisplayMetrics().density;
        return (int)t;

    }
}
