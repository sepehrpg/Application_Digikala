package com.eksirsanat.ir.Action;

import android.content.Intent;

import java.text.DecimalFormat;

public class FormatNumber_Decimal {

    public static String GetFormatInteger(String str){ //this class separate number of price

        String check=null;
        DecimalFormat decimalFormat=new DecimalFormat("###,###");
        check=decimalFormat.format(Integer.valueOf(str));

        return check;

    }
}
