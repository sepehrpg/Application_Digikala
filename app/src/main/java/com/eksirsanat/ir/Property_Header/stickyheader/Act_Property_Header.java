package com.eksirsanat.ir.Property_Header.stickyheader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Main_Home.Config;
import com.eksirsanat.ir.Property_Header.ChildModel;
import com.eksirsanat.ir.Property_Header.HeaderModel;
import com.eksirsanat.ir.Property_Header.Section;
import com.eksirsanat.ir.Property_Header.SectionAdapter;
import com.eksirsanat.ir.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Act_Property_Header extends AppCompatActivity implements Config {

    ImageView imag_back;

    RecyclerView recyclerView;

    ArrayList<Section> sectionArrayList;
    int HeaderDone=0,ChildDone=0;

    SectionAdapter adapter;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__property__header);

        Cast();
        sectionArrayList=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(Act_Property_Header.this));

        GetData();

        adapter=new SectionAdapter(sectionArrayList);
        recyclerView.setAdapter(adapter);
        StickyHeaderItemDecorator decorator=new StickyHeaderItemDecorator(adapter);
        decorator.attachToRecyclerView(recyclerView);

    }


    public void Cast(){
        imag_back=findViewById(R.id.Image_back_Property);
        recyclerView=findViewById(R.id.ReeView_Property);

        imag_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }



    public void GetData(){
        url=urlHome+"propertyA.php?idproduct=20";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray=response.getJSONArray("property");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        HeaderModel headerModel=new HeaderModel(i);
                        headerModel.setheader(jsonObject.getString("name"));
                        Log.i("one",jsonObject.getString("name"));
                        sectionArrayList.add(headerModel);
                        HeaderDone=HeaderDone+1;

                        JSONArray jsonArray1=jsonObject.getJSONArray("value");
                        for (int a=0; a<jsonArray1.length() ;a++){

                            JSONObject jsonObject1=jsonArray1.getJSONObject(a);
                            Log.i("tow",jsonObject1.getString("name"));
                            ChildModel childModel=new ChildModel(0);
                            childModel.setChild(jsonObject1.getString("name"));
                            childModel.setInfo(jsonObject1.getString("valper"));

                            sectionArrayList.add(childModel);
                            ChildDone=ChildDone+1;

                        }

                    }

                    adapter.notifyDataSetChanged();//very Importent

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Act_Property_Header.this, "خطا در دریافت اطلاعات از سمت سرور", Toast.LENGTH_SHORT).show();
            }
        });


        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Request_Volley.getInstance(Act_Property_Header.this).add(jsonObjectRequest);




    }

}
