package com.eksirsanat.ir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.eksirsanat.ir.Action.Convert_PX_To_Dp;
import com.eksirsanat.ir.Main_Home.Category_Main.Adapter_RecyclerView_Catagory_Home;
import com.eksirsanat.ir.Main_Home.Category_Main.Api_Category_home;
import com.eksirsanat.ir.Main_Home.Category_Main.Datamodel_Category_Home;
import com.eksirsanat.ir.Main_Home.Menu_Navigation.Act_About_Company;
import com.eksirsanat.ir.Main_Home.Pack_Slider.Slider_PageAdapter;
import com.eksirsanat.ir.Main_Home.Pack_Span_Count.Adapter_RecyclerView_SpanCount_Home;
import com.eksirsanat.ir.Main_Home.Pack_Span_Count.Api_SpanCount;
import com.eksirsanat.ir.Main_Home.Pack_Span_Count.Datamodel_SpanCount;
import com.eksirsanat.ir.Main_Home.Product.Api_product;
import com.eksirsanat.ir.Main_Home.Product.Custom_Product;
import com.eksirsanat.ir.Main_Home.Product.Datamodel_ListProduct;
import com.eksirsanat.ir.Main_Home.Product.product_offer.Api_product_Offer;
import com.eksirsanat.ir.Main_Home.Product.product_offer.Custom_Product_Offer;
import com.eksirsanat.ir.Main_Home.pack_timer.Api_Timer;
import com.eksirsanat.ir.Panel_User.Act_LoginActivity;
import com.eksirsanat.ir.ViewPager_Tablayout_Category.Act_ViewPager_Category;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Act_Home extends AppCompatActivity implements Api_Category_home.Get_category {


    public static  Typeface iransans,iransansBold;

    ViewPager viewPager;
    Slider_PageAdapter slider_pageAdapter;
    RecyclerView Rec_Btn_category_home,Rec_SpanCount;
    Api_Category_home api_category_home;

    Adapter_RecyclerView_Catagory_Home adapterRecyclerViewCatagoryHome;

    Api_SpanCount api_spanCount;

    Adapter_RecyclerView_SpanCount_Home adapterCount;


    Custom_Product customProduct,customProductNew;

    Custom_Product_Offer Customproduct_Offer;

    TextView Tv__sec,Tv_min,Tv_hour;


    LinearLayout lineTimer,LinearIndicator;
    boolean showTimer;


    DrawerLayout drwLayout;
    NavigationView Navi;


    int countSlider;
    int idView[];

    Context context;




    ImageView img_search,img_store,img_search_back,menu;
    EditText edt_search;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__home);
        Cast();
        Setup_slider();
        Setup_Category_Home();
        GetList_CustomProduct_Offer();
        getTimer();
        Setup_SpanCount();
        GetList_CustomProduct_Porforosh();

    }
    void Cast(){
        context=Act_Home.this;
        viewPager=findViewById(R.id.viewPager_home);
        Rec_Btn_category_home=findViewById(R.id.Rec_Btn_category_home);
        Rec_SpanCount=findViewById(R.id.Rec_SpanCount);
        Tv__sec=findViewById(R.id.Tv__sec);
        Tv_min=findViewById(R.id.Tv__min);
        Tv_hour=findViewById(R.id.Tv_hour);
        lineTimer=findViewById(R.id.LinearTimer);
        LinearIndicator=findViewById(R.id.LinearIndicator);
        drwLayout=findViewById(R.id.drwLayout);
        menu=findViewById(R.id.menu);
        Navi=findViewById(R.id.Navi_Menu);
        Navigation_Method();
        Header();

        iransans=Typeface.createFromAsset(getAssets(),"font/iranian_sans.ttf");
        iransansBold=Typeface.createFromAsset(getAssets(),"font/iranian_sans_bold.ttf");

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drwLayout.openDrawer(Gravity.RIGHT );
            }
        });
    }


    void Header(){
        img_search=findViewById(R.id.Img_search);
        img_store=findViewById(R.id.Img_store_main);

        edt_search=findViewById(R.id.Edt_search);
        img_search_back=findViewById(R.id.Img_search_back);

    }



    public void Navigation_Method(){

        Navi.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id=item.getItemId();

                item.setChecked(true);


                if (id==R.id.home){
                    startActivity(new Intent(Act_Home.this,Act_Home.class));

                }
                if (id==R.id.list){
                    startActivity(new Intent(Act_Home.this, Act_ViewPager_Category.class));

                }
                if (id==R.id.buy){
                }
                if (id==R.id.product_sale){
                }
                if (id==R.id.product_show){
                }

                if (id==R.id.product_new){
                }
                if (id==R.id.settings){
                }
                if (id==R.id.question){
                }
                if (id==R.id.about){
                    startActivity(new Intent(Act_Home.this,Act_About_Company.class));
                }


                return true; //very import that return true
            }
        });
        Navi.setCheckedItem(R.id.home); //different with btn_navigation


        View view=Navi.getHeaderView(0);
        TextView dg=view.findViewById(R.id.LoginAndRegister);

        dg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_Home.this, Act_LoginActivity.class));

            }
        });






    }





    //slider............................................................................

    void Setup_slider(){
        String url="http://eksirsanat.ir/Digikala/api/home.php";
        final List<String> stringsarray=new ArrayList<>();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("slider");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        stringsarray.add(jsonObject.getString("pic"));
                    }
                    idView=new int[jsonArray.length()];
                    slider_pageAdapter=new Slider_PageAdapter(Act_Home.this,Act_Home.this,stringsarray,idView);
                    viewPager.setAdapter(slider_pageAdapter);
                    Auto_Slider(jsonArray.length());
                    SliderIndicator(jsonArray.length());

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Act_Home.this, "خطا در دریافت اطلاعات از سمت سرور", Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(Act_Home.this).add(jsonObjectRequest);
    }

    void SliderIndicator(final int len){

        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(Convert_PX_To_Dp.convert_px(context,150),Convert_PX_To_Dp.convert_px(context,150)); //with this code we can set some attribute to one variable example View or imageView
        layoutParams.setMargins(0,0,Convert_PX_To_Dp.convert_px(context,35),0);

        for (int i=0; i<len ;i++){

            int id=View.generateViewId(); //i think this code make random or main id for each view //*generate MEEANS:tolid kardan b vojod avardan
            idView[i]=id;

            View view=new View(Act_Home.this);
            view.setBackgroundResource(R.drawable.shape_slider_indicator_noactive);
            view.setLayoutParams(layoutParams);
            view.setId(id);
            LinearIndicator.addView(view);



            //Example ImageView for LayoutParams

            /*ImageView imageView=new ImageView(Act_Home.this);
            //imageView.setImageResource(R.drawable.);
            imageView.setLayoutParams(layoutParams);
            LinearIndicator.addView(imageView);
            */
        }


       viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

           }

           @Override
           public void onPageSelected(int position) {

               countSlider=position;

               for (int i=0 ;i<idView.length; i++) {

                   View view = findViewById(idView[i]); //Casting View

                   if (i == position) {
                       view.setBackgroundResource(R.drawable.shape_slider_indicator_active);
                   } else {
                       view.setBackgroundResource(R.drawable.shape_slider_indicator_noactive);

                   }
               }

           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });


        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                if (position < -1){
                    page.setAlpha(0);
                }
                else if (position <= 0){
                    page.setAlpha(1);
                    page.setPivotX(page.getWidth());
                    page.setRotationY(90*Math.abs(position));
                }
                else if (position <= 1){
                    page.setAlpha(1);
                    page.setPivotX(0);
                    page.setRotationY(-90*Math.abs(position));
                }
                else{
                    page.setAlpha(0);
                }



                if (Math.abs(position) <= 0.5){
                    page.setScaleY(Math.max(.4f,1-Math.abs(position)));
                }
                else if (Math.abs(position) <= 1){
                    page.setScaleY(Math.max(.4f,1-Math.abs(position)));

                }
            }
        });


    }


    void Auto_Slider(final int len){

        final Handler handler=new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {

                //countSlider=0;
                countSlider=viewPager.getCurrentItem();

                while (true){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
                                @Override
                                public void transformPage(@NonNull View page, float position) {
                                    if (position < -1){
                                        page.setAlpha(0);
                                    }
                                    else if (position <= 0){
                                        page.setAlpha(1);
                                        page.setPivotX(page.getWidth());
                                        page.setRotationY(90*Math.abs(position));
                                    }
                                    else if (position <= 1){
                                        page.setAlpha(1);
                                        page.setPivotX(0);
                                        page.setRotationY(-90*Math.abs(position));
                                    }
                                    else{
                                        page.setAlpha(0);
                                    }



                                    if (Math.abs(position) <= 0.5){
                                        page.setScaleY(Math.max(.4f,1-Math.abs(position)));
                                    }
                                    else if (Math.abs(position) <= 1){
                                        page.setScaleY(Math.max(.4f,1-Math.abs(position)));

                                    }
                                }
                            });

                            viewPager.setCurrentItem(countSlider);

                            for (int i=0; i<idView.length; i++){
                                View view=findViewById(idView[i]); //Casting View

                                if (i==countSlider){
                                    view.setBackgroundResource(R.drawable.shape_slider_indicator_active);
                                }
                                else {
                                    view.setBackgroundResource(R.drawable.shape_slider_indicator_noactive);

                                }

                            }

                            countSlider++;

                        }
                    });

                    try {
                        Thread.sleep(3000);
                        if (countSlider==len){
                            countSlider=0;
                        }


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }


            }
        }).start();


    }



    //..............................................................................................






    //list-category............................................................................

    void Setup_Category_Home(){
        Api_Category_home.Category(Act_Home.this,this); //get data of category from Api_Category_home
    }


    @Override
    public void getcategory(List<Datamodel_Category_Home> list) {
        Rec_Btn_category_home.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));//different with linear layout:for this we can define spanCount

        adapterRecyclerViewCatagoryHome=new Adapter_RecyclerView_Catagory_Home(Act_Home.this,list);
        Rec_Btn_category_home.setAdapter(adapterRecyclerViewCatagoryHome);
        Log.i("cat","ok");
    }

    @Override
    public void Error(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    //..............................................................................................






    //list-product-offer-And-timer............................................................................
    void getTimer(){
        //Api_Timer.GetMethod_timer(Act_Home.this,Tv__sec,Tv_min,Tv_hour);

      if (!showTimer){
            lineTimer.setVisibility(View.GONE);
          //Toast.makeText(this, "false", Toast.LENGTH_SHORT).show();
        }
        else {
          //Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();

          lineTimer.setVisibility(View.VISIBLE);
          Api_Timer.GetMethod_timer(Act_Home.this,Tv__sec,Tv_min,Tv_hour);
        }
    }
    void GetList_CustomProduct_Offer(){

        Api_product_Offer.GetLsit_Product("product-offer",Act_Home.this, new Api_product_Offer.ListProduct() {
            @Override
            public void ListPost(List<Datamodel_ListProduct> listProductList) {

                //Toast.makeText(Act_Home.this, listProductList.size()+" ", Toast.LENGTH_SHORT).show();
                if (listProductList.size()<1){
                    showTimer=false;
                }else {
                    showTimer=true;
                }

                Customproduct_Offer=findViewById(R.id.Customproduct_Offer);
                Customproduct_Offer.GetListProduct();
                Customproduct_Offer.getList(listProductList);
                getTimer();

            }
        });
    }
    //..............................................................................................





    //define-spanCount............................................................................

    void Setup_SpanCount(){
        api_spanCount=new Api_SpanCount(this);
        api_spanCount.Setup_SpandCount(new Api_SpanCount.GetSpanCount() {
            @Override
            public void ListSpanCount(List<Datamodel_SpanCount> SpanCount) {

                final GridLayoutManager manager=new GridLayoutManager(Act_Home.this,2);

                manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) { // set size span
                        switch (adapterCount.getItemViewType(position)){ //etItemViewType(position)= 0 or 1 //define in adapter rec in view type
                            case Adapter_RecyclerView_SpanCount_Home.VIEW_TYPE_HEADER: //Means summery:: if be 0:

                                return manager.getSpanCount();

                                case Adapter_RecyclerView_SpanCount_Home.VIEW_TYPE_SPANCOUNT:
                                    return 1;

                                    default:
                                        return -1;
                        }
                    }
                });

                Rec_SpanCount.setLayoutManager(manager);
                adapterCount=new Adapter_RecyclerView_SpanCount_Home(Act_Home.this,SpanCount);
                Rec_SpanCount.setAdapter(adapterCount);


            }
        });

    }
    //..............................................................................................









    //list-product-new-and-sale............................................................................

    void GetList_CustomProduct_Porforosh(){

        Api_product.GetLsit_Product("product-sale",Act_Home.this, new Api_product.ListProduct() {
            @Override
            public void ListPost(List<Datamodel_ListProduct> listProductList) {
                customProduct=findViewById(R.id.custom_Product_porforosh);

                customProduct.ViewAndCast();
                customProduct.getList(listProductList);
                customProduct.setTitle("محصولات پر فروش");

            }
        });
        GetList_CustomProduct_New();
    }

    void GetList_CustomProduct_New(){

        Api_product.GetLsit_Product("product-new",Act_Home.this, new Api_product.ListProduct() {
            @Override
            public void ListPost(List<Datamodel_ListProduct> listProductList) {
                customProductNew=findViewById(R.id.custom_Product_new);

                customProductNew.ViewAndCast();
                customProductNew.getList(listProductList);
                customProductNew.setTitle("جدید ترین محصولات");
            }
        });
    }

    //..............................................................................................


}
