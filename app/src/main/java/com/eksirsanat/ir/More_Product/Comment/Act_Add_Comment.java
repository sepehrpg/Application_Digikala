package com.eksirsanat.ir.More_Product.Comment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.eksirsanat.ir.Action.Get_Token;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Config;
import com.eksirsanat.ir.More_Product.Comment.ApiAndDataModel_Vote.DataModel_GetVote;
import com.eksirsanat.ir.More_Product.More_Product;
import com.eksirsanat.ir.Panel_User.Act_LoginActivity;
import com.eksirsanat.ir.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Act_Add_Comment extends AppCompatActivity implements Config {

    LinearLayout line_addVote;
    RecyclerView recyclerView;
    String idProduct;
    String UrlVote="";

    public static List<DataModel_GetVote> listNameVote;

    Adapter_AddComment adapter_addComment;
    TextView txt_add_vote;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__add__comment);

        if (Get_Token.getToken(Act_Add_Comment.this)==null ||  Get_Token.getToken(Act_Add_Comment.this).length()<10){
            Intent intent=new Intent(Act_Add_Comment.this, Act_LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            Cast();
            setUpRecycler();
        }
    }



    public void Cast(){
        SharedPreferences sharedPreferences=getSharedPreferences("seekbar",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        idProduct=getIntent().getStringExtra("idproduct");
        line_addVote=findViewById(R.id.Line_AddVote);
        recyclerView=findViewById(R.id.Rec_AddVote);
        txt_add_vote=findViewById(R.id.Txt_Btn_AddVote);
        listNameVote=new ArrayList<>();
        listNameVote= More_Product.listNameVote;
        UrlVote="";
    }


    public void setUpRecycler(){
        adapter_addComment=new Adapter_AddComment(Act_Add_Comment.this,listNameVote);
        RecyclerView.LayoutManager manager=new GridLayoutManager(Act_Add_Comment.this,1);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter_addComment);


        txt_add_vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String UrlVote="";
                boolean key=true;
                SharedPreferences sharedPreferences=getSharedPreferences("seekbar", Context.MODE_PRIVATE);

                for (int i=0;i<listNameVote.size();i++){
                    String Star=sharedPreferences.getString(listNameVote.get(i).getName(),"0");
                    String nameVote=sharedPreferences.getString(listNameVote.get(i).getName()+"id","null");

                    if (Star.equals("0")){
                        Toast.makeText(Act_Add_Comment.this, "لطفا امتیازات را کامل کنید", Toast.LENGTH_SHORT).show();
                        key=false;
                        break;

                    }
                    else if (nameVote.equals("null")){
                        Toast.makeText(Act_Add_Comment.this, "لطفا امتیازات را کامل کنید", Toast.LENGTH_SHORT).show();
                        key=false;
                        break;

                    }

                    UrlVote+="&vote-"+nameVote+"="+Star;

                }




                if (!UrlVote.equals("") && key){

                  String url=urlHome+"addvote.php?idproduct="+idProduct+"&token="+Get_Token.getToken(Act_Add_Comment.this)+UrlVote;

                    JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            if (response.has("status")){

                                try {
                                    if (response.getString("status").equals("ok")){
                                        //Toast.makeText(Act_Add_Comment.this, "با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(Act_Add_Comment.this,Act_More_AddComment.class);
                                        intent.putExtra("idproduct",idProduct);
                                        startActivity(intent);
                                    }
                                    else if (response.getString("status").equals("error")){
                                        Toast.makeText(Act_Add_Comment.this, response.getString("error"), Toast.LENGTH_SHORT).show();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Act_Add_Comment.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    //Volley.newRequestQueue(context).add(jsonObjectRequest);
                    Request_Volley.getInstance(Act_Add_Comment.this).add(jsonObjectRequest);


                }


                Log.i("UrlVote",UrlVote);


            }
        });

    }




}
