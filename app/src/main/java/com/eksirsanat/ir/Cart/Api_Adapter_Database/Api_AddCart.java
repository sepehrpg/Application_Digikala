package com.eksirsanat.ir.Cart.Api_Adapter_Database;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.eksirsanat.ir.Action.Get_Token;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Config;

import org.json.JSONException;
import org.json.JSONObject;

public class Api_AddCart implements Config {



    public static void Add_Cart(final Context context, String Count,String idStore, final ResponseMessage responseMessage){

        String url=urlHome+"addcart.php?token="+Get_Token.getToken(context)+"&idstore="+idStore+"&count="+Count;

        StringRequest stringRequest=new StringRequest(1, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("ok")){
                        responseMessage.response("ok");

                    }else {
                        responseMessage.response("خطا در افزودن به سبد خرید");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Volley.newRequestQueue(context).add(jsonObjectRequest);
        Request_Volley.getInstance(context).add(stringRequest);



    }

    public interface ResponseMessage{
        void response(String message);
    }




    public static void DeleteCart(final Context context,String idStore, final ResponseMessage responseMessage){

        String url=urlHome+"deletecart.php?token="+Get_Token.getToken(context)+"&idstore="+idStore;

        StringRequest stringRequest=new StringRequest(1, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if (jsonObject.has("status") && jsonObject.getString("status").equals("ok")){
                        responseMessage.response("ok");

                    }else {
                        responseMessage.response("خطا در حذف محصول");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Volley.newRequestQueue(context).add(jsonObjectRequest);
        Request_Volley.getInstance(context).add(stringRequest);



    }





}
