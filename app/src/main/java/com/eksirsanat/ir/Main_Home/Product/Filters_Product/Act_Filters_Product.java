package com.eksirsanat.ir.Main_Home.Product.Filters_Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eksirsanat.ir.R;

import java.util.ArrayList;
import java.util.List;

public class Act_Filters_Product extends AppCompatActivity implements Adapter_Filters_Value.GetDataFromFilter {

    ImageView img_back;
    Button btn_filters;
    RecyclerView rec_filters,rec_filters_value;

    LinearLayout line_delete_filter;

    Adapter_Filters adapter;
    Adapter_Filters_Value adapter_child;

    int Position;

    EditText edt_search;




    String idCat;

    String from_Price,to_Price;


    String newUrl="";
    String sendColor="";
    String sendBrand="";

    String sendProperty="";


    List<Filters_DataModel> listProperty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__filters__product);
        try {
            Cast();
            SharedPreferences sharedPreferences=getSharedPreferences("PositionFilter",0);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.clear();
            editor.apply();
            edt_search.setVisibility(View.GONE);
            Get_List_Filter(idCat,"قیمت",1);
            //SendPropertyFilters();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    void Cast(){

        SharedPreferences sharedPreferences=getSharedPreferences("saveFilter", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();

        SharedPreferences filter=getSharedPreferences("FiltersList",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1=filter.edit();
        editor1.clear();
        editor1.apply();
        //adapter_child.notifyDataSetChanged();

        listProperty=new ArrayList<>();
        img_back=findViewById(R.id.Close_FilterProduct);
        btn_filters=findViewById(R.id.Btn_Filter);
        rec_filters=findViewById(R.id.Rec_Filters);
        edt_search=findViewById(R.id.Edt_Search_Filters);
        rec_filters_value=findViewById(R.id.Rec_Filters_Value);
        line_delete_filter=findViewById(R.id.Filter_Line_Pro);
        idCat=getIntent().getStringExtra("idCat");
        Click_ok();
    }


    String idColor;
    String idBrand;
    void Click_ok(){







        btn_filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    //Toast.makeText(Act_Filters_Product.this, from_Price+" and  "+ to_Price, Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences=getSharedPreferences("saveFilter",Context.MODE_PRIVATE);

                    //Log.i("IDCOLOR",sharedPreferences.getString("valueColor","nul"));

                     idColor=sharedPreferences.getString("valueColor",null);
                     idBrand=sharedPreferences.getString("valueBrand",null);


                    if (idColor!=null && !idColor.equals("") && !idColor.equals("null")){
                        sendColor="";
                        if (idColor.endsWith(",")){
                        idColor=idColor.substring(0,idColor.length()-1);
                      }
                        sendColor="&colors="+idColor;
                        newUrl+=sendColor;
                        Log.i("NEWURL",newUrl);

                    }
                    if (idBrand!=null &&  !idBrand.equals("") && !idBrand.equals("null")){
                        sendBrand="";
                        if (idBrand.endsWith(",")){
                            idBrand=idBrand.substring(0,idBrand.length()-1);
                        }
                        sendBrand="&idbrand="+idBrand;
                        newUrl+=sendBrand;
                    }

                    if (idColor!=null && idColor.equals("")){
                        sendColor="";
                    }
                    if (idBrand!=null &&  idBrand.equals("")){
                        sendBrand="";
                    }


                    //newUrl=sendBrand+sendColor;

                    if (from_Price!=null && !from_Price.equals("") && !from_Price.equals("null")){

                        if (to_Price!=null && !to_Price.equals("مهم نیست") && !to_Price.equals("") && !to_Price.equals("null")){
                            newUrl+="&price="+from_Price+"-"+to_Price;

                        }else {
                            newUrl+="&price="+from_Price;
                        }

                    }

                    SendPropertyFilters();
                    if (sendProperty!=null && !sendProperty.equals("") && !sendProperty.equals("null")){
                        if (sendProperty.endsWith("!")){
                            sendProperty=sendProperty.substring(0,sendProperty.length()-1);
                        }
                        newUrl+=sendProperty;

                    }


                    SharedPreferences filter=getSharedPreferences("FiltersList",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=filter.edit();
                    editor.putString("newUrl",newUrl);
                    editor.apply();

                    finish();









                }catch (Exception e){
                    Toast.makeText(Act_Filters_Product.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
       line_delete_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBrand="";
                sendColor="";
                sendProperty="";
                to_Price="";
                from_Price="";
                idColor=null;
                idBrand=null;
                SharedPreferences sharedPreferences=getSharedPreferences("saveFilter", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.apply();

                SharedPreferences filter=getSharedPreferences("FiltersList",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1=filter.edit();
                editor1.clear();
                editor1.apply();

                newUrl="";

                adapter_child.notifyDataSetChanged();

            }
        });
    }







    //get filter header and child...................................................................
    void Get_List_Filter(String idCat, String nameValue, final int SpanCount){

        Api_Filters.GetList_Filters(Act_Filters_Product.this, idCat, new Api_Filters.List_Filter() {
            @Override
            public void ListFilter(List<Filters_DataModel> List_Filter_Product) {
                listProperty=List_Filter_Product;

                adapter=new Adapter_Filters(Act_Filters_Product.this, List_Filter_Product, clickListener);
                RecyclerView.LayoutManager manager=new GridLayoutManager(Act_Filters_Product.this,1);
                rec_filters.setLayoutManager(manager);
                rec_filters.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });


       Api_Child_Filter.GetList_Filters(Act_Filters_Product.this, idCat, nameValue, new Api_Child_Filter.List_Filter() {
            @Override
            public void ListFilter(List<FilterModel_ColorAndBrand> list, List<String> property) {


                adapter_child=new Adapter_Filters_Value(Act_Filters_Product.this,list,property,Act_Filters_Product.this);

                RecyclerView.LayoutManager manager=new GridLayoutManager(Act_Filters_Product.this,SpanCount);
                rec_filters_value.setLayoutManager(manager);
                rec_filters_value.setAdapter(adapter_child);
                adapter_child.notifyDataSetChanged();
            }
        });


        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adapter_child.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter_child.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter_child.getFilter().filter(s);

            }
        });


    }
    //..............................................................................................







    //onClick For Header Filter.....................................................................
    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ContentValues contentValues=(ContentValues)v.getTag();
            String nameValue=contentValues.getAsString("name");
            String nameValueProperty=contentValues.getAsString("nameEn");
            Position=contentValues.getAsInteger("position");

            if (Position==0){
                edt_search.setVisibility(View.GONE);

            }else {
                edt_search.setVisibility(View.VISIBLE);
            }

           if (Position==0 || Position==1 || Position==2){
                //Toast.makeText(Act_Filters_Product.this, "yes", Toast.LENGTH_SHORT).show();
                if (nameValue.equals("رنگ ها")){
                    nameValue="رنگ";
                }
                TextView nameFilter=v.findViewById(R.id.Txt_NameFilter);
                LinearLayout linearLayout=v.findViewById(R.id.LineBackItemFiilter);
                nameFilter.setBackgroundColor(getResources().getColor(R.color.white));
                nameFilter.setTextColor(getResources().getColor(R.color.meshkii));
                SharedPreferences sharedPreferences=getSharedPreferences("PositionFilter",0);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("pos",Position);
                editor.apply();
                adapter.notifyDataSetChanged();
                if (Position==1){
                    Get_List_Filter(idCat,nameValue,2);

                }else {
                    Get_List_Filter(idCat,nameValue,1);
                }
            }
            else {
                TextView nameFilter=v.findViewById(R.id.Txt_NameFilter);
                LinearLayout linearLayout=v.findViewById(R.id.LineBackItemFiilter);
                nameFilter.setBackgroundColor(getResources().getColor(R.color.white));
                nameFilter.setTextColor(getResources().getColor(R.color.meshkii));
                SharedPreferences sharedPreferences=getSharedPreferences("PositionFilter",0);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("pos",Position);
                editor.apply();
                adapter.notifyDataSetChanged();
                Get_List_Filter(idCat,nameValueProperty,1);
            }
        }
    };

    //..............................................................................................





    //..Interface from adapter......................................................................
    @Override
    public void get_Price(String from, String to) {

        from_Price=from;
        to_Price=to;
    }

    @Override
    public void sizeProperty(int size) {
        //Size=size;
    }
    //..............................................................................................




    public void SendPropertyFilters(){
       sendProperty="";
       for (int i=0;i<listProperty.size();i++){
           Filters_DataModel model=listProperty.get(i);

             if (model.getNameEn()!=null){
                 SharedPreferences sharedPreferences=getSharedPreferences("saveFilter",Context.MODE_PRIVATE);
                 String Property=sharedPreferences.getString(model.getNameEn(),null);

                 if (Property!=null && !Property.equals("") && !Property.equals("null")){
                     sendProperty+="&"+model.getNameEn()+"="+Property;
                 }
             }


         }




    }

}
