package com.eksirsanat.ir.Main_Home.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eksirsanat.ir.Main_Home.Product.AShow_ListProduct.All_ListProduct_Model;
import com.eksirsanat.ir.Main_Home.Product.AShow_ListProduct.Api.Api_List_Product;
import com.eksirsanat.ir.R;
import com.eksirsanat.ir.Search_Product.Act_Search_Product;
import com.eksirsanat.ir.ViewPager_Tablayout_Category.More_Category.Api.Api_GetListProduct_Firs;
import com.eksirsanat.ir.ViewPager_Tablayout_Category.More_Category.Custom_Product_ViewPager;

import java.util.List;

public class Act_SeeAll_For_newAndPrice extends AppCompatActivity {
    Custom_Product customProduct,customProductNew,custom_MainProduct;

    RecyclerView recyclerView;
    String idCat;
    String nameCat;

    ImageView img_back,img_search,img_store;
    TextView title_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__see_all__for_new_and_price);
        Cast();
        onClick();
        GetList_custom_MainProduct();
    }

    void Cast(){
        nameCat=getIntent().getStringExtra("nameCat");
        img_back=findViewById(R.id.Close_Main_Toolbar);
        title_text=findViewById(R.id.Title_Custom_Toolbar);
        img_search=findViewById(R.id.Img_search_Main_Toolbar);
        if (nameCat.equals("product-sale")){
            title_text.setText("محصولات پر فروش");

        }
        else {
            title_text.setText("جدید ترین محصولات");
        }
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
                startActivity(new Intent(Act_SeeAll_For_newAndPrice.this, Act_Search_Product.class));
            }
        });


    }




    void GetList_custom_MainProduct(){

        if (nameCat.equals("product-sale")){

            Api_product.GetLsit_Product("product-sale",Act_SeeAll_For_newAndPrice.this, new Api_product.ListProduct() {
                @Override
                public void ListPost(List<Datamodel_ListProduct> listProductList) {
                    customProduct=findViewById(R.id.custom_Product_porforosh);

                    customProduct.ViewAndCast("product-sale");
                    customProduct.getList(listProductList,2);
                    customProduct.setTitle("محصولات پر فروش");

                }
            });

        }else if (nameCat.equals("product-new")){
            Api_product.GetLsit_Product("product-new",Act_SeeAll_For_newAndPrice.this, new Api_product.ListProduct() {
                @Override
                public void ListPost(List<Datamodel_ListProduct> listProductList) {
                    customProduct=findViewById(R.id.custom_Product_porforosh);
                    customProduct.ViewAndCast("product-new");
                    customProduct.getList(listProductList,2);
                    customProduct.setTitle("جدید ترین محصولات");

                }
            });
        }


    }
}
