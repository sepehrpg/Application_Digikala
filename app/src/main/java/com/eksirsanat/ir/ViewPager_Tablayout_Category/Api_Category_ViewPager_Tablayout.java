package com.eksirsanat.ir.ViewPager_Tablayout_Category;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Main_Home.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Api_Category_ViewPager_Tablayout implements Config {


    public static void GetCat(final Context context, final int Index, final GetCatInterface getcat){
        String url=urlHome+"catA.php";
        final List<DataModel_Category_ViewPager_Tab> dataList=new ArrayList<>();

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonList=response.getJSONArray("list-cat");
                    JSONObject js=jsonList.getJSONObject(Index);
                    try {

                        if (js.has("sub-cat")){  //check has this string or no

                            JSONArray jsonArray=js.getJSONArray("sub-cat");
                            if (jsonArray.length()>0){
                                for (int i=0;i<jsonArray.length();i++){
                                    DataModel_Category_ViewPager_Tab model=new DataModel_Category_ViewPager_Tab();

                                    JSONObject jsonObject=jsonArray.getJSONObject(i);

                                    model.setIdcat(jsonObject.getString("id"));
                                    model.setName(jsonObject.getString("name"));
                                    model.setNameEn(jsonObject.getString("nameEn"));
                                    //model.setSubid(jsonObject.getString("sub"));
                                    model.setIcon(jsonObject.getString("icon"));

                                    dataList.add(model);
                                }
                                getcat.ListCat(dataList);
                            }
                        }


                    }catch (Exception e){
                        Toast.makeText(context, "دسته بندی نا موجود", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Volley.newRequestQueue(context).add(jsonObjectRequest);
        Request_Volley.getInstance(context).add(jsonObjectRequest);
    }

    public interface GetCatInterface{

        void ListCat(List<DataModel_Category_ViewPager_Tab> list);

    }
}
