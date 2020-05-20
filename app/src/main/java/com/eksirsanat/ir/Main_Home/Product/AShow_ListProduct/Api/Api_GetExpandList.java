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
import com.eksirsanat.ir.ViewPager_Tablayout_Category.More_Category.HeadModel_Server;
import com.eksirsanat.ir.ViewPager_Tablayout_Category.More_Category.ItemModel_Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Api_GetExpandList  {


    public static void GetListCategory_2(final Context context, String subid, final GetAllList get_list ){

        final List<String> header=new ArrayList<>();
        //final List<String> listName=new ArrayList<>();
        final HashMap<String,List<String> > items=new HashMap<>();



        final List<HeadModel_Server> Header=new ArrayList<>();

        //final List<ItemModel_Server> Items=new ArrayList<>();

        final HashMap<String,List<ItemModel_Server> > ListItems=new HashMap<>();


        String url="http://eksirsanat.ir/Digikala/api/catA.php?subid="+subid;


        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray cat_1=response.getJSONArray("list-cat");
                    for (int i=0;i<cat_1.length();i++){
                        JSONObject js_1=cat_1.getJSONObject(i);

                        HeadModel_Server headModel_server=new HeadModel_Server();
                        headModel_server.setName(js_1.getString("name"));
                        headModel_server.setId(js_1.getString("id"));
                        headModel_server.setNameEn(js_1.getString("nameEn"));


                        List<ItemModel_Server> Items=new ArrayList<>();
                        ItemModel_Server itemModel_serverFirs=new ItemModel_Server();

                        itemModel_serverFirs.setName(js_1.getString("name"));
                        itemModel_serverFirs.setId(js_1.getString("id"));
                        itemModel_serverFirs.setNameEn(js_1.getString("nameEn"));
                        Items.add(itemModel_serverFirs);

                        if (js_1.has("sub-cat")){

                            JSONArray cat_2=js_1.getJSONArray("sub-cat");
                            for (int a=0;a<cat_2.length();a++){
                                JSONObject js_2=cat_2.getJSONObject(a);
                                //listName.add(js_2.getString("name"));
                                ItemModel_Server itemModel_server=new ItemModel_Server();

                                if (js_2.has("nameEn")){
                                    itemModel_server.setNameEn(js_2.getString("nameEn"));
                                }
                                itemModel_server.setName(js_2.getString("name"));
                                itemModel_server.setId(js_2.getString("id"));
                                Items.add(itemModel_server);
                                Log.i("TEST2",js_2.getString("name")+"");
                            }
                            Header.add(headModel_server);
                            ListItems.put(Header.get(i).getName(),Items);
                        }
                        else {
                            Header.add(headModel_server);
                            ListItems.put(Header.get(i).getName(),Items);

                           /* List<ItemModel_Server> Items2=new ArrayList<>();
                            ItemModel_Server itemModel_server=new ItemModel_Server();
                            itemModel_server.setName("به زودی محصول جدید اضافه میشود");
                            itemModel_server.setNameEn("Coming soon");
                            itemModel_server.setId("-10");
                            Items2.add(itemModel_server);
                            Header.add(headModel_server);
                            ListItems.put(Header.get(i).getName(),Items2);*/
                        }


                    }//




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
