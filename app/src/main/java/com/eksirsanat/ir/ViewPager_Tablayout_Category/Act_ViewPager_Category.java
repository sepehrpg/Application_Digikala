package com.eksirsanat.ir.ViewPager_Tablayout_Category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Main_Home.Config;
import com.eksirsanat.ir.R;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Act_ViewPager_Category extends AppCompatActivity implements Config {

    ImageView img_back;
    TabLayout tabLayout;
    Context context;
    ViewPager viewPager;

    AdapterViewpager_TabCategory adapterViewpager_tabCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__view_pager__category);
        Cast();
        SetUpViewPager();
    }

    void SetUpViewPager(){

        String url=urlHome+"catA.php";
        final List<String> listTitle=new ArrayList<>();

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() { //for get cat 0 title and id
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray=response.getJSONArray("list-cat");

                    for (int i=0; i<jsonArray.length() ;i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        listTitle.add(jsonObject.getString("name")); //use final because this is one interface

                    }
                    adapterViewpager_tabCategory=new AdapterViewpager_TabCategory(context,listTitle);
                    viewPager.setRotationY(180); //for rtl viewpager

                    viewPager.setAdapter(adapterViewpager_tabCategory);
                    tabLayout.setupWithViewPager(viewPager);


                    //set Animation
                      final float MIN_SCALE = 0.65f;
                      final float MIN_ALPHA = 0.3f;
                    viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
                        @Override
                        public void transformPage(@NonNull View page, float position) {

                            if (position <-1){  // [-Infinity,-1)
                                // This page is way off-screen to the left.
                                page.setAlpha(0);

                            }
                            else if (position <=1){ // [-1,1]

                                page.setScaleX(Math.max(MIN_SCALE,1-Math.abs(position)));
                                page.setScaleY(Math.max(MIN_SCALE,1-Math.abs(position)));
                                page.setAlpha(Math.max(MIN_ALPHA,1-Math.abs(position)));

                            }
                            else {  // (1,+Infinity]
                                // This page is way off-screen to the right.
                                page.setAlpha(0);

                            }
                        }
                    });




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


    void Cast(){
        context=Act_ViewPager_Category.this;
        img_back=findViewById(R.id.Img_back_Category);


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tabLayout=findViewById(R.id.tablayout_category);

        viewPager=findViewById(R.id.viewpager_category);

    }
}
