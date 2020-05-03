package com.eksirsanat.ir.ViewPager_Tablayout_Category.More_Category;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.eksirsanat.ir.Action.Request_Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Api_Expand_Server_1 {




    public static void GetListCategory_2(final Context context, final GetAllList get_list ){

        final List<String> header=new ArrayList<>();
        //final List<String> listName=new ArrayList<>();
        final HashMap<String,List<String> > items=new HashMap<>();



        final List<HeadModel_Server> Header=new ArrayList<>();

        //final List<ItemModel_Server> Items=new ArrayList<>();

        final HashMap<String,List<ItemModel_Server> > ListItems=new HashMap<>();


        String url="http://eksirsanat.ir/Digikala/api/catA.php";


        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray cat_1=response.getJSONArray("list-cat");
                    for (int i=0;i<cat_1.length();i++){
                        JSONObject js_1=cat_1.getJSONObject(i);
                        //header.add(js_1.getString("name"));
                        //List<String> listName=new ArrayList<>();

                        HeadModel_Server headModel_server=new HeadModel_Server();
                        headModel_server.setName(js_1.getString("name"));
                        headModel_server.setId(js_1.getString("id"));
                        headModel_server.setNameEn(js_1.getString("nameEn"));
                        Header.add(headModel_server);

                        List<ItemModel_Server> Items=new ArrayList<>();


                        JSONArray cat_2=js_1.getJSONArray("sub-cat");
                        for (int a=0;a<cat_2.length();a++){
                            JSONObject js_2=cat_2.getJSONObject(a);
                            //listName.add(js_2.getString("name"));
                            ItemModel_Server itemModel_server=new ItemModel_Server();
                            itemModel_server.setName(js_2.getString("name"));
                            itemModel_server.setId(js_2.getString("id"));
                            itemModel_server.setNameEn(js_2.getString("nameEn"));
                            Items.add(itemModel_server);

                        }

                        ListItems.put(Header.get(i).getName(),Items);



                    }//


                  /*  for (int i=0;i<header.size();i++){

                    }
*/

                    get_list.get_List(Header,ListItems);




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

        void get_List(List<HeadModel_Server> Header, HashMap<String, List<ItemModel_Server>> ListItems);
    }
}
