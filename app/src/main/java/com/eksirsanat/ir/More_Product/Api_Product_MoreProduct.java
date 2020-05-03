package com.eksirsanat.ir.More_Product;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Main_Home.Config;
import com.eksirsanat.ir.Main_Home.Product.Api_product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Api_Product_MoreProduct implements Config {


    public static void GetPost(final Context context, int idproduct, final InterFace_Product interFaceProduct, final InterFace_Image listimage){

       final List<DataModer_Product_MoreProduct> products=new ArrayList<>();

       final  List<String> ListImages=new ArrayList<>();



        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, urlHome + "productA.php?idproduct="+idproduct, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject=response.getJSONObject("info");
                    DataModer_Product_MoreProduct modelProduct=new DataModer_Product_MoreProduct();

                    modelProduct.setIdproduct(jsonObject.getString("id"));
                    modelProduct.setName(jsonObject.getString("name"));
                    modelProduct.setNameEn(jsonObject.getString("nameEn"));
                    modelProduct.setIdbrand(jsonObject.getString("idbrand"));
                    modelProduct.setColors(jsonObject.getString("colors"));
                    modelProduct.setStatus(jsonObject.getString("status"));
                    modelProduct.setDes(jsonObject.getString("des"));
                    modelProduct.setSum(jsonObject.getString("sum"));
                    modelProduct.setPic(jsonObject.getString("pic"));
                    modelProduct.setIdcat(jsonObject.getString("idcat"));
                    modelProduct.setWeight(jsonObject.getString("weight"));

                    products.add(modelProduct);
                    interFaceProduct.list(products);


                    JSONArray jsonArray=response.getJSONArray("images");

                    for ( int i=0; i<jsonArray.length();i++){

                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        ListImages.add(jsonObject1.getString("url"));
                    }
                    listimage.listImage(ListImages);



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


    public interface InterFace_Product{
        void list( List<DataModer_Product_MoreProduct> data_modelproduct);
    }

    public interface InterFace_Image{
        void listImage( List<String> Images);
    }

}
