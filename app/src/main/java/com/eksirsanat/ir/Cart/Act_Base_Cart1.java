package com.eksirsanat.ir.Cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.eksirsanat.ir.Cart.Fragment.fg_cart_1;
import com.eksirsanat.ir.R;

public class Act_Base_Cart1 extends AppCompatActivity {



    Fragment page_1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__base__cart1);
        Cast();
    }

    public void Cast(){
        SetUpFragment();
    }

    public void SetUpFragment(){

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.BasefragmentMain,new fg_cart_1());
        transaction.commit();


    }
}
