package com.eksirsanat.ir.Action.CallBack_Page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.eksirsanat.ir.Action.Get_Token;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Config;
import com.eksirsanat.ir.Panel_User.Act_LoginActivity;
import com.eksirsanat.ir.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Act_CallBack_Server extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ContentValues> list;
    Adapter_ActCallBack adapter;
    ImageView back;

    TextView text_savabegh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__call_back__server);

        if (Get_Token.getToken(this)==null || Get_Token.getToken(this).length()<10){
            startActivity(new Intent(this, Act_LoginActivity.class));
            finish();
        }else {
            recyclerView=findViewById(R.id.Rec_UserOreder);
            back=findViewById(R.id.Close_Main_Toolbar);
            text_savabegh=findViewById(R.id.text_savabegh);
            list=new ArrayList<>();

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            Api_CallBack();
        }


    }

    public void Api_CallBack(){

        String urlGate= Config.urlHome+"gate/user_order.php?token="+ Get_Token.getToken(Act_CallBack_Server.this);
        Log.i("urlFF",urlGate);

        JsonObjectRequest jsonObjectRequest2=new JsonObjectRequest(0, urlGate, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.has("order-finish") && !response.isNull("order-finish") && !response.getString("order-finish").equals("null")){
                        JSONArray jsonArray=response.getJSONArray("order-finish");
                        if (jsonArray.length()>0){
                            for (int i = 0; i <jsonArray.length() ; i++) {
                                JSONObject js=jsonArray.getJSONObject(i);
                                ContentValues contentValues=new ContentValues();
                                contentValues.put("code",js.getString("code"));
                                contentValues.put("price_total",js.getString("price_total"));
                                contentValues.put("datepay",js.getString("datepay"));
                                contentValues.put("status",js.getString("status"));
                                Log.i("statussss",js.getString("status"));
                                list.add(contentValues);
                            }
                        }

                    }


                    setUpRecycler(list);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Act_CallBack_Server.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequest2.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Volley.newRequestQueue(context).add(jsonObjectRequest);
        Request_Volley.getInstance(Act_CallBack_Server.this).add(jsonObjectRequest2);

    }


    public void setUpRecycler(ArrayList<ContentValues> listset){
        try {
            if (listset.size()<1 || listset.isEmpty()){
                text_savabegh.setText("تا کنون خریدی نداشتید");

            }else {
                adapter=new Adapter_ActCallBack(Act_CallBack_Server.this,listset);
                RecyclerView.LayoutManager manager=new GridLayoutManager(Act_CallBack_Server.this,1);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
                text_savabegh.setText("سوابق خرید و وضعیت سفارشات شما");
            }

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
