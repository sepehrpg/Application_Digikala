package com.eksirsanat.ir.Main_Home.Product.AShow_ListProduct.Api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Config;
import com.eksirsanat.ir.Main_Home.Product.AShow_ListProduct.All_ListProduct_Model;
import com.eksirsanat.ir.ViewPager_Tablayout_Category.More_Category.HeadModel_Server;
import com.eksirsanat.ir.ViewPager_Tablayout_Category.More_Category.ItemModel_Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Api_List_Product implements Config {


    public static void GetListCategory_2(final Context context, String idCat,String moreUrl , final GetAllList getAllList){

        String url;

        final List<All_ListProduct_Model> list=new ArrayList<>();

        if (moreUrl!=null && !moreUrl.equals("")){
             url=urlHome+"list-productA.php?idcat="+idCat+moreUrl;
        }else {
             url=urlHome+"list-productA.php?idcat="+idCat;
        }
        Log.i("URLLL",url);

        //String url=urlHome+"list-productA.php?idcat="+idCat;


        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray=response.getJSONArray("products");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject js=jsonArray.getJSONObject(i);
                        All_ListProduct_Model model=new All_ListProduct_Model();

                        model.setId(js.getString("idproduct"));
                        model.setName(js.getString("name"));
                        model.setNameEn(js.getString("nameEn"));

                        if (!js.isNull("img")){
                            model.setImg(js.getString("img"));
                        }
                        model.setIdbrand(js.getString("idbrand"));
                        model.setIdCat(js.getString("idcat"));
                        model.setColors(js.getString("colors"));
                        model.setPrice(js.getString("price"));

                        list.add(model);

                    }
                    getAllList.get_List(list);


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

        //Volley.newRequestQueue(context).add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Request_Volley.getInstance(context).add(jsonObjectRequest);

    }

    public interface GetAllList{

        void get_List(List<All_ListProduct_Model> list);
    }

}
