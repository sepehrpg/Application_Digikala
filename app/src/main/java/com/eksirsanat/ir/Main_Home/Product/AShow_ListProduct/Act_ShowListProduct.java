package com.eksirsanat.ir.Main_Home.Product.AShow_ListProduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eksirsanat.ir.Main_Home.Product.AShow_ListProduct.Adapter.Adapter_Recview_ListProduct;
import com.eksirsanat.ir.Main_Home.Product.AShow_ListProduct.Api.Api_List_Product;
import com.eksirsanat.ir.Main_Home.Product.Act_SeeAll_For_newAndPrice;
import com.eksirsanat.ir.Main_Home.Product.Filters_Product.Act_Filters_Product;
import com.eksirsanat.ir.R;
import com.eksirsanat.ir.Search_Product.Act_Search_Product;

import java.util.List;

public class Act_ShowListProduct extends AppCompatActivity implements Dialog_Class.getText {
    TextView txt_toolbar,title_order,Txt_ExitProduct;
    LinearLayout line_filter,lint_order;
    ImageView img_order_product,img_back,img_search;
    ;
    RecyclerView recyclerView;

    String idCat,nameTitle;
    Adapter_Recview_ListProduct adapter;
    String title;
    int order=1;

    String moreUrl=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__show_list_product);
        Cast();
        onClick();
        SetRec(1,moreUrl);

    }

    void Cast(){
        SharedPreferences filter=getSharedPreferences("FiltersList",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1=filter.edit();
        editor1.clear();
        editor1.apply();


        img_search=findViewById(R.id.Img_search_Main_Toolbar);
        line_filter=findViewById(R.id.Line_Filter);
        lint_order=findViewById(R.id.Line_OrderProduct);
        title_order=findViewById(R.id.Title_order);
        img_order_product=findViewById(R.id.Img_ChangeOrderProduct);
        img_back=findViewById(R.id.Close_Main_Toolbar);
        recyclerView=findViewById(R.id.Rec_ProductGroup);
        txt_toolbar=findViewById(R.id.Title_Custom_Toolbar);
        Txt_ExitProduct=findViewById(R.id.Txt_ExitProduct);
        Txt_ExitProduct.setVisibility(View.GONE);

        idCat=getIntent().getStringExtra("idcat");
        nameTitle=getIntent().getStringExtra("name");
        //SharedPreferences sharedPreferences=getSharedPreferences("radio",0);
        //title=sharedPreferences.getString("name",null);
        txt_toolbar.setText(nameTitle);


    }

    void onClick(){

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_ShowListProduct.this, Act_Search_Product.class));

            }
        });

        line_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Act_ShowListProduct.this, Act_Filters_Product.class);
                intent.putExtra("idCat",idCat);
                startActivity(intent);
            }
        });

        lint_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog_Class dialog_class=new Dialog_Class(Act_ShowListProduct.this,Act_ShowListProduct.this);
                dialog_class.show();
                //SharedPreferences sharedPreferences=getSharedPreferences("radio",0);
                //title=sharedPreferences.getString("name","پر بازدید ترین");
                //title_order.setText(title);
            }
        });

        img_order_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (order==1){
                        order=2;
                        img_order_product.setImageDrawable(getResources().getDrawable(R.drawable.ic_horizontal));
                        SetRec(order,moreUrl);
                    }
                    else {
                        order=1;
                        img_order_product.setImageDrawable(getResources().getDrawable(R.drawable.ic_vertical));
                        SetRec(order,moreUrl);
                    }


                }catch (Exception e){
                    Toast.makeText(Act_ShowListProduct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences filter=getSharedPreferences("FiltersList", Context.MODE_PRIVATE);
        moreUrl=filter.getString("newUrl",null);
        SetRec(order,moreUrl);


    }




    void SetRec(final int orders, String moreUrl){

        Api_List_Product.GetListCategory_2(Act_ShowListProduct.this, idCat,moreUrl, new Api_List_Product.GetAllList() {
            @Override
            public void get_List(List<All_ListProduct_Model> list) {

                if (list.size()<1){
                    Txt_ExitProduct.setVisibility(View.VISIBLE);
                }
                else {
                    Txt_ExitProduct.setVisibility(View.GONE);
                }

                if (orders==1){
                    adapter=new Adapter_Recview_ListProduct(Act_ShowListProduct.this,orders,list);
                    RecyclerView.LayoutManager manager=new GridLayoutManager(Act_ShowListProduct.this,1);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                }else {
                    adapter = new Adapter_Recview_ListProduct(Act_ShowListProduct.this, orders, list);
                    RecyclerView.LayoutManager manager = new GridLayoutManager(Act_ShowListProduct.this, 2);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

            }
        });

    }

    @Override
    public void get_Text(String text) {
        title_order.setText(text);
    }
}



