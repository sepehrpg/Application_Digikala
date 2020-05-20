package com.eksirsanat.ir.Main_Home.Product.Filters_Product;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Config;
import com.eksirsanat.ir.Main_Home.Product.Api_product;
import com.eksirsanat.ir.Main_Home.Product.Datamodel_ListProduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Api_Filters implements Config {

    public static void GetList_Filters(final Context context, String idCat, final List_Filter listProduct){

        final List<Filters_DataModel> list=new ArrayList<>();

        String url=urlHome+"list-productA.php?idcat="+idCat+"&limit=100";
        Log.i("URL",url);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray=response.getJSONArray("filters");

                    for (int i=0;i<jsonArray.length();i++){

                        JSONObject js=jsonArray.getJSONObject(i);
                        Filters_DataModel filters=new Filters_DataModel();
                        filters.setName(js.getString("namePe"));
                        filters.setNameEn(js.getString("nameEn"));
                        list.add(filters);


                    }
                    listProduct.ListFilter(list);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "خطا در دریافت اطلاعات از سمت سرور", Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Volley.newRequestQueue(context).add(jsonObjectRequest);
        Request_Volley.getInstance(context).add(jsonObjectRequest);

    }

    public interface List_Filter{

        void ListFilter(List<Filters_DataModel> List_Filter_Product);
    }
}
