package com.eksirsanat.ir.Main_Home.Category_Main;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Main_Home.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Api_Category_home implements Config {  //get data from api category and send to interface until interface send to Act_Home.java


    public static void Category(Context context, final Get_category get_category){

        String url=urlHome+"catA.php";
        final List<Datamodel_Category_Home> datamodel_category_homes=new ArrayList<>(); //is list data model

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("list-cat");
                    for (int i=0; i<jsonArray.length() ;i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        Datamodel_Category_Home datamodelCategoryHome=new Datamodel_Category_Home(); // is data model

                        datamodelCategoryHome.setIdcat(jsonObject.getString("id"));
                        datamodelCategoryHome.setTitle(jsonObject.getString("name"));
                        datamodelCategoryHome.setTitleEn(jsonObject.getString("nameEn"));


                        datamodel_category_homes.add(datamodelCategoryHome);

                    }

                    get_category.getcategory(datamodel_category_homes);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                get_category.Error("خطا در دریافت اطلاعات از سمت سرور");
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Volley.newRequestQueue(context).add(jsonObjectRequest);
        Request_Volley.getInstance(context).add(jsonObjectRequest);
    }

    public interface Get_category{

        void getcategory(List<Datamodel_Category_Home> list);
        void Error(String s);

    }


}
