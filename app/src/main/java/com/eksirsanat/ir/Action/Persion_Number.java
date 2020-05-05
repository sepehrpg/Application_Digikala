package com.eksirsanat.ir.Action;

import android.content.Intent;

public class Persion_Number {

    static String[] Persion_Number=new String[]{"","","","","","","","","",""}; //enter persion number

    static String farsuNumber(String text){

        if (text.length()==0){ //if null return "";
            return "";
        }

        String out="";
        int length=text.length();

        for (int i=0; i<length ;i++){
            char c=text.charAt(i); //get char

            if ('0' <=c && c<='9' ){ //if betwen 0 and 9

                int number=Integer.parseInt(String.valueOf(c)); //get number and change to integer

                out=out+Persion_Number[number]; //change english to persion
            }

            else if (c==',' || c=='و'){ //when number separate before

                out=out+','; // we separate too
            }
            else {
                out=out+c; // if not number 0..9
            }

        }
        return out;
    }


}
