package com.eksirsanat.ir.Cart.Fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.eksirsanat.ir.Action.FormatNumber_Decimal;
import com.eksirsanat.ir.Action.Get_Token;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Cart.Act_BasketCart;
import com.eksirsanat.ir.Cart.Api_Adapter_Database.Adapter_BasketCart_First;
import com.eksirsanat.ir.Cart.Api_Adapter_Database.DataModel_DbProduct;
import com.eksirsanat.ir.Cart.Api_Adapter_Database.Db_CartFirst;
import com.eksirsanat.ir.Config;
import com.eksirsanat.ir.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class fg_cart_2 extends Fragment {



    View view;

    Switch aSwitch;
    EditText edt_offer;
    TextView txt_regCodeOffer,txt_address,txt_nameZarinpal,txt_nameMelat,txt_priceFinaly,txt_price_post,txt_priceSumProduct,txt_codeOrder;
    Button txt_ok_peyment;
    ImageView img_zarin,img_melat;


    RecyclerView recyclerView;

    Adapter_BasketCart_First adapter;
    Db_CartFirst database;

    List<DataModel_DbProduct> list;

    ArrayList<String> CountSpiner;

    String address;
    String idAddress;


    String code_Offer="";

    String Price_Post,Price_Total,Price_cart;
    String Code_Order;

    RadioButton radioZarin,radioMelat;

    boolean checkPeyment;

    String nameZarin,iconZarin,urlZarin;

    String nameMelat,iconMelat;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_fg_cart_2, container, false);
        Cast();
        RadioButtonSet();
        setUpRecyclerView();
        setApiAndDataSend();
        send_To_Dargah_Pardakht();
        return view;
    }

    public void Cast(){
        aSwitch=view.findViewById(R.id.SwichCart);
        edt_offer=view.findViewById(R.id.Edt_CodeOffer);
        txt_regCodeOffer=view.findViewById(R.id.Txt_RegCodeOffer);


        txt_address=view.findViewById(R.id.Txt_AddressFinalyCart);
        txt_nameZarinpal=view.findViewById(R.id.Txt_DargahZarinpal);
        txt_nameMelat=view.findViewById(R.id.Txt_DargahMelat);
        txt_priceFinaly=view.findViewById(R.id.Txt_Final_AllPrice);
        txt_price_post=view.findViewById(R.id.Txt_Price_Post);
        img_zarin=view.findViewById(R.id.Img_ZarinPal);
        img_melat=view.findViewById(R.id.Img_Melat);
        txt_ok_peyment=view.findViewById(R.id.Btn_FinishAndPeyment);
        txt_priceSumProduct=view.findViewById(R.id.Txt_Final_PriceSumCart);
        txt_codeOrder=view.findViewById(R.id.Txt_CodeOrder);

        radioZarin=view.findViewById(R.id.Radio_Zarinpal);
        radioMelat=view.findViewById(R.id.Radio_Melat);


        recyclerView=view.findViewById(R.id.Rec_SumBuy);
        database=new Db_CartFirst(getContext());
        list=new ArrayList<>();
        radioZarin.setChecked(true);

        CountSpiner=new ArrayList<>();
        CountSpiner.add("1");
        CountSpiner.add("2");
        CountSpiner.add("3");
        CountSpiner.add("4");
        CountSpiner.add("5");
        CountSpiner.add("6");
        CountSpiner.add("7");
        CountSpiner.add("8");
        CountSpiner.add("9");
        CountSpiner.add("10");  

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setCancelable(true);
        progressDialog.setMessage(" در حال پردازش ");

        SharedPreferences sharedPreferences=getContext().getSharedPreferences("address", Context.MODE_PRIVATE);
        address=sharedPreferences.getString("mainAddress",null);
        idAddress=sharedPreferences.getString("idAddress","null");

        txt_address.setText(" تمامی محصولات به آدرس : "+address+" ارسال میشود ");

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    edt_offer.setVisibility(View.VISIBLE);
                    txt_regCodeOffer.setVisibility(View.VISIBLE);

                }else {
                    edt_offer.setVisibility(View.GONE);
                    txt_regCodeOffer.setVisibility(View.GONE);
                }

            }
        });

    }



    public void setUpRecyclerView() {

        list = database.get_Info_Db();

        adapter = new Adapter_BasketCart_First(getContext(), list, CountSpiner, "0", false, new Adapter_BasketCart_First.ChangeSpiner() {
            @Override
            public void Change(int change) {

            }

            @Override
            public void ListEmpyty(int empty) {

            }
        });

        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(),1,RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }


    public void setApiAndDataSend(){

        final String url=Config.urlHome+"setorder.php?token="+Get_Token.getToken(getContext())+"&idaddress="+idAddress+"&typepost=postpishtaz&code_offer="+code_Offer;

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.has("status") && response.getString("status").equals("ok")){
                        checkPeyment=true;
                        progressDialog.dismiss();
                        Price_Post=response.getString("price_post");
                        Price_Total=response.getString("price_total");
                        Price_cart=response.getString("price_cart");
                        Code_Order=response.getString("code_order");


                        txt_codeOrder.setText("کد پیگیری سفارش : "+Code_Order);
                        txt_priceSumProduct.setText(FormatNumber_Decimal.GetFormatInteger(String.valueOf(Price_cart))+" تومان ");

                        if(Price_Post.equals("0")){
                            txt_price_post.setText(" رایگان");
                        }else {
                            txt_price_post.setText(FormatNumber_Decimal.GetFormatInteger(String.valueOf(Price_Post))+" تومان ");
                        }

                        txt_priceFinaly.setText(FormatNumber_Decimal.GetFormatInteger(String.valueOf(Price_Total))+" تومان ");
                        setPrice_Code_Offer();

                    }
                    else if (response.has("status") && response.getString("status").equals("error")){

                        Toast.makeText(getContext(), response.getString("error"), Toast.LENGTH_SHORT).show();
                        checkPeyment=false;
                    }
                    else {
                        Toast.makeText(getContext(), "مشکل در سبد خرید ", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Volley.newRequestQueue(context).add(jsonObjectRequest);
        Request_Volley.getInstance(getContext()).add(jsonObjectRequest);






        String urlGate=Config.urlHome+"listGate.php";

        JsonObjectRequest jsonObjectRequest2=new JsonObjectRequest(0, urlGate, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray=response.getJSONArray("list-gates");

                        JSONObject js=jsonArray.getJSONObject(0);
                        nameZarin=js.getString("name");
                        iconZarin=js.getString("icon");
                        urlZarin=js.getString("url");

                        txt_nameZarinpal.setText(nameZarin);
                        Glide.with(getContext()).load(iconZarin).into(img_zarin);

                        JSONObject js2=jsonArray.getJSONObject(1);
                        nameMelat=js2.getString("name");
                        iconMelat=js2.getString("icon");

                        txt_nameMelat.setText(nameMelat);
                        Glide.with(getContext()).load(iconMelat).into(img_melat);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequest2.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Volley.newRequestQueue(context).add(jsonObjectRequest);
        Request_Volley.getInstance(getContext()).add(jsonObjectRequest2);







    }


    public void setPrice_Code_Offer(){

        txt_regCodeOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                code_Offer=edt_offer.getText().toString().trim();
                setApiAndDataSend();
                progressDialog.show();

            }
        });

    }

    public void RadioButtonSet(){

        radioZarin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    radioMelat.setChecked(false);
                }

            }
        });

        radioMelat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    radioZarin.setChecked(false);
                }

            }
        });

    }




    public void send_To_Dargah_Pardakht(){

        txt_ok_peyment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radioMelat.isChecked()){
                    Toast.makeText(getContext(), "در گاه پرداخت ملت غیر فعال میباشد ", Toast.LENGTH_SHORT).show();
                }else {

                    if (Code_Order!=null || !Code_Order.equals("null") && !Code_Order.equals("")){
                        String UrlSendZarin=urlZarin+"?codeorder="+Code_Order;



                        try {
                            Intent intent=new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(UrlSendZarin));
                            database.DeleteAllProduct();
                            startActivity(intent);
                            ((Activity)getContext()).finish();
                        }catch (Exception e){
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }



                }

            }
        });

    }


}
