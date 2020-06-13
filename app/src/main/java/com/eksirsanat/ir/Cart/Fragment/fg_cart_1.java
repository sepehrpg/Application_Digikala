package com.eksirsanat.ir.Cart.Fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eksirsanat.ir.Panel_User.Panel.EditPanel.Act_Add_Address;
import com.eksirsanat.ir.Panel_User.Panel.EditPanel.Act_List_Address;
import com.eksirsanat.ir.Panel_User.Panel.EditPanel.Adapter_Rec_ListAddress;
import com.eksirsanat.ir.Panel_User.Panel.EditPanel.Api_Pack_And_Datamodel.Api_GetLIst_Address;
import com.eksirsanat.ir.Panel_User.Panel.EditPanel.Api_Pack_And_Datamodel.DataModel_List_Address;
import com.eksirsanat.ir.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class fg_cart_1 extends Fragment {

    View view;

    RecyclerView recyclerView;
    TextView txt_okAddress;

    Adapter_Rec_ListAddress address;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_fg_cart_1, container, false);
        Cast();
        Get_ListAddres();
        NeexStep();
        return view;
    }

    public void Cast(){
        recyclerView=view.findViewById(R.id.Rec_Address_Cart);
        txt_okAddress=view.findViewById(R.id.Txt_addAddresFinal);

    }

    public  void Get_ListAddres(){

        Api_GetLIst_Address.getList_Address(getContext(), new Api_GetLIst_Address.Get_List_Address() {
            @Override
            public void getListAddress(List<DataModel_List_Address> list) {

                if (list.size()<1 || list.isEmpty() ){
                    startActivity(new Intent(getContext(), Act_Add_Address.class));

                    ((Activity)getContext()).finish();
                }else {
                    address=new Adapter_Rec_ListAddress(getContext(),list);
                    RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(address);
                    address.notifyDataSetChanged();
                }
            }
        });


    }


    public void NeexStep(){
        txt_okAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences=getContext().getSharedPreferences("address", Context.MODE_PRIVATE);

                String idAddress=sharedPreferences.getString("idAddress","null");

                if (idAddress.equals("null")){
                    Toast.makeText(getContext(), "لطفا آدرسی را انتخاب کنید ", Toast.LENGTH_SHORT).show();
                }else {

                    FragmentManager fragmentManager=getFragmentManager();
                    FragmentTransaction transaction=fragmentManager.beginTransaction();
                    transaction.replace(R.id.BasefragmentMain,new fg_cart_2()).addToBackStack(null);
                    transaction.commit();

                }


            }
        });
    }




}
