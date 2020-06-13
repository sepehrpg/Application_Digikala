package com.eksirsanat.ir.Cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eksirsanat.ir.Action.FormatNumber_Decimal;
import com.eksirsanat.ir.Action.Get_Token;
import com.eksirsanat.ir.Cart.Api_Adapter_Database.Adapter_BasketCart_First;
import com.eksirsanat.ir.Cart.Api_Adapter_Database.DataModel_DbProduct;
import com.eksirsanat.ir.Cart.Api_Adapter_Database.Db_CartFirst;
import com.eksirsanat.ir.Cart.Api_Adapter_Database.Api_AddCart;
import com.eksirsanat.ir.Panel_User.Act_LoginActivity;
import com.eksirsanat.ir.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Act_BasketCart extends AppCompatActivity {

    TextView txt_totalPrice,txt_ok,empyty_basket;
    RecyclerView recyclerView;
    ImageView back;

    CardView cardView;

    List<DataModel_DbProduct> list;
    List<String> CountSpiner;

    Adapter_BasketCart_First adapter;
    Db_CartFirst database;

    String idproduct;
    long TotalPrice;
    String TotalPrice_str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__basket_cart);
        if (Get_Token.getToken(this)==null || Get_Token.getToken(this).length()<10){
            startActivity(new Intent(this, Act_LoginActivity.class));
            finish();
        }else {
            Cast();
            setUpRecycler();
        }



    }

    public void Cast() {
        try {
            txt_totalPrice=findViewById(R.id.Txt_TotalPrice_FirstCart);
            txt_ok=findViewById(R.id.Txt_OkFirstCart);
            empyty_basket=findViewById(R.id.ShowBasketHide);
            cardView=findViewById(R.id.CardCart);
            recyclerView=findViewById(R.id.Rec_FirstCart);
            database=new Db_CartFirst(Act_BasketCart.this);
            back=findViewById(R.id.Close_Main_Toolbar);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
       list=new ArrayList<>();
        CountSpiner=new ArrayList<>();
        CountSpiner.add("1");
        CountSpiner.add("2");
        CountSpiner.add("3");
        CountSpiner.add("4");
        CountSpiner.add("5");
        CountSpiner.add("6");
        CountSpiner.add("7");
        CountSpiner.add("8");
        CountSpiner.add("9");
        CountSpiner.add("10");
            idproduct=String.valueOf(getIntent().getIntExtra("idproduct",0));
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        long TotalPrice=database.Final_price_product();
        TotalPrice_str=String.valueOf(TotalPrice);
        //txt_totalPrice.setText(FormatNumber_Decimal.GetFormatInteger(TotalPrice_str)+" تومان ");


    }

    public void setUpRecycler(){

        list=database.get_Info_Db();
        Collections.reverse(list);

        if (list.size()<1){
            empyty_basket.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.GONE);
            txt_ok.setVisibility(View.GONE);
        }else {
            cardView.setVisibility(View.VISIBLE);
            txt_ok.setVisibility(View.VISIBLE);

        }

        adapter=new Adapter_BasketCart_First(Act_BasketCart.this, list, CountSpiner, idproduct,true, new Adapter_BasketCart_First.ChangeSpiner() {
            @Override
            public void Change(int change) {
                txt_totalPrice.setText(FormatNumber_Decimal.GetFormatInteger(String.valueOf(change))+" تومان ");
            }

            @Override
            public void ListEmpyty(int empty) {

                if (empty==10){
                    empyty_basket.setVisibility(View.VISIBLE);
                    cardView.setVisibility(View.GONE);
                    txt_ok.setVisibility(View.GONE);
                }

            }
        });

        RecyclerView.LayoutManager manager=new GridLayoutManager(Act_BasketCart.this,1);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        GoTO_Next_Step();

    }



    boolean check=true;
    int pos;
    public void GoTO_Next_Step(){
        txt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               list=new ArrayList<>();
               list=database.get_Info_Db();

                if (list.size()<1){
                    return;
                }

                for (int i = 0; i <list.size() ; i++) {
                    String count=list.get(i).getNumber();
                    String idStore=list.get(i).getIdstore();

                    final int finalI = i;
                    Api_AddCart.Add_Cart(Act_BasketCart.this, count, idStore, new Api_AddCart.ResponseMessage() {
                        @Override
                        public void response(String message) {

                            if (message.equals("ok")){
                                check=true;


                            }else if (message.equals("خطا در افزودن به سبد خرید")){
                                check=false;
                                pos=finalI;
                            }

                        }
                    });

                    if (!check){
                      Toast.makeText(Act_BasketCart.this, " محصول "+list.get(pos).getTitlefa()+" دارای مشکل است ", Toast.LENGTH_SHORT).show();
                      break;
                    }

                }


                if (check){
                    startActivity(new Intent(Act_BasketCart.this,Act_Base_Cart1.class));
                    finish();

                }


            }
        });
    }




}
