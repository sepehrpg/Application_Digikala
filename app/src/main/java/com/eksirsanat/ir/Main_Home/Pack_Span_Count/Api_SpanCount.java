package com.eksirsanat.ir.Main_Home.Pack_Span_Count;

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

public class Api_SpanCount implements Config {
    Context context;
    String url=urlHome+"home.php";

    public Api_SpanCount(Context context){
        this.context=context;
    }

    public void Setup_SpandCount(final GetSpanCount getSpanCount){

        final List<Datamodel_SpanCount> SpanCount=new ArrayList<>();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("slider");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        Datamodel_SpanCount datamodelSpanCount=new Datamodel_SpanCount();

                        datamodelSpanCount.setId(jsonObject.getString("id"));
                        datamodelSpanCount.setPic(jsonObject.getString("pic"));

                        SpanCount.add(datamodelSpanCount);
                    }

                    getSpanCount.ListSpanCount(SpanCount);

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
        ///Volley.newRequestQueue(context).add(jsonObjectRequest);
        Request_Volley.getInstance(context).add(jsonObjectRequest);
    }


    public interface GetSpanCount{
        void ListSpanCount(List<Datamodel_SpanCount> SpanCount);
    }



}
