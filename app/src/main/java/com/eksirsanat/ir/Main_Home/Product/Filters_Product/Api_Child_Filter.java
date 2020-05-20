package com.eksirsanat.ir.Main_Home.Product.Filters_Product;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Api_Child_Filter implements Config {



    public static void GetList_Filters(final Context context, String idCat, final String valueFilter, final Api_Child_Filter.List_Filter listProduct){

        final List<FilterModel_ColorAndBrand> list=new ArrayList<>();

        final String url=urlHome+"list-productA.php?idcat="+idCat+"&"+valueFilter;
        Log.i("URLLLL",url);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    if (valueFilter.equals("رنگ")){
                        JSONObject jsonObject=response.getJSONObject("Child_Value");
                        JSONArray jsonArray=jsonObject.getJSONArray("values");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject js=jsonArray.getJSONObject(i);
                            FilterModel_ColorAndBrand model=new FilterModel_ColorAndBrand();

                            model.setColor(js.getString("name"));
                            model.setValueColor(js.getString("nameEn"));
                            model.setIdColor(js.getString("id"));
                            list.add(model);
                        }
                        listProduct.ListFilter(list,null);
                    }


                    else if (valueFilter.equals("برند")){
                        JSONObject jsonObject=response.getJSONObject("Child_Value");
                        JSONArray jsonArray=jsonObject.getJSONArray("values");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject js=jsonArray.getJSONObject(i);
                            FilterModel_ColorAndBrand model=new FilterModel_ColorAndBrand();

                            model.setName(js.getString("name"));
                            model.setNameEn(js.getString("nameEn"));
                            model.setId(js.getString("id"));
                            list.add(model);
                        }
                        listProduct.ListFilter(list,null);

                    }
                    else if (valueFilter.equals("قیمت")){
                        Log.i("UUUURL",url);
                        JSONObject jsonObject=response.getJSONObject("Child_Value");
                        JSONArray jsonArray=jsonObject.getJSONArray("values");

                        Log.i("SIZLEN",jsonArray.length()+"");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject js=jsonArray.getJSONObject(i);

                            FilterModel_ColorAndBrand model=new FilterModel_ColorAndBrand();

                            if (!js.getString("from").equals("null")){

                                if (!js.getString("to").equals("null")){
                                    Log.i("SSSTOOFROm",js.getString("from")+"  "+js.getString("to"));
                                    model.setFrom_Price(js.getString("from"));
                                    model.setTo_price(js.getString("to"));
                                    list.add(model);
                                }

                            }

                        }
                        listProduct.ListFilter(list,null);


                    }

                    else {
                        JSONArray jsonArray=response.getJSONArray("Val_Propert");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject js=jsonArray.getJSONObject(i);
                            FilterModel_ColorAndBrand model=new FilterModel_ColorAndBrand();
                            model.setProperty(js.getString("pro"));
                            list.add(model);
                        }

                        listProduct.ListFilter(list,null);

                    }






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

        void ListFilter(List<FilterModel_ColorAndBrand> list,List<String> property);
    }

}
