package com.eksirsanat.ir.Main_Home.Product.product_offer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eksirsanat.ir.Main_Home.Product.Adapter_RecyclerView_Product;
import com.eksirsanat.ir.Main_Home.Product.Datamodel_ListProduct;
import com.eksirsanat.ir.R;

import java.util.List;

public class Custom_Product_Offer extends RelativeLayout {

    RecyclerView recyclerView;

    Adapter_RecyclerView_Product_Offer adapterProduct;

    public Custom_Product_Offer(Context context) {
        super(context);
        GetListProduct();
    }

    public Custom_Product_Offer(Context context, AttributeSet attrs) {
        super(context, attrs);
        GetListProduct();

    }

    public Custom_Product_Offer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        GetListProduct();

    }

    public Custom_Product_Offer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        super(context, attrs, defStyleAttr, defStyleRes);
        GetListProduct();

    }

    public void GetListProduct(){

        View view= LayoutInflater.from(getContext()).inflate(R.layout.layout_offer,this,true); //this: means: be is hear And true:means:attach hear
        recyclerView=view.findViewById(R.id.recyclerview_product);

    }

    public void getList(List<Datamodel_ListProduct> listProductList){

        LinearLayoutManager manager=new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);

        adapterProduct=new Adapter_RecyclerView_Product_Offer(getContext(),listProductList);

        recyclerView.setAdapter(adapterProduct);
    }

}
