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
import com.eksirsanat.ir.More_Product.Images.ModelpriceAndGaranti_Moreproduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Api_Product_MoreProduct implements Config {


    public static void GetPost(final Context context, int idproduct, final InterFace_Product interFaceProduct, final InterFace_Image listimage){

       final List<DataModer_Product_MoreProduct> products=new ArrayList<>();

       final  List<String> ListImages=new ArrayList<>();


       final List<ModelColor_Moreproduct> colorsList=new ArrayList<>();
       final List<ModelpriceAndGaranti_Moreproduct> pricecList=new ArrayList<>();
       final List<ModelPriceOffer_MoreProduct> priceOfferList=new ArrayList<>();


        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, urlHome + "productA.php?idproduct="+idproduct, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray arrayColor=response.getJSONArray("colors");
                    for (int i=0;i<arrayColor.length();i++){
                        JSONObject jsColor=arrayColor.getJSONObject(i);
                        ModelColor_Moreproduct color_moreproduct=new ModelColor_Moreproduct();
                        color_moreproduct.setId(jsColor.getString("id"));
                        color_moreproduct.setName(jsColor.getString("name"));
                        color_moreproduct.setValue(jsColor.getString("value"));
                        colorsList.add(color_moreproduct);
                    }


                  JSONArray arrayPrice=response.getJSONArray("prices");
                    for (int i=0;i<arrayPrice.length();i++){
                        JSONObject jsPrice=arrayPrice.getJSONObject(i);

                        ModelpriceAndGaranti_Moreproduct prices=new ModelpriceAndGaranti_Moreproduct();
                        prices.setId(jsPrice.getString("id"));
                        prices.setIdColor(jsPrice.getString("idcolor"));
                        prices.setGaranti(jsPrice.getString("garanti"));
                        prices.setPrice_sale(jsPrice.getString("price_sale"));
                        pricecList.add(prices);
                    }



                    JSONArray arrayPriceOffer=response.getJSONArray("price_offer");
                    if (arrayPriceOffer.length()>0){
                        for (int i=0;i<arrayPriceOffer.length();i++){
                            JSONObject jsPriceoffer=arrayPriceOffer.getJSONObject(i);
                            ModelPriceOffer_MoreProduct priceOfferModel=new ModelPriceOffer_MoreProduct();

                            priceOfferModel.setId(jsPriceoffer.getString("id"));
                            priceOfferModel.setIdColor(jsPriceoffer.getString("idcolor"));
                            priceOfferModel.setGaranti(jsPriceoffer.getString("garanti"));
                            priceOfferModel.setPrice_sale(jsPriceoffer.getString("price_sale"));
                            priceOfferModel.setPrice_offer(jsPriceoffer.getString("price_offer"));
                            priceOfferList.add(priceOfferModel);

                        }
                    }






                    JSONObject jsonObject=response.getJSONObject("info");
                    DataModer_Product_MoreProduct modelProduct=new DataModer_Product_MoreProduct();

                    if (!jsonObject.isNull("pic")){
                        modelProduct.setPic(jsonObject.getString("pic"));
                    }
                    if (!jsonObject.isNull("des")){
                        modelProduct.setDes(jsonObject.getString("des"));
                    }
                    modelProduct.setIdproduct(jsonObject.getString("id"));
                    modelProduct.setName(jsonObject.getString("name"));
                    modelProduct.setNameEn(jsonObject.getString("nameEn"));
                    modelProduct.setIdbrand(jsonObject.getString("idbrand"));
                    modelProduct.setColors(jsonObject.getString("colors"));
                    modelProduct.setStatus(jsonObject.getString("status"));
                    modelProduct.setSum(jsonObject.getString("sum"));
                    modelProduct.setIdcat(jsonObject.getString("idcat"));
                    modelProduct.setWeight(jsonObject.getString("weight"));

                    products.add(modelProduct);
                    interFaceProduct.list(products,colorsList,pricecList,priceOfferList);


                    JSONArray jsonArray=response.getJSONArray("images");

                    for ( int i=0; i<jsonArray.length();i++){

                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        if (!jsonObject1.isNull("url")){
                            ListImages.add(jsonObject1.getString("url"));
                        }
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
        void list( List<DataModer_Product_MoreProduct> data_modelproduct,List<ModelColor_Moreproduct> colorsList,List<ModelpriceAndGaranti_Moreproduct> pricecList
        ,List<ModelPriceOffer_MoreProduct> priceOfferList);
    }

    public interface InterFace_Image{
        void listImage( List<String> Images);
    }

}
