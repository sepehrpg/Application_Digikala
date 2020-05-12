package com.eksirsanat.ir.Panel_User.Panel.EditPanel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Act_Edit_User extends AppCompatActivity implements Config {

    ImageView img_back;
    EditText name,family,codemeli,phone,mobile;
    Spinner day,mon,year,town,city;
    RadioGroup radioGroup;
    RadioButton man,woman;

    String Email;

    String st_name,st_family,st_phone,st_mobile,st_codemeli;
    Button btnok;

    List<String> cityList;
    List<String> townList;
    List<String> idCity;

    String idCat=null;


    String gender="آقا";


    List<String> listDay;
    List<String> listMon;
    List<String> listYear;


    String Birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__edit__user);

        try {
            Cast();
            ocClick();
            GetInfo();
            GetList_Town();
            Set_ListBirth();

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    void Cast(){
        img_back=findViewById(R.id.back_UserInfo);
        name=findViewById(R.id.Edt_nameUserE);
        family=findViewById(R.id.Edt_FamilyE);
        codemeli=findViewById(R.id.Edt_CodeMeliE);
        phone=findViewById(R.id.Edt_PhoneE);
        radioGroup=findViewById(R.id.RadioGp_E);
        man=findViewById(R.id.RadioMan);
        woman=findViewById(R.id.RadioWoman);
        mobile=findViewById(R.id.Edt_MobileE);
        day=findViewById(R.id.Sp_day);
        mon=findViewById(R.id.Sp_mon);
        year=findViewById(R.id.Sp_year);
        town=findViewById(R.id.Sp_city);
        city=findViewById(R.id.Sp_Ostan);
        btnok=findViewById(R.id.Btn_OkUserInfo);
        cityList=new ArrayList<>();
        townList=new ArrayList<>();
        idCity=new ArrayList<>();
    }

    void Set_ListBirth(){
        listDay=new ArrayList<>();
        listMon=new ArrayList<>();
        listYear=new ArrayList<>();

        String[] DAY={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19",
                "20","21","22","23","24","25","26","27","28","29","30","31"};
        //listDay.addAll(Arrays.asList(DAY));
        listDay.add("روز");
        for(int a=0; a<DAY.length ;a++){
            listDay.add(DAY[a]);
        }


        String[] MON={"01","02","03","04","05","06","07","08","09","10","11","12"};
        listMon.add("ماه");
        for (int b=0;b<MON.length;b++){
            listMon.add(MON[b]);
        }

        int YEAR_Int=1349;
        listYear.add("سال");
        for (int i=1401; i>YEAR_Int ;i--){
            String MAIN_YEAR=String.valueOf(i);
            listYear.add(MAIN_YEAR);
        }



        ArrayAdapter<String> adapterDay=new ArrayAdapter<String>(Act_Edit_User.this,android.R.layout.simple_spinner_dropdown_item,listDay);
        day.setAdapter(adapterDay);

        ArrayAdapter<String> adapterMon=new ArrayAdapter<String>(Act_Edit_User.this,android.R.layout.simple_spinner_dropdown_item,listMon);
        mon.setAdapter(adapterMon);

        ArrayAdapter<String> adapterYear=new ArrayAdapter<String>(Act_Edit_User.this,android.R.layout.simple_spinner_dropdown_item,listYear);
        year.setAdapter(adapterYear);



    }





    void ocClick(){
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        man.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    gender="آقا";

                }
            }
        });

        woman.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    gender="خانم";
                }
            }
        });


        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                st_name=name.getText().toString().trim();
                st_family=family.getText().toString().trim();
                st_codemeli=codemeli.getText().toString().trim();
                st_mobile=mobile.getText().toString().trim();
                st_phone=phone.getText().toString().trim();


                if (st_name.length()<2){
                    name.requestFocus();
                    name.setError("وارد کردن نام اجباریست");
                }

                else if (st_family.length()<4){
                    family.requestFocus();
                    family.setError("وارد کردن نام خانوادگی اجباریست");
                }



                else if (st_mobile.length()!=11){
                    mobile.requestFocus();
                    mobile.setError("َشماره موبایل باید 11 رقم باشد ");
                }

                else if (city.getSelectedItemPosition()==0){
                    Toast.makeText(Act_Edit_User.this, "لطفا شهر و استان خود را انتخاب کنید", Toast.LENGTH_SHORT).show();

                }
                else if(town.getSelectedItemPosition()==0){
                    Toast.makeText(Act_Edit_User.this, "لطفا شهر و استان خود را انتخاب کنید", Toast.LENGTH_SHORT).show();
                }




                else {
                    Birthday=year.getSelectedItem().toString()+"/"+mon.getSelectedItem().toString()+"/"+day.getSelectedItem().toString();
                    //Toast.makeText(Act_Edit_User.this, Birthday, Toast.LENGTH_SHORT).show();
                    Send_Info(st_name,st_family,st_phone,st_mobile,st_codemeli);
                }
            }
        });



        town.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idCat=idCity.get(position);
                GetList_City(idCat);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(Act_Edit_User.this,android.R.layout.simple_spinner_dropdown_item,cityList);
                    city.setAdapter(arrayAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Act_Edit_User.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(Act_Edit_User.this,android.R.layout.simple_spinner_dropdown_item,townList);
                    town.setAdapter(arrayAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Act_Edit_User.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Volley.newRequestQueue(context).add(jsonObjectRequest);
        Request_Volley.getInstance(this).add(jsonObjectRequest);
    }



    public void Send_Info(final String Name, final String Family, final String Phone, final String Mobile, final String Codemeli){

        String url=urlHome+"edituser.php?token="+ Get_Token.getToken(this);
        StringRequest stringRequest=new StringRequest(1, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Act_Edit_User.this, "اطلاعات با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Act_Edit_User.this, "خطا در ارتباط با سروز", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap=new HashMap<>();

                hashMap.put("name",Name);

                hashMap.put("family",Family);
                if (!Codemeli.equals("")){
                    hashMap.put("codemeli",Codemeli);
                }
                if (!Phone.equals("")){
                    hashMap.put("phone",Phone);
                }
                hashMap.put("mobile",Mobile);
                hashMap.put("gender",gender);

                 if (day.getSelectedItemPosition()!=0 && mon.getSelectedItemPosition()!=0 && year.getSelectedItemPosition()!=0){
                     hashMap.put("date_birth",Birthday);
                 }

                if (!idCat.equals("0") ){
                    hashMap.put("idcity",idCat);
                }

                return hashMap;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Volley.newRequestQueue(context).add(jsonObjectRequest);
        Request_Volley.getInstance(this).add(stringRequest);

    }

    void GetInfo(){

        String url=urlHome+"userinfo.php?token="+ Get_Token.getToken(this);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getString("status").equals("ok")){

                        JSONObject js=response.getJSONObject("userInfo");
                        if (!js.isNull("name")){
                            name.setText(js.getString("name"));
                            SharedPreferences sharedPreferences=getSharedPreferences("NameUser", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("nameUser",js.getString("name"));
                            editor.apply();
                        }
                        if (!js.isNull("family")){
                            family.setText(js.getString("family"));
                        }

                        if (!js.isNull("codemeli")){
                            codemeli.setText(js.getString("codemeli"));
                        }

                        if (!js.isNull("phone")){
                            phone.setText(js.getString("phone"));
                        }

                        if (!js.isNull("mobile")){
                            mobile.setText(js.getString("mobile"));
                        }

                        if (!js.isNull("gender")){

                            if (js.getString("gender").equals("آقا")){
                                man.setChecked(true);
                            }
                            if (js.getString("gender").equals("خانم")){
                                woman.setChecked(true);
                            }

                        }else {
                            man.setChecked(true);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Act_Edit_User.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Volley.newRequestQueue(context).add(jsonObjectRequest);
        Request_Volley.getInstance(this).add(jsonObjectRequest);

    }
















}
