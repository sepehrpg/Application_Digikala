package com.eksirsanat.ir.Action;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import com.eksirsanat.ir.Act_Home;
import com.eksirsanat.ir.Panel_User.Panel.Act_Main_Profile_User;

public class Get_Info {

    public static void get_Info(Activity activity){

        SharedPreferences sharedPreferences=activity.getSharedPreferences("info",0);
        String token=sharedPreferences.getString("token",null);
        if (token==null){

        }else {
            Intent intent=new Intent(activity, Act_Main_Profile_User.class); //if user register when click in button reg and login go to profile
            activity.startActivity(intent);
            activity.finish();
        }
    }



    public static void getText(Activity activity){
        SharedPreferences sharedPreferences=activity.getSharedPreferences("info",0);
        String token=sharedPreferences.getString("token",null);

        if (token==null){
            Intent intent=new Intent(activity, Act_Home.class);
            activity.startActivity(intent);
            activity.finish();
        }
    }


}
