package com.eksirsanat.ir.More_Product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.eksirsanat.ir.Cart.Act_BasketCart;
import com.eksirsanat.ir.Cart.Api_Adapter_Database.Api_AddCart;
import com.eksirsanat.ir.Cart.Api_Adapter_Database.Db_CartFirst;
import com.eksirsanat.ir.More_Product.Comment.Act_Add_Comment;
import com.eksirsanat.ir.Action.Convert_PX_To_Dp;
import com.eksirsanat.ir.Action.FormatNumber_Decimal;
import com.eksirsanat.ir.Action.Get_Token;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Config;
import com.eksirsanat.ir.Main_Home.pack_timer.Api_Timer;
import com.eksirsanat.ir.More_Product.Comment.Act_All_CommentUser;
import com.eksirsanat.ir.More_Product.Comment.Adapter_Vote_Product;
import com.eksirsanat.ir.More_Product.Comment.ApiAndDataModel_Vote.Api_GetSumVote;
import com.eksirsanat.ir.More_Product.Comment.ApiAndDataModel_Vote.Api_GetVote;
import com.eksirsanat.ir.More_Product.Comment.ApiAndDataModel_Vote.DataModel_GetVote;
import com.eksirsanat.ir.More_Product.Images.ModelpriceAndGaranti_Moreproduct;
import com.eksirsanat.ir.More_Product.Images.Slider_PageAdapter_Product;
import com.eksirsanat.ir.Panel_User.Act_LoginActivity;
import com.eksirsanat.ir.Property_Header.stickyheader.Act_Property_Header;
import com.eksirsanat.ir.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class More_Product extends AppCompatActivity implements Config {


    LinearLayout TestLine;
    Context context;
    TextView txt_name,txt_nameEn,txt_tile_toolbar,Tv__sec,Tv_min,Tv_hour,Txt_property,txt_sumAnddes,txt_moreshowAndClose,txt_addComment,txt_add_basket;
    TextView txt_price,txt_priceOffer,txt_countColor,txt_garanti,txt_des_allvote;
    Intent GetIntent;
    LinearLayout lineTimer,linear_Indicator,line_comment;
    ImageView img_back,img_shop,img_fav,img_share;
    ViewPager viewPager;
    RatingBar ratingBar;

    int idView[];

    Toolbar toolbar;
    NestedScrollView scrollView;

    String name_title,des,sum,price,priceOffer;

    int check;
    boolean CloseAndSho;


    int ID_PRODUCT,countUser;
    double allStar;

    Adapter_ColorMoreProduct adapter;
    RecyclerView recyclerView,Rec_GetVote;


    Adapter_Vote_Product adapter_vote;


   public static List<Float> listVote;
   public static List<DataModel_GetVote> listNameVote;


    String str_countUser;
    String str_allStar,IdStore;


    Db_CartFirst db_cartFirst;

    String garanti,idcolor,valueColor,images,nameEn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more__product);
        Cast();
        Getproduct();

        OnclickMethod();
        Check_onCreate_Fav();
        Add_Fav();

    }



    void CheckTimer(){
        try {
            //getTimer();
            if (priceOffer!=null){
                lineTimer.setVisibility(View.VISIBLE);
                getTimer();
            }
            else {
                lineTimer.setVisibility(View.GONE);
            }
            /*String check=GetIntent.getStringExtra("offer");
            if (check!=null && check.equals("0")){
                lineTimer.setVisibility(View.GONE);
            }*/
           /*  if (priceOffer!=null){
                lineTimer.setVisibility(View.VISIBLE);
                getTimer();
            }
            else {
                lineTimer.setVisibility(View.GONE);
                //getTimer();
            }*/
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        Setup_Rec_VoteScore();
        //Getproduct();
        //OnclickMethod();
        //Check_onCreate_Fav();
    }

    public void Cast(){
        db_cartFirst=new Db_CartFirst(this);
        SharedPreferences sharedPreferences=getSharedPreferences("SelectColor",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        context=More_Product.this;
        GetIntent=getIntent();
        viewPager=findViewById(R.id.ViewPage_More_Product);
        lineTimer=findViewById(R.id.Linear_Timer_MoreProduct);
        CloseAndSho=false;
        txt_name=findViewById(R.id.Tv_Title_Prodict_fa);
        txt_nameEn=findViewById(R.id.Tv_Title_Prodict_en);
        txt_sumAnddes=findViewById(R.id.Txt_sumAndDes_MoreProduct);
        txt_moreshowAndClose=findViewById(R.id.Txt_MoreShowAndClose);
        recyclerView=findViewById(R.id.Rec_ColorMore);
        Rec_GetVote=findViewById(R.id.Rec_GetVote);
        txt_des_allvote=findViewById(R.id.Txt_DesAllVote);
        ratingBar=findViewById(R.id.starVote);
        line_comment=findViewById(R.id.Linear_Commert_MoreProduct);
        img_share=findViewById(R.id.Img_Share_MoreProduct);
        txt_addComment=findViewById(R.id.Txt_Add_Comment);
        txt_add_basket=findViewById(R.id.Txt_Add_To_Basket);
        txt_des_allvote.setText("هنوز امتیازی ثبت نشده");
        listVote=new ArrayList<>();
        listNameVote=new ArrayList<>();


        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,txt_name.getText()+"\n\n"+txt_nameEn.getText()+"\n\n"+images);
                startActivity(Intent.createChooser(intent,"اشتراک گذاری به وسیله ..."));
            }
        });





        Tv_min=findViewById(R.id.Tv__min);
        Tv_hour=findViewById(R.id.Tv_hour);
        Tv__sec=findViewById(R.id.Tv__sec);
        img_back=findViewById(R.id.Img_back_MoreProduct);
        img_fav=findViewById(R.id.Img_Fav_MoreProduct);

        linear_Indicator=findViewById(R.id.Linear_Indicator_MoreProduct);
        toolbar=findViewById(R.id.Toolbar_MoreProduct);
        scrollView=findViewById(R.id.Scroll_MoreProduct);
        img_shop=findViewById(R.id.Img_shop);
        txt_tile_toolbar=findViewById(R.id.Tv_Toolbar_Title);
        Txt_property=findViewById(R.id.Txt_property);


        txt_price=findViewById(R.id.Txt_PriceMoreProduct);
        txt_priceOffer=findViewById(R.id.Txt_PriceOfferMoreProduct);
        txt_countColor=findViewById(R.id.Txt_CountColor);
        txt_garanti=findViewById(R.id.Txt_Garanti);





        ID_PRODUCT=Integer.parseInt(GetIntent.getStringExtra("idproduct"));

        img_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(More_Product.this,Act_BasketCart.class));
            }
        });

    }




    public  void Setup_Rec_VoteScore(){

        /*int getDpDestenceToWith=200;
        int countCountVote=5;
        int pxWith=getResources().getDisplayMetrics().widthPixels; //get with phone to  pixel
        int  t= Convert_Dp_To_Px.convert_px_to_dp(getDpDestenceToWith,More_Product.this); //change 200 dp too px
        int count=(pxWith-t)/countCountVote;*/


        Api_GetVote.GetApiVote(More_Product.this, String.valueOf(ID_PRODUCT), new Api_GetVote.GetList_Vote() {
            @Override
            public void ListCat(final List<DataModel_GetVote> list) {
                listNameVote=list;
                adapter_vote=new Adapter_Vote_Product(More_Product.this,list,null);
                RecyclerView.LayoutManager manager=new GridLayoutManager(More_Product.this,1);
                Rec_GetVote.setLayoutManager(manager);
                Rec_GetVote.setAdapter(adapter_vote);

                if (list.size()>0){
                    Api_GetSumVote.GetApiVote_Sum(More_Product.this, String.valueOf(ID_PRODUCT), list, new Api_GetSumVote.GetList_Vote_Star() {
                        @Override
                        public void ListVoteStar(List<Float> EachStar) {
                            listVote=EachStar;
                            adapter_vote=new Adapter_Vote_Product(More_Product.this,list,EachStar);
                            Rec_GetVote.setAdapter(adapter_vote);
                            adapter_vote.notifyDataSetChanged();
                        }

                        @Override
                        public void getStarAndCountUser(int CountUser, double AllStar) {
                            try {
                                Log.i("SSSFad",CountUser+"  "+AllStar);
                                countUser=CountUser;
                                allStar=AllStar;
                                if (countUser>0 && AllStar>0){

                                    str_countUser=String.valueOf(countUser);
                                    str_allStar=String.valueOf(allStar);

                                    if (str_allStar.length()>1){
                                        str_allStar=str_allStar.substring(0,3);
                                        txt_des_allvote.setText("امتیازات "+str_allStar+" از 5 با مجموع "+ str_countUser +" رای دهنده ");
                                        float star=Float.parseFloat(str_allStar);
                                        ratingBar.setRating(star);

                                    }else {
                                        float star=Float.parseFloat(str_allStar);
                                        ratingBar.setRating(star);
                                        txt_des_allvote.setText("امتیازات "+str_allStar+" از 5 با مجموع  "+ str_countUser +" رای دهنده ");
                                    }


                                }else {
                                    txt_des_allvote.setText("هنوز امتیازی ثبت نشده");
                                }


                            }catch (Exception e){
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }



            }
        });





    }





    public void Database(){

        //first check that user register

        final ProgressDialog progressDialog=new ProgressDialog(More_Product.this);
        progressDialog.setMessage("در حال پردازش");
        progressDialog.show();
        progressDialog.setCancelable(false);

        if (Get_Token.getToken(More_Product.this)==null || Get_Token.getToken(More_Product.this).length()<10 ){
            startActivity(new Intent(More_Product.this,Act_LoginActivity.class));
            progressDialog.dismiss();
        }else {

            try {
                if (IdStore==null){
                    Toast.makeText(context, "محصول موجود نمی باشد ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (db_cartFirst.idproduct_Exists(String.valueOf(ID_PRODUCT))){

                    long countNumber=db_cartFirst.Number_Count(String.valueOf(ID_PRODUCT));
                    long Count=countNumber+1;
                    db_cartFirst.Update_Number(String.valueOf(ID_PRODUCT));
                    Api_AddCart.Add_Cart(More_Product.this, String.valueOf(Count),IdStore, new Api_AddCart.ResponseMessage() {
                        @Override
                        public void response(String message) {

                            if (message.equals("ok")){
                                Intent intent=new Intent(More_Product.this, Act_BasketCart.class);
                                startActivity(intent);
                                progressDialog.dismiss();

                            }else {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                }
                else {

                    if (IdStore==null){
                        Toast.makeText(context, "محصول موجود نمی باشد ", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Api_AddCart.Add_Cart(More_Product.this, "1",IdStore, new Api_AddCart.ResponseMessage() {
                        @Override
                        public void response(String message) {
                            if (message.equals("ok")){
                                SharedPreferences sharedPreferences=context.getSharedPreferences("SelectColor",Context.MODE_PRIVATE);
                                ContentValues contentValues=new ContentValues();
                                contentValues.put("idproduct",String.valueOf(ID_PRODUCT));
                                contentValues.put("titlefa",name_title);
                                contentValues.put("titleen",nameEn);
                                contentValues.put("price",Integer.parseInt(price));
                                contentValues.put("color",sharedPreferences.getString("nameColor",null));

                                contentValues.put("service","post");
                                contentValues.put("number",1); //first count product is 1


                                if (images != null) {
                                    contentValues.put("image",images);
                                }
                                if (garanti!=null){
                                    contentValues.put("shop",garanti);
                                }

                                if (priceOffer!=null){
                                    contentValues.put("offer_price",Integer.parseInt(priceOffer));
                                    contentValues.put("total_price",Integer.parseInt(priceOffer));
                                    contentValues.put("final_price",Integer.parseInt(priceOffer));

                                }
                                else {
                                    contentValues.put("total_price",Integer.parseInt(price));
                                    contentValues.put("final_price",Integer.parseInt(price));

                                }
                                contentValues.put("idstore",IdStore);



                                db_cartFirst.Insert_Post(ID_PRODUCT,contentValues);
                                Intent intent=new Intent(More_Product.this, Act_BasketCart.class);
                                intent.putExtra("idproduct",ID_PRODUCT);
                                intent.putExtra("idcolor",sharedPreferences.getString("idColor",null));
                                intent.putExtra("valuecolor",sharedPreferences.getString("valueColor",null));
                                intent.putExtra("namecolor",sharedPreferences.getString("nameColor",null));
                                startActivity(intent);
                                progressDialog.dismiss();

                            }else {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                            }

                        }
                    });


                }



            }catch (Exception e){
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }






    }









    void OnclickMethod(){
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                supportFinishAfterTransition();
            }
        });


        line_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameProduct=txt_name.getText().toString().trim();
                Intent intent=new Intent(context, Act_All_CommentUser.class);
                intent.putExtra("nameProduct",nameProduct);
                intent.putExtra("countUser",str_countUser);
                intent.putExtra("allStar",str_allStar);
                String idpro=String.valueOf(ID_PRODUCT);
                intent.putExtra("idproduct",idpro);
                startActivity(intent);
            }
        });


        txt_addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Act_Add_Comment.class);
                String idpro=String.valueOf(ID_PRODUCT);
                intent.putExtra("idproduct",idpro);
                startActivity(intent);
            }
        });


        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY>427){
                    //toolbar.setBackgroundColor(Color.parseColor("#000"));
                    //toolbar.setBackgroundColor(getResources().getColor(R.color.red));
                    toolbar.setBackgroundResource(R.color.ghemezkamrang);
                    img_shop.setImageResource(R.drawable.ic_store_white);
                    img_back.setImageResource(R.drawable.ic_back_right_white);
                }
                else {
                    toolbar.setBackgroundResource(R.color.sefidd);
                    img_shop.setImageResource(R.drawable.ic_store_black_24dp);
                    img_back.setImageResource(R.drawable.ic_back_right_black);
                }


                if (scrollY>380){
                    if (check==0){
                        Animation animation= AnimationUtils.loadAnimation(context,R.anim.anim_toolbar);
                        txt_tile_toolbar.startAnimation(animation);
                        check=1;
                    }
                    txt_tile_toolbar.setText(name_title);

                }else {
                    txt_tile_toolbar.setText("");
                    check=0;
                }
            }
        });

        Txt_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameProduct=txt_name.getText().toString().trim();

                Intent intent=new Intent(context, Act_Property_Header.class);
                intent.putExtra("nameProduct",nameProduct);
                String idpro=String.valueOf(ID_PRODUCT);
                intent.putExtra("idproduct",idpro);
                startActivity(intent);
            }
        });


        txt_moreshowAndClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!CloseAndSho){
                    if (des!=null && !des.equals("null") && !des.equals("") ){
                        txt_sumAnddes.setText(Html.fromHtml(des));
                    }else {
                        txt_sumAnddes.setText(sum);
                    }

                    txt_moreshowAndClose.setText("بستن");
                    CloseAndSho=true;
                }else {
                    txt_sumAnddes.setText(sum);
                    txt_moreshowAndClose.setText("ادامه مطلب");
                    CloseAndSho=false;
                }

            }
        });




        txt_add_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database();
            }
        });




    }


    void  Getproduct(){

        try {
            Api_Product_MoreProduct.GetPost(context, ID_PRODUCT, new Api_Product_MoreProduct.InterFace_Product() {
                @Override
                public void list(List<DataModer_Product_MoreProduct> data_modelproduct, List<ModelColor_Moreproduct> colorsList, List<ModelpriceAndGaranti_Moreproduct> pricecList, List<ModelPriceOffer_MoreProduct> priceOfferList) {

                    try {
                        for (int i=0; i<data_modelproduct.size() ;i++){
                            DataModer_Product_MoreProduct productData=data_modelproduct.get(i);

                            name_title=productData.getName();
                            txt_name.setText(productData.getName());
                            txt_nameEn.setText(productData.getNameEn());
                            nameEn=productData.getNameEn();
                            des=productData.getDes();
                            sum=productData.getSum();
                            txt_sumAnddes.setText(sum);
                            images=productData.getPic();
                        }

                        for (int i=0; i<pricecList.size() ;i++){
                            ModelpriceAndGaranti_Moreproduct pricec=pricecList.get(i);
                            price=pricec.getPrice_sale();
                            txt_garanti.setText(pricec.getGaranti());
                            garanti=pricec.getGaranti();
                            IdStore=pricec.getId();
                        }
                        for (int i=0; i<priceOfferList.size() ;i++){
                            ModelPriceOffer_MoreProduct offer=priceOfferList.get(i);
                            priceOffer=offer.getPrice_offer();
                        }


                        if (priceOffer!=null){
                            txt_price.setTextColor(getResources().getColor(R.color.ghermez));
                            txt_price.setText(FormatNumber_Decimal.GetFormatInteger(price)+" تومان ");
                            txt_price.setPaintFlags(txt_price.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                            txt_priceOffer.setText(FormatNumber_Decimal.GetFormatInteger(priceOffer)+" تومان ");

                        }else {

                            if (price!=null){
                                txt_price.setTextColor(getResources().getColor(R.color.sabzporrang));
                                txt_price.setText(FormatNumber_Decimal.GetFormatInteger(price)+" تومان ");
                            }else {
                                txt_price.setTextColor(getResources().getColor(R.color.ghermez));
                                txt_price.setText(" نا موجود ");
                            }

                        }


                        if (colorsList.size()>0){
                            txt_countColor.setText(String.valueOf(colorsList.size())+" رنگ ");
                            adapter=new Adapter_ColorMoreProduct(More_Product.this,colorsList);
                            RecyclerView.LayoutManager manager=new LinearLayoutManager(More_Product.this,RecyclerView.HORIZONTAL,false);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(adapter);

                            SharedPreferences sharedPreferences=getSharedPreferences("SelectColor",Context.MODE_PRIVATE);
                            idcolor=sharedPreferences.getString("idColor","null");
                            valueColor=sharedPreferences.getString("valueColor","null");
                        }

                        CheckTimer();

                    }catch (Exception ee){
                        Toast.makeText(context, ee.getMessage(), Toast.LENGTH_SHORT).show();
                    }



                }
            }, new Api_Product_MoreProduct.InterFace_Image() {
                @Override
                public void listImage(List<String> Images) {

                    if (!Images.isEmpty()) {
                        Collections.reverse(Images);
                        idView = new int[Images.size()];
                        Slider_PageAdapter_Product adapter = new Slider_PageAdapter_Product(context, Images);
                        viewPager.setAdapter(adapter);
                        SliderIndicator(Images.size());
                    }


                }
            });
            Setup_Rec_VoteScore();
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    void Add_Fav(){
        img_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences=getSharedPreferences("info",0);
                String token=sharedPreferences.getString("token",null);
                if (token==null){
                    startActivity(new Intent(More_Product.this, Act_LoginActivity.class));

                }else {
                    String url=urlHome+"addfav.php?token="+Get_Token.getToken(More_Product.this)+"&idproduct="+ID_PRODUCT;
                    Log.i("urlllllFav",url);
                    JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String check=response.getString("status");
                                if (check.equals("ok")){
                                    img_fav.setImageResource(R.drawable.ic_addfav);
                                    Log.i("addFav","add");
                                }
                                else { //means: before exist

                                    String url=urlHome+"delfav.php?token="+Get_Token.getToken(More_Product.this)+"&idproduct="+ID_PRODUCT;
                                    JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {
                                                String check=response.getString("status");
                                                if (check.equals("ok")){
                                                    img_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                                                    Log.i("delFav","delete");

                                                }
                                                else {
                                                    img_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                                                    Log.i("ErrorFav2","error2");

                                                }


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
                    Request_Volley.getInstance(context).add(jsonObjectRequest);

                }
            }
        });
    }


    void Check_onCreate_Fav(){
        SharedPreferences sharedPreferences=getSharedPreferences("info",0);
        String token=sharedPreferences.getString("token",null);
        if (token==null){

        }
        else {
            String url=urlHome+"productA.php?idproduct="+ID_PRODUCT+"&token="+Get_Token.getToken(More_Product.this);
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        JSONObject jsonObject=response.getJSONObject("forUser");

                        String checkFav=jsonObject.getString("fav");
                        if (checkFav.equals("false")){
                            img_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                        }
                        if (checkFav.equals("true")){
                            img_fav.setImageResource(R.drawable.ic_addfav);
                        }

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
    }








    //list-product-offer-And-timer............................................................................
    void getTimer(){
        try {

            Api_Timer.GetMethod_timer(More_Product.this,Tv__sec,Tv_min,Tv_hour);

        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }




    void SliderIndicator(final int len){

        if (len!=0){
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(Convert_PX_To_Dp.convert_px(context,100),Convert_PX_To_Dp.convert_px(context,100)); //with this code we can set some attribute to one variable example View or imageView
            layoutParams.setMargins(0,0,Convert_PX_To_Dp.convert_px(context,35),0);


            for (int i=0; i<len ;i++){

                int id=View.generateViewId(); //i think this code make random or main id for each view //*generate MEEANS:tolid kardan b vojod avardan
                idView[i]=id;

                View view=new View(context);
                view.setBackgroundResource(R.drawable.shape_slider_indicator_noactive);
                view.setLayoutParams(layoutParams);
                view.setId(id);


                linear_Indicator.addView(view);

            }
        }


        View view=findViewById(idView[0]);
        view.setBackgroundResource(R.drawable.shape_slider_indicator_active);





        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

             for (int i=0; i<idView.length; i++){
               View view=findViewById(idView[i]); //Casting View

              if (i==position){
                view.setBackgroundResource(R.drawable.shape_slider_indicator_active);
              }
              else {
                view.setBackgroundResource(R.drawable.shape_slider_indicator_noactive);

              }

        }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //View view=findViewById(idView[posi]); //Casting View



        /*for (int i=0; i<idView.length; i++){
            View view=findViewById(idView[i]); //Casting View

           int posi=viewPager.getCurrentItem();


            if (i==viewPager.getCurrentItem()){
                view.setBackgroundResource(R.drawable.shape_slider_indicator_active);
            }
            else {
                view.setBackgroundResource(R.drawable.shape_slider_indicator_noactive);

            }

        }*/

        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {

                if (position < 0.5 && position > -0.5) {
                    page.setVisibility(View.VISIBLE);
                } else {
                    page.setVisibility(View.INVISIBLE);
                }



                if (position < -1){     // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    page.setAlpha(0);

                }
                else if (position <= 0) {    // [-1,0]
                    page.setAlpha(1);
                    page.setRotationY(180 *(1-Math.abs(position)+1));

                }
                else if (position <= 1) {    // (0,1]
                    page.setAlpha(1);
                    page.setRotationY(-180 *(1-Math.abs(position)+1));

                }
                else {    // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.setAlpha(0);

                }
            }
        });



    }

}
