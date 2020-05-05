package com.eksirsanat.ir.ViewPager_Tablayout_Category.More_Category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eksirsanat.ir.Act_Home;
import com.eksirsanat.ir.Main_Home.Menu_Navigation.Act_About_Company;
import com.eksirsanat.ir.Main_Home.Product.Api_product;
import com.eksirsanat.ir.Main_Home.Product.Custom_Product;
import com.eksirsanat.ir.Main_Home.Product.Datamodel_ListProduct;
import com.eksirsanat.ir.R;
import com.eksirsanat.ir.ViewPager_Tablayout_Category.Act_ViewPager_Category;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.List;

public class Act_Cat_Home extends AppCompatActivity {

    Custom_Product customProduct,customProductNew;
    Context context;


    ImageView img_search,img_store,img_search_back,menu;
    EditText edt_search;

    TextView title_tolbar;

    String nameCat,idCat;

    Adapter_Expand_Server_1 adapter;

    ExpandableListView expandableList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__cat__home);
        Cast();
        Header();

        GetList_CustomProduct_Porforosh();
        //SetUpExpandRecView();
    }

    void Cast(){
        context= Act_Cat_Home.this;
        //title_tolbar=findViewById(R.id.Titile_Tolbar_Cat);

        nameCat=getIntent().getStringExtra("nameCat");
        idCat=getIntent().getStringExtra("idCat");


        //title_tolbar.setText(nameCat);
        expandableList=findViewById(R.id.ExpandListView);


    }

    void Header(){
        img_search=findViewById(R.id.Img_search);
        img_store=findViewById(R.id.Img_store_main);
        edt_search=findViewById(R.id.Edt_search);
        img_search_back=findViewById(R.id.Img_search_back);

    }


    /*void SetUpExpandRecView(){


      Api_Expand_Server_1.GetListCategory_2(Act_Cat_Home.this, new Api_Expand_Server_1.GetAllList() {
          @Override
          public void get_List(List<HeadModel_Server> Header, HashMap<String, List<ItemModel_Server>> ListItems) {

              adapter=new Adapter_Expand_Server_1(Act_Cat_Home.this,Header,ListItems);
              expandableList.setAdapter(adapter);

          }
      });


        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Toast.makeText(context, groupPosition+"   "+childPosition, Toast.LENGTH_SHORT).show();

                Toast.makeText(context, id+"", Toast.LENGTH_SHORT).show();

                return true;
            }
        });


        expandableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "cccc", Toast.LENGTH_SHORT).show();
            }
        });



        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
                return false;
            }
        });





    }*/





    void GetList_CustomProduct_Porforosh(){

        Api_product.GetLsit_Product("product-sale",context, new Api_product.ListProduct() {
            @Override
            public void ListPost(List<Datamodel_ListProduct> listProductList) {
                customProduct=findViewById(R.id.custom_Product_porforosh);

                customProduct.ViewAndCast();
                customProduct.getList(listProductList);
                customProduct.setTitle("محصولات پر فروش");

            }
        });
        GetList_CustomProduct_New();
    }

    void GetList_CustomProduct_New(){

        Api_product.GetLsit_Product("product-new",context, new Api_product.ListProduct() {
            @Override
            public void ListPost(List<Datamodel_ListProduct> listProductList) {
                customProductNew=findViewById(R.id.custom_Product_new);

                customProductNew.ViewAndCast();
                customProductNew.getList(listProductList);
                customProductNew.setTitle("جدید ترین محصولات");
            }
        });
    }
}
