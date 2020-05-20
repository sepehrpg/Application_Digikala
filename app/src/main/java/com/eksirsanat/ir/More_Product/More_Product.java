package com.eksirsanat.ir.More_Product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.eksirsanat.ir.Action.Convert_PX_To_Dp;
import com.eksirsanat.ir.Action.Get_Token;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Config;
import com.eksirsanat.ir.Main_Home.pack_timer.Api_Timer;
import com.eksirsanat.ir.More_Product.Images.Slider_PageAdapter_Product;
import com.eksirsanat.ir.Panel_User.Act_LoginActivity;
import com.eksirsanat.ir.Property_Header.stickyheader.Act_Property_Header;
import com.eksirsanat.ir.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class More_Product extends AppCompatActivity implements Config {



    Context context;

    TextView txt_name,txt_nameEn,txt_tile_toolbar,Tv__sec,Tv_min,Tv_hour,Txt_property;

    Intent GetIntent;

    LinearLayout lineTimer,linear_Indicator;

    ImageView img_back,img_shop,img_fav;

    ViewPager viewPager;

    int idView[];

    Toolbar toolbar;
    NestedScrollView scrollView;

    String name_title;

    int check;


    int ID_PRODUCT;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more__product);
        Cast();
        CheckTimer();
        Getproduct();
        OnclickMethod();
        Check_onCreate_Fav();
        Add_Fav();
    }

    void CheckTimer(){

        String check=GetIntent.getStringExtra("offer");
        if (check!=null && check.equals("0")){
            lineTimer.setVisibility(View.GONE);
        }else {
            lineTimer.setVisibility(View.VISIBLE);
            getTimer();
        }
    }

    public void Cast(){
        context=More_Product.this;
        GetIntent=getIntent();
        viewPager=findViewById(R.id.ViewPage_More_Product);
        lineTimer=findViewById(R.id.Linear_Timer_MoreProduct);

        txt_name=findViewById(R.id.Tv_Title_Prodict_fa);
        txt_nameEn=findViewById(R.id.Tv_Title_Prodict_en);

        Tv__sec=findViewById(R.id.Tv__sec);
        Tv_min=findViewById(R.id.Tv__min);
        Tv_hour=findViewById(R.id.Tv_hour);
        img_back=findViewById(R.id.Img_back_MoreProduct);
        img_fav=findViewById(R.id.Img_Fav_MoreProduct);

        linear_Indicator=findViewById(R.id.Linear_Indicator_MoreProduct);
        toolbar=findViewById(R.id.Toolbar_MoreProduct);
        scrollView=findViewById(R.id.Scroll_MoreProduct);
        img_shop=findViewById(R.id.Img_shop);
        txt_tile_toolbar=findViewById(R.id.Tv_Toolbar_Title);
        Txt_property=findViewById(R.id.Txt_property);

        ID_PRODUCT=Integer.parseInt(GetIntent.getStringExtra("idproduct"));


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




    void OnclickMethod(){
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
                    img_shop.setImageResource(R.drawable.ic_store_black);
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



    }




    //list-product-offer-And-timer............................................................................
    void getTimer(){
        Api_Timer.GetMethod_timer(context,Tv__sec,Tv_min,Tv_hour);
    }


    void  Getproduct(){
        Api_Product_MoreProduct.GetPost(context, ID_PRODUCT, new Api_Product_MoreProduct.InterFace_Product() {
            @Override
            public void list(List<DataModer_Product_MoreProduct> data_modelproduct) {
                for (int i=0; i<data_modelproduct.size() ;i++){
                    DataModer_Product_MoreProduct productData=data_modelproduct.get(i);
                    name_title=productData.getName();
                    txt_name.setText(productData.getName());
                    txt_nameEn.setText(productData.getNameEn());
                }

            }
        }, new Api_Product_MoreProduct.InterFace_Image() {
            @Override
            public void listImage(List<String> Images) {

                if (!Images.isEmpty()){
                    idView=new int[Images.size()];
                    Slider_PageAdapter_Product adapter=new Slider_PageAdapter_Product(context,Images);
                    viewPager.setAdapter(adapter);
                    SliderIndicator(Images.size());
                }


            }
        });
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
