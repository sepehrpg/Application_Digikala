package com.eksirsanat.ir.More_Product.Comment.ApiAndDataModel_Vote;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Config;
import com.eksirsanat.ir.ViewPager_Tablayout_Category.DataModel_Category_ViewPager_Tab;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Api_GetVote implements Config {


   public static final  void GetApiVote(Context context, String idproduct, final GetList_Vote getList_vote){

       final List<DataModel_GetVote> list=new ArrayList<>();

       String url=urlHome+"getvote.php?idproduct="+idproduct;
       final List<DataModel_Category_ViewPager_Tab> dataList=new ArrayList<>();

       JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {

               try {
                   if (response.has("vote")){
                       JSONArray jsonArray=response.getJSONArray("vote");

                       if (jsonArray.length()>0){
                           for (int i = 0; i <jsonArray.length() ; i++) {

                               JSONObject js=jsonArray.getJSONObject(i);
                               DataModel_GetVote model_getVote=new DataModel_GetVote();
                               model_getVote.setIdvote(js.getString("id"));
                               model_getVote.setIdcat(js.getString("idcat"));
                               model_getVote.setName(js.getString("name"));
                               list.add(model_getVote);

                           }

                       }


                       getList_vote.ListCat(list);
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


    public interface GetList_Vote{

        void ListCat(List<DataModel_GetVote> list);

    }


   }




