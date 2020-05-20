package com.eksirsanat.ir.Main_Home.Product.AShow_ListProduct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.eksirsanat.ir.Main_Home.Product.AShow_ListProduct.Adapter.Adapter_Expand_Server_1;
import com.eksirsanat.ir.Main_Home.Product.AShow_ListProduct.Api.Api_GetExpandList;
import com.eksirsanat.ir.R;
import com.eksirsanat.ir.ViewPager_Tablayout_Category.More_Category.HeadModel_Server;
import com.eksirsanat.ir.ViewPager_Tablayout_Category.More_Category.ItemModel_Server;

import java.util.HashMap;
import java.util.List;

public class Act_ShowFirs_ListCat extends AppCompatActivity {

    ExpandableListView expandableListView;
    Adapter_Expand_Server_1 adapter;

    String subid;

    List<HeadModel_Server> Header_Main;
    HashMap<String, List<ItemModel_Server>> ListItems_Main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__show_firs__list_cat);
        Cast();
        SetUpExpandRecView();
    }


    void Cast(){
        expandableListView=findViewById(R.id.Expand_ActHomeCat);
        subid=getIntent().getStringExtra("subid");
    }




  public void SetUpExpandRecView(){


       try {
            Api_GetExpandList.GetListCategory_2(Act_ShowFirs_ListCat.this, subid, new Api_GetExpandList.GetAllList() {
                @Override
                public void get_List(List<HeadModel_Server> Header, HashMap<String, List<ItemModel_Server>> ListItems) {
                    //Toast.makeText(Act_ShowFirs_ListCat.this, "ok", Toast.LENGTH_SHORT).show();
                    adapter=new Adapter_Expand_Server_1(Act_ShowFirs_ListCat.this,Header,ListItems);
                    expandableListView.setAdapter(adapter);
                    Header_Main=Header;
                    ListItems_Main=ListItems;
                }
            });

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


         expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                String idCat=ListItems_Main.get(Header_Main.get(groupPosition).getName()).get(childPosition).getId();
                String name=ListItems_Main.get(Header_Main.get(groupPosition).getName()).get(childPosition).getName();
                Intent intent=new Intent(Act_ShowFirs_ListCat.this, Act_ShowListProduct.class);
                intent.putExtra("idcat",idCat);
                intent.putExtra("name",name);
                startActivity(intent);
                //Toast.makeText(Act_ShowFirs_ListCat.this, idCat, Toast.LENGTH_SHORT).show();


                return false;
            }
        });



          expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                return false;
            }
        });






    }
}
