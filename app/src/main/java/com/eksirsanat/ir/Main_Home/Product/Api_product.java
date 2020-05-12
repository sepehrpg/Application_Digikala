package com.eksirsanat.ir.Main_Home.Product;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

public class Api_product implements Config {


    public static void GetLsit_Product(final String arrayname,final Context context, final ListProduct listProduct){

        final List<Datamodel_ListProduct> list=new ArrayList<>();

        String url=urlHome+"home.php";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray=response.getJSONArray(arrayname); //arrayname==name array api
                    for (int i=0; i<jsonArray.length() ;i++){

                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        Datamodel_ListProduct datamodelListProduct=new Datamodel_ListProduct();

                        datamodelListProduct.setIdstore(jsonObject.getString("idstore"));
                        datamodelListProduct.setIdproduct(jsonObject.getString("idproduct"));
                        datamodelListProduct.setPrice_sale(jsonObject.getString("price_sale"));
                        datamodelListProduct.setName(jsonObject.getString("name"));
                        datamodelListProduct.setPic(jsonObject.getString("pic"));
                        datamodelListProduct.setIdcat(jsonObject.getString("idcat"));

                        list.add(datamodelListProduct);

                        //Log.i("list",list.get(i)+"");

                    }

                    listProduct.ListPost(list);

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

    public interface ListProduct{
        void ListPost(List<Datamodel_ListProduct> listProductList);
    }
}
