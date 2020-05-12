package com.eksirsanat.ir.Panel_User.Panel.EditPanel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.eksirsanat.ir.Action.Get_Token;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Config;
import com.eksirsanat.ir.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Act_Add_Address extends AppCompatActivity implements Config {

    EditText edt_nameuser,edt_mobile,edt_phone,edt_codeposti,edt_address;
    Spinner sp_town,sp_city;
    Button btn_ok;
    ImageView img_back;


    List<String> cityList;
    List<String> townList;
    List<String> idCity;


    String idCat=null;

    int Send_ID_City;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__add__address);
        try {
            Cast();
            GetList_Town();
            onClick();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    void Cast(){
        edt_nameuser=findViewById(R.id.Edt_nameUserE_Address);
        edt_mobile=findViewById(R.id.Edt_MobileE_Address);
        edt_phone=findViewById(R.id.Edt_PhoneE_Address);
        edt_codeposti=findViewById(R.id.Edt_CodePosti);
        edt_address=findViewById(R.id.Edt_Address);
        sp_town=findViewById(R.id.Sp_Ostan_Address);
        sp_city=findViewById(R.id.Sp_city_Address);
        btn_ok=findViewById(R.id.Btn_OkUserAddress);
        img_back=findViewById(R.id.Img_back_Address);
        cityList=new ArrayList<>();
        townList=new ArrayList<>();
        idCity=new ArrayList<>();
    }

    public void onClick(){

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

      sp_town.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idCat=idCity.get(position);
                GetList_City(idCat);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=edt_nameuser.getText().toString().trim();
                String mobile=edt_mobile.getText().toString().trim();
                String phone=edt_phone.getText().toString().trim();
                String codeposti=edt_codeposti.getText().toString().trim();
                String address=edt_address.getText().toString().trim();


                if (name.length()<3){
                    edt_nameuser.requestFocus();
                    edt_nameuser.setError("نام و نام خانوادگی را وارد کنید");
                }

                else if (mobile.length()<11){
                    edt_mobile.requestFocus();
                    edt_mobile.setError("شماره موبایل باید 11 رقم باشد");
                }

                else if (phone.length()<11){
                    edt_phone.requestFocus();
                    edt_phone.setError("شماره تماس منزل را با کد استان وارد کنید");
                }

                else if (codeposti.length()<9){
                    edt_codeposti.requestFocus();
                    edt_codeposti.setError("کد پستی را وارد کنید");
                }
                else if (address.length()<9){
                    edt_address.requestFocus();
                    edt_address.setError("آدرس را کامل وارد کنید");
                }

                else if (sp_town.getSelectedItemPosition()==0){
                    Toast.makeText(Act_Add_Address.this, "لطفا شهر و استان خود را انتخاب کنید", Toast.LENGTH_SHORT).show();
                }

                else if (sp_city.getSelectedItemPosition()==0){
                    Toast.makeText(Act_Add_Address.this, "لطفا شهر و استان خود را انتخاب کنید", Toast.LENGTH_SHORT).show();
                }



                else {
                    Send_Info(name,phone,mobile,codeposti,address);
                }
            }
        });


    }



    public void Send_Info(final String Name, final String Phone, final String Mobile, final String Codeposti, final String Address){

        String url=urlHome+"addadress.php?token="+ Get_Token.getToken(this);
        StringRequest stringRequest=new StringRequest(1, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(Act_Add_Address.this, "اطلاعات با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Act_Add_Address.this, "خطا در ارتباط با سرور", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap=new HashMap<>();

                hashMap.put("name",Name);
                hashMap.put("phone",Phone);
                hashMap.put("mobile",Mobile);
                hashMap.put("codeposti",Codeposti);
                hashMap.put("address",Address);
                hashMap.put("idcity",idCat);

                return hashMap;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Volley.newRequestQueue(context).add(jsonObjectRequest);
        Request_Volley.getInstance(this).add(stringRequest);

    }


    void GetList_City(String idcity){

        if (!cityList.isEmpty()){
            cityList.clear();
        }

        String url=urlHome+"city.php?subid="+idcity;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray=response.getJSONArray("list-city");
                    cityList.add("شهر");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        cityList.add(jsonObject.getString("name"));
                    }

                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(Act_Add_Address.this,android.R.layout.simple_spinner_dropdown_item,cityList);
                    sp_city.setAdapter(arrayAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Act_Add_Address.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Volley.newRequestQueue(context).add(jsonObjectRequest);
        Request_Volley.getInstance(this).add(jsonObjectRequest);
    }

    void GetList_Town(){

        String url=urlHome+"city.php?subid="+1;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray=response.getJSONArray("list-city");
                    townList.add("استان");
                    idCity.add("0");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        townList.add(jsonObject.getString("name"));
                        idCity.add(jsonObject.getString("idcity"));

                    }

                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(Act_Add_Address.this,android.R.layout.simple_spinner_dropdown_item,townList);
                    sp_town.setAdapter(arrayAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Act_Add_Address.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Volley.newRequestQueue(context).add(jsonObjectRequest);
        Request_Volley.getInstance(this).add(jsonObjectRequest);
    }

}
