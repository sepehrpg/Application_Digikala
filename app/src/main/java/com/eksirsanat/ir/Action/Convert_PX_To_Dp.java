package com.eksirsanat.ir.Action;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.bumptech.glide.load.engine.Resource;

public class Convert_PX_To_Dp {

    public static  int convert_px(Context context,float px){
        Resources resource=context.getResources();

        DisplayMetrics metrics=((Resources) resource).getDisplayMetrics();
        float dp= px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int)dp;

    }

}
