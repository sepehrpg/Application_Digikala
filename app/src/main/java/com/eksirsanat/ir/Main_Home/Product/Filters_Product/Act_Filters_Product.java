package com.eksirsanat.ir.Main_Home.Product.Filters_Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.eksirsanat.ir.R;

import java.util.List;

public class Act_Filters_Product extends AppCompatActivity {

    ImageView img_back;
    Button btn_filters;
    RecyclerView rec_filters,rec_filters_value;

    Adapter_Filters adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__filters__product);
        Cast();
        Get_List_Filter();
    }

    void Cast(){
        img_back=findViewById(R.id.Close_FilterProduct);
        btn_filters=findViewById(R.id.Btn_Filter);
        rec_filters=findViewById(R.id.Rec_Filters);
        rec_filters_value=findViewById(R.id.Rec_Filters_Value);
    }


    void Get_List_Filter(){

        Api_Filters.GetList_Filters(Act_Filters_Product.this, "1", new Api_Filters.List_Filter() {
            @Override
            public void ListFilter(List<Filters_DataModel> List_Filter_Product) {

                adapter=new Adapter_Filters(Act_Filters_Product.this,List_Filter_Product);
                RecyclerView.LayoutManager manager=new GridLayoutManager(Act_Filters_Product.this,1);
                rec_filters.setLayoutManager(manager);
                rec_filters.setAdapter(adapter);

            }
        });

    }
}
