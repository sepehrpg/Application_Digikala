package com.eksirsanat.ir.Main_Home.Product;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eksirsanat.ir.R;

import java.util.List;

public class Custom_Product extends RelativeLayout { //Example Activity

    TextView Tv_title,Tv_product_all;
    RecyclerView recyclerView;
    int order;

    Adapter_RecyclerView_Product adapterProduct;

    public Custom_Product(Context context) {
        super(context);
        ViewAndCast(null);
    }

    public Custom_Product(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewAndCast(null);

    }

    public Custom_Product(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewAndCast(null);

    }

    public Custom_Product(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        super(context, attrs, defStyleAttr, defStyleRes); //this error for version android and not import
        ViewAndCast(null);

    }




    //We Define ....................................................................................

    public void ViewAndCast(final String nameCat){

        //View view=View.inflate(getContext(),R.layout.product_layout,this); //is true

        View view= LayoutInflater.from(getContext()).inflate(R.layout.product_layout,this,true); //this: means: be is hear And true:means:attach hear
        recyclerView=view.findViewById(R.id.recyclerview_product);
        Tv_title=view.findViewById(R.id.Tv_title);
        Tv_product_all=view.findViewById(R.id.Tv_product_all);


        Tv_product_all.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Act_SeeAll_For_newAndPrice.class);
                intent.putExtra("nameCat",nameCat);
                getContext().startActivity(intent);

            }
        });

    }

    public void getList(List<Datamodel_ListProduct> listProductList,int order){
        //LinearLayoutManager manager;

        this.order=order;

        if (order==2){
            RecyclerView.LayoutManager manager =new GridLayoutManager(getContext(),1);
            recyclerView.setLayoutManager(manager);


        }else {
            LinearLayoutManager manager=new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,true);
            manager.setStackFromEnd(true);
            recyclerView.setLayoutManager(manager);
        }

        adapterProduct=new Adapter_RecyclerView_Product(getContext(),listProductList,order);
        recyclerView.setAdapter(adapterProduct);


    }


    public void setTitle(String title){
        if (order==2){
            Tv_title.setText(title);

        }else {
            Tv_title.setText(title);
            Tv_product_all.setText("مشاهده همه");

        }

    }

}
