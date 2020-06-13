package com.eksirsanat.ir.Panel_User.Panel.EditPanel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eksirsanat.ir.Panel_User.Panel.Act_Main_Profile_User;
import com.eksirsanat.ir.Panel_User.Panel.EditPanel.Api_Pack_And_Datamodel.Api_GetLIst_Address;
import com.eksirsanat.ir.Panel_User.Panel.EditPanel.Api_Pack_And_Datamodel.DataModel_List_Address;
import com.eksirsanat.ir.R;

import java.util.List;

public class Act_List_Address extends AppCompatActivity {

    TextView textView;
    RecyclerView recyclerView;

    Adapter_Rec_ListAddress address;

    ImageView img_back,img_add_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__list__address);
        recyclerView=findViewById(R.id.Rec_ListAddress);
        img_back=findViewById(R.id.Img_back_Address);
        img_add_address=findViewById(R.id.Img_Add_Address);
        textView=findViewById(R.id.Txt_ListAddress_POINT);
        onClick();
        Get_ListAddres();

    }

    void onClick(){

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        img_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_List_Address.this, Act_Add_Address.class));
            }
        });


    }


    public  void Get_ListAddres(){

        Api_GetLIst_Address.getList_Address(Act_List_Address.this, new Api_GetLIst_Address.Get_List_Address() {
            @Override
            public void getListAddress(List<DataModel_List_Address> list) {

                if (list.size()<1 || list.isEmpty()){
                    textView.setVisibility(View.VISIBLE);

                }else {
                    address=new Adapter_Rec_ListAddress(Act_List_Address.this,list);
                    recyclerView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                    RecyclerView.LayoutManager manager=new GridLayoutManager(Act_List_Address.this,1,RecyclerView.VERTICAL,false);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(address);
                    address.notifyDataSetChanged();
                }





            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Get_ListAddres();

    }
}
