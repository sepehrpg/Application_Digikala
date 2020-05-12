package com.eksirsanat.ir.Panel_User.Fav;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.eksirsanat.ir.Act_Home;
import com.eksirsanat.ir.Action.Get_Token;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Config;
import com.eksirsanat.ir.More_Product.More_Product;
import com.eksirsanat.ir.Panel_User.Act_LoginActivity;
import com.eksirsanat.ir.Panel_User.Panel.Act_Main_Profile_User;
import com.eksirsanat.ir.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Act_FavList extends AppCompatActivity implements Config {

    ImageView img_back;
    RecyclerView recyclerView;

    Adapter_FavList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Get_Token.getToken(this)==null){
            Intent intent=new Intent(Act_FavList.this, Act_LoginActivity.class);
            startActivity(intent);
            finish();

        }else {
            setContentView(R.layout.activity_act__fav_list);
            img_back=findViewById(R.id.Close_FavList);
            recyclerView=findViewById(R.id.Rec_FavList);
            Get_ListFav();
        }



    }


    void Get_ListFav(){

        Api_FavList.Get_FavList(Act_FavList.this, new Api_FavList.GetListFav() {
            @Override
            public void FavList(List<FavList_Datamodel> products) {



                Collections.reverse(products);
                adapter=new Adapter_FavList(Act_FavList.this,products,clickListener);
                RecyclerView.LayoutManager manager=new GridLayoutManager(Act_FavList.this,1);

                recyclerView.setLayoutManager(manager);

                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        });

    }

    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final FavList_Datamodel model=(FavList_Datamodel)v.getTag();

            final AlertDialog.Builder alertdialog=new AlertDialog.Builder(Act_FavList.this,R.style.AlertDialogCustom);

            alertdialog.setTitle("حذف از علاقه مندی ها");
            alertdialog.setMessage("محصول از لیست علاقه مندی ها حذف شود ؟");
            alertdialog.setPositiveButton("بله ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String url=urlHome+"delfav.php?token="+Get_Token.getToken(Act_FavList.this)+"&idproduct="+model.getId();
                    JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String check=response.getString("status");
                                Get_ListFav();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Act_FavList.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    //Volley.newRequestQueue(context).add(jsonObjectRequest);
                    Request_Volley.getInstance(Act_FavList.this).add(jsonObjectRequest);

                }
            });

            alertdialog.setNegativeButton("لغو عملیات", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertdialog.show();
        }



    };

}
