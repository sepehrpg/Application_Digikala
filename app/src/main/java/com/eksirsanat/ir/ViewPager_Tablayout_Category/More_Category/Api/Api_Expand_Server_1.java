package com.eksirsanat.ir.ViewPager_Tablayout_Category.More_Category.Api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.ViewPager_Tablayout_Category.More_Category.ItemModel_Server;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Api_Expand_Server_1 {




    public static void GetListCategory_2(final Context context,String idcat, final GetAllList get_list ){


        final List<ItemModel_Server> items=new ArrayList<>();
        //final HashMap<String,List<ItemModel_Server> > ListItems=new HashMap<>();

        String url="http://eksirsanat.ir/Digikala/api/catA.php?subid="+idcat;


        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray cat_1=response.getJSONArray("list-cat");
                    for (int i=0;i<cat_1.length();i++){
                        JSONObject js_1=cat_1.getJSONObject(i);

                        ItemModel_Server model=new ItemModel_Server();
                        model.setName(js_1.getString("name"));
                        model.setId(js_1.getString("id"));
                        model.setNameEn(js_1.getString("nameEn"));
                        model.setSub(js_1.getString("sub"));
                        items.add(model);

                    }
                    get_list.get_List(items);


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

        void get_List(List<ItemModel_Server> items);
    }
}
