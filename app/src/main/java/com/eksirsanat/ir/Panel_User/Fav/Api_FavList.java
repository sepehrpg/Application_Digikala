package com.eksirsanat.ir.Panel_User.Fav;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.eksirsanat.ir.Action.Get_Token;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Config;
import com.eksirsanat.ir.More_Product.Api_Product_MoreProduct;
import com.eksirsanat.ir.More_Product.DataModer_Product_MoreProduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Api_FavList implements Config {

    public static void Get_FavList(final Context context, final GetListFav getListFav){

        final List<FavList_Datamodel> products=new ArrayList<>();

        String url=urlHome+"listfav.php?token="+Get_Token.getToken(context);
        Log.i("urlFAAVLIST",url);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray=response.getJSONArray("info-product");

                    for (int i=0; i<jsonArray.length(); i++){



                        JSONObject js=jsonArray.getJSONObject(i);
                        FavList_Datamodel favList_datamodel=new FavList_Datamodel();
                        Log.i("SSFFf",js.getString("name"));
                        Log.i("XXXXX",js+"");

                        favList_datamodel.setName(js.getString("name"));
                        favList_datamodel.setNameEn(js.getString("nameEn"));
                        favList_datamodel.setIdbrand(js.getString("idbrand"));
                        favList_datamodel.setColors(js.getString("colors"));
                        favList_datamodel.setDes(js.getString("des"));
                        favList_datamodel.setSum(js.getString("sum"));
                        favList_datamodel.setIdcat(js.getString("idcat"));
                        favList_datamodel.setPic(js.getString("pic"));
                        favList_datamodel.setId(js.getString("id"));

                        products.add(favList_datamodel);

                    }

                    getListFav.FavList(products);



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
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Volley.newRequestQueue(context).add(jsonObjectRequest);
        Request_Volley.getInstance(context).add(jsonObjectRequest);

    }


    public interface GetListFav{
        void FavList(List<FavList_Datamodel> products);
    }



}
