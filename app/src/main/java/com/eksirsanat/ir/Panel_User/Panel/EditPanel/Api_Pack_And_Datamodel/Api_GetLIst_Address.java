package com.eksirsanat.ir.Panel_User.Panel.EditPanel.Api_Pack_And_Datamodel;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.eksirsanat.ir.Action.Get_Token;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Config;
import com.eksirsanat.ir.Main_Home.Product.Datamodel_ListProduct;
import com.eksirsanat.ir.More_Product.DataModer_Product_MoreProduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Api_GetLIst_Address implements Config {

    public static void getList_Address(final Context context, final Get_List_Address get_list_address){

        String url=urlHome+"listaddress.php?token="+Get_Token.getToken(context);
        final List<DataModel_List_Address> list=new ArrayList<>();

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    if (response.has("address") &&  !response.isNull("address") && !response.getString("address").equals("null")){
                        JSONArray jsonArray=response.getJSONArray("address");
                        if (jsonArray.length()>0){
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject js=jsonArray.getJSONObject(i);

                                DataModel_List_Address datamodelListProduct=new DataModel_List_Address();

                                if (!js.isNull("id")){
                                    datamodelListProduct.setId(js.getString("id"));

                                }
                                if (!js.isNull("name")){
                                    datamodelListProduct.setName(js.getString("name"));

                                }
                                if (!js.isNull("phone")){
                                    datamodelListProduct.setPhone(js.getString("phone"));

                                }
                                if (!js.isNull("mobile")){
                                    datamodelListProduct.setMobile(js.getString("mobile"));

                                }
                                if (!js.isNull("idcity")){
                                    datamodelListProduct.setIdcity(js.getString("idcity"));

                                }

                                if (!js.isNull("codeposti")){
                                    datamodelListProduct.setCodeposti(js.getString("codeposti"));
                                }
                                if (!js.isNull("address")){
                                    datamodelListProduct.setAddress(js.getString("address"));

                                }
                                if (!js.isNull("iduser")){
                                    datamodelListProduct.setIduser(js.getString("iduser"));
                                }

                                list.add(datamodelListProduct);

                            }
                        }
                    }


                    get_list_address.getListAddress(list);


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


    public interface Get_List_Address{
        void getListAddress(List<DataModel_List_Address> list);

    }

}
