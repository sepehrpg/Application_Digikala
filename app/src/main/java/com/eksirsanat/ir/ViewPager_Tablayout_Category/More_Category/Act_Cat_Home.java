package com.eksirsanat.ir.ViewPager_Tablayout_Category.More_Category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eksirsanat.ir.Main_Home.Product.AShow_ListProduct.Act_ShowListProduct;
import com.eksirsanat.ir.Main_Home.Product.AShow_ListProduct.All_ListProduct_Model;
import com.eksirsanat.ir.Main_Home.Product.AShow_ListProduct.Api.Api_List_Product;
import com.eksirsanat.ir.Main_Home.Product.Api_product;
import com.eksirsanat.ir.Main_Home.Product.Custom_Product;
import com.eksirsanat.ir.Main_Home.Product.Datamodel_ListProduct;
import com.eksirsanat.ir.R;
import com.eksirsanat.ir.Search_Product.Act_Search_Product;
import com.eksirsanat.ir.ViewPager_Tablayout_Category.More_Category.Adapter.AdapterExpandable_ListProduct;
import com.eksirsanat.ir.ViewPager_Tablayout_Category.More_Category.Api.Api_Expand_Server_1;
import com.eksirsanat.ir.ViewPager_Tablayout_Category.More_Category.Api.Api_GetListProduct_Firs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Act_Cat_Home extends AppCompatActivity {

    Custom_Product customProduct,customProductNew,custom_MainProduct;
    Context context;
    Custom_Product_ViewPager custom_product_viewPager;



    ImageView img_search,img_store,img_back,menu;
    EditText edt_search;

    TextView title_tolbar;

    String nameCat,idCat;

    AdapterExpandable_ListProduct adapter;


    ExpandableListView expandableList;
    List<String> Header_Main;
    HashMap<String, List<ItemModel_Server>> ListItems_Main;


    String subid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__cat__home);
        Cast();
        Header();
        GetList_CustomProduct_Porforosh();
        SetUpExpandRecView();
        GetList_custom_MainProduct();
    }

    void Cast(){
        context= Act_Cat_Home.this;
        Header_Main=new ArrayList<>();
        ListItems_Main=new HashMap<>();
        nameCat=getIntent().getStringExtra("nameCat");
        idCat=getIntent().getStringExtra("idCat");
        expandableList=findViewById(R.id.Expand_ActHomeCat2);

    }

    void Header(){
        img_back=findViewById(R.id.Close_Main_Toolbar);
        title_tolbar=findViewById(R.id.Title_Custom_Toolbar);
        img_search=findViewById(R.id.Img_search_Main_Toolbar);
        title_tolbar.setText(nameCat);


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, Act_Search_Product.class));

            }
        });

    }


    public void SetUpExpandRecView(){


        try {
            Api_Expand_Server_1.GetListCategory_2(Act_Cat_Home.this, idCat, new Api_Expand_Server_1.GetAllList() {
                @Override
                public void get_List(List<ItemModel_Server> items) {
                    Header_Main.add(nameCat);
                    ListItems_Main.put(nameCat,items);

                    adapter=new AdapterExpandable_ListProduct(Act_Cat_Home.this,Header_Main,ListItems_Main);
                    expandableList.setAdapter(adapter);
                    //Header_Main=Header;
                    //ListItems_Main=ListItems;

                }
            });

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }



       expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                String idCat=ListItems_Main.get(Header_Main.get(groupPosition)).get(childPosition).getId();
                String name=ListItems_Main.get(Header_Main.get(groupPosition)).get(childPosition).getName();
                Intent intent=new Intent(Act_Cat_Home.this, Act_ShowListProduct.class);
                intent.putExtra("idcat",idCat);
                intent.putExtra("name",name);
                startActivity(intent);
                //Toast.makeText(Act_ShowFirs_ListCat.this, idCat, Toast.LENGTH_SHORT).show();


                return false;
            }
        });



        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                return false;
            }
        });






    }





    void GetList_custom_MainProduct(){

        try {
            Api_GetListProduct_Firs.Get_ListProduct_First(context, idCat, new Api_List_Product.GetAllList() {
                @Override
                public void get_List(List<All_ListProduct_Model> list) {
                    Log.i("OKK","OK");
                    custom_product_viewPager=findViewById(R.id.custom_MainProduct);

                    custom_product_viewPager.ViewAndCast(idCat,nameCat);
                    custom_product_viewPager.getList(list);
                    custom_product_viewPager.setTitle(nameCat);
                }
            });

        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }


    void GetList_CustomProduct_Porforosh(){

        Api_product.GetLsit_Product("product-sale",context, new Api_product.ListProduct() {
            @Override
            public void ListPost(List<Datamodel_ListProduct> listProductList) {
                customProduct=findViewById(R.id.custom_Product_porforosh);

                customProduct.ViewAndCast("product-sale");
                customProduct.getList(listProductList,1);
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

                customProductNew.ViewAndCast("product-new");
                customProductNew.getList(listProductList,1);
                customProductNew.setTitle("جدید ترین محصولات");
            }
        });
    }
}
