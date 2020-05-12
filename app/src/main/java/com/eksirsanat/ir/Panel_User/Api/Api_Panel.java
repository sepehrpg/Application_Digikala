package com.eksirsanat.ir.Panel_User.Api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.eksirsanat.ir.Action.Get_Token;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Config;
import com.eksirsanat.ir.Panel_User.Act_ChangePassword;
import com.eksirsanat.ir.Panel_User.Panel.Act_Main_Profile_User;
import com.eksirsanat.ir.Panel_User.Act_Viryfy_Code;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Api_Panel implements Config {

    Context context;
    ProgressWheel progressWheel;

    public Api_Panel(Context context, ProgressWheel progressWheel) {
        this.context = context;
        this.progressWheel = progressWheel;
    }


    public void Get_RegisterPanel(final String Username, final String pass){
        progressWheel.setVisibility(View.VISIBLE);

        String url=urlHome+"reg.php";
        StringRequest stringRequest=new StringRequest(1, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressWheel.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String check=jsonObject.getString("status");

                    if (check.equals("ok")){

                        Intent intent=new Intent(context, Act_Viryfy_Code.class);
                        intent.putExtra("username",Username);
                        intent.putExtra("reg","reg");
                        context.startActivity(intent);
                        ((Activity)context).finish();


                    }else {
                        Toast.makeText(context, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressWheel.setVisibility(View.GONE);
                Toast.makeText(context, "مشکل در اتصال به اینترنت", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("username",Username);
                hashMap.put("password",pass);

                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Volley.newRequestQueue(context).add(jsonObjectRequest);
        Request_Volley.getInstance(context).add(stringRequest);
    }


    public void Get_ChangePass(final String Pass, final String rePass){
        progressWheel.setVisibility(View.VISIBLE);
        String url=urlHome+"setpasschange.php";


        StringRequest stringRequest=new StringRequest(1, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressWheel.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String check=jsonObject.getString("status");
                    if (check.equals("ok")){

                        Intent intent=new Intent(context, Act_Main_Profile_User.class);
                        context.startActivity(intent);
                        ((Activity)context).finish();

                    }else {
                        progressWheel.setVisibility(View.GONE);
                        Toast.makeText(context, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressWheel.setVisibility(View.GONE);
                Toast.makeText(context, "مشکل در اتصال به اینترنت", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("token", Get_Token.getToken(context).toString());
                hashMap.put("pass",Pass);
                hashMap.put("repass",rePass);

                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Volley.newRequestQueue(context).add(stringRequest);
        Request_Volley.getInstance(context).add(stringRequest);
    }




    public void Get_Login(final String Username, final String password){
        progressWheel.setVisibility(View.VISIBLE);
        String url=urlHome+"login.php";


        StringRequest stringRequest=new StringRequest(1, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressWheel.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String check=jsonObject.getString("status");
                    Log.i("Stepdasd","ok");
                    if (check.equals("ok")){
                        SharedPreferences sharedPreferences=context.getSharedPreferences("info",0);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("token",jsonObject.getString("token"));
                        editor.apply();

                        Intent intent=new Intent(context, Act_Main_Profile_User.class);
                        context.startActivity(intent);
                        ((Activity)context).finish();
                        Log.i("Loginasdasd","ok");



                    }else {
                        progressWheel.setVisibility(View.GONE);
                        Toast.makeText(context, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressWheel.setVisibility(View.GONE);
                Toast.makeText(context, "مشکل در اتصال به اینترنت", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("username",Username);
                hashMap.put("password",password);

                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Volley.newRequestQueue(context).add(stringRequest);
        Request_Volley.getInstance(context).add(stringRequest);
    }




    public void Get_SendCode_Panel(final String Username, final String code,final String reg){
        progressWheel.setVisibility(View.VISIBLE);
        String url;
        if (reg.equals("reg")){
             url=urlHome+"checkCode.php";

        }else {
            url=urlHome+"checkcodeforget.php";
        }


        StringRequest stringRequest=new StringRequest(1, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressWheel.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String check=jsonObject.getString("status");

                    if (check.equals("ok")){
                        SharedPreferences sharedPreferences=context.getSharedPreferences("info",0);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("token",jsonObject.getString("token"));
                        editor.apply();

                            if (reg.equals("reg")){
                                Intent intent=new Intent(context, Act_Main_Profile_User.class);
                                context.startActivity(intent);
                                ((Activity)context).finish();
                            }else{
                                Intent intent=new Intent(context, Act_ChangePassword.class);
                                context.startActivity(intent);
                                ((Activity)context).finish();
                            }


                    }else {
                        progressWheel.setVisibility(View.GONE);
                        Toast.makeText(context, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressWheel.setVisibility(View.GONE);
                Toast.makeText(context, "مشکل در اتصال به اینترنت", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("username",Username);
                hashMap.put("code",code);

                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Volley.newRequestQueue(context).add(stringRequest);
        Request_Volley.getInstance(context).add(stringRequest);
    }





    public void Get_SendCode_Reply(final String Username ){
        progressWheel.setVisibility(View.VISIBLE);
        String url=urlHome+"forgetpass.php";


        StringRequest stringRequest=new StringRequest(1, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressWheel.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String check=jsonObject.getString("status");

                    if (check.equals("ok")){

                        Intent intent=new Intent(context, Act_Viryfy_Code.class);
                        intent.putExtra("username",Username);
                        intent.putExtra("reg","change");
                        context.startActivity(intent);
                        ((Activity)context).finish();

                    }else {
                        progressWheel.setVisibility(View.GONE);
                        Toast.makeText(context, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressWheel.setVisibility(View.GONE);
                Toast.makeText(context, "مشکل در اتصال به اینترنت", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("username",Username);


                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Volley.newRequestQueue(context).add(stringRequest);
        Request_Volley.getInstance(context).add(stringRequest);
    }


}
