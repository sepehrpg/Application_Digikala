package com.eksirsanat.ir.Panel_User.Panel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.eksirsanat.ir.Action.Get_Info;
import com.eksirsanat.ir.Action.Get_Token;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Config;
import com.eksirsanat.ir.Panel_User.Act_ChangePassword;
import com.eksirsanat.ir.Panel_User.Act_Viryfy_Code;
import com.eksirsanat.ir.Panel_User.Fav.Act_FavList;
import com.eksirsanat.ir.Panel_User.Panel.EditPanel.Act_Add_Address;
import com.eksirsanat.ir.Panel_User.Panel.EditPanel.Act_Edit_User;
import com.eksirsanat.ir.Panel_User.Panel.EditPanel.Act_List_Address;
import com.eksirsanat.ir.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Act_Main_Profile_User extends AppCompatActivity implements Config {

    Adapter_Rec_User adpter;

    RecyclerView recyclerView;

    ImageView img_back;

    TextView username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__main__profile__user);
        Get_Info.getText(Act_Main_Profile_User.this);
        Cast();
        Match_Rec();
        GetInfo();

    }

    void Cast(){
        recyclerView=findViewById(R.id.Rec_UserInfo);
        img_back=findViewById(R.id.Close_Main_Toolbar);
        username=findViewById(R.id.Username_Userinfo);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }



    void Match_Rec(){

        List<DataModel_CLass> List=Api_Fake_User.ListModel(Act_Main_Profile_User.this);
        adpter=new Adapter_Rec_User(Act_Main_Profile_User.this,List,Click);
        RecyclerView.LayoutManager manager=new GridLayoutManager(this,1);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adpter);
    }


    View.OnClickListener Click=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DataModel_CLass dataModel_cLass=(DataModel_CLass)v.getTag();
            int id=dataModel_cLass.getId();


            if (id==4){
                startActivity(new Intent(Act_Main_Profile_User.this, Act_FavList.class));
            }
            if (id==6){
                startActivity(new Intent(Act_Main_Profile_User.this, Act_List_Address.class));
            }

            if (id==1){
                startActivity(new Intent(Act_Main_Profile_User.this, Act_Edit_User.class));
            }

            if (id==8){
                startActivity(new Intent(Act_Main_Profile_User.this, Act_ChangePassword.class));
                finish();

            }


            if (id==9){
                final AlertDialog.Builder alertdialog=new AlertDialog.Builder(Act_Main_Profile_User.this,R.style.AlertDialogCustom);

                alertdialog.setTitle("خروح از فروشگاه");
                alertdialog.setMessage("آیا برای خروج از فروشگاه اطمینان دارید؟");
                alertdialog.setPositiveButton("بله ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences=getSharedPreferences("info",0);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.clear();
                        editor.apply();

                        SharedPreferences sharedPreferences2=getSharedPreferences("NameUser", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor2=sharedPreferences2.edit();
                        editor2.clear();
                        editor2.apply();
                        finish();
                    }
                });

                alertdialog.setNegativeButton("لغو عملیات", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertdialog.show();
            }




        }
    };


    void GetInfo(){

        String url=urlHome+"userinfo.php?token="+Get_Token.getToken(this);
        Log.i("urlll",url);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getString("status").equals("ok")){

                        JSONObject js=response.getJSONObject("userInfo");
                        username.setText(js.getString("email"));

                    }
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
        Request_Volley.getInstance(this).add(jsonObjectRequest);
    }



}
