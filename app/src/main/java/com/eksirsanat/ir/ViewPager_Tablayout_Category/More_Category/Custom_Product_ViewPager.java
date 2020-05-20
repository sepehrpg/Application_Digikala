package com.eksirsanat.ir.ViewPager_Tablayout_Category.More_Category;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eksirsanat.ir.Main_Home.Product.AShow_ListProduct.Act_ShowListProduct;
import com.eksirsanat.ir.Main_Home.Product.AShow_ListProduct.All_ListProduct_Model;
import com.eksirsanat.ir.Main_Home.Product.Adapter_RecyclerView_Product;
import com.eksirsanat.ir.Main_Home.Product.Datamodel_ListProduct;
import com.eksirsanat.ir.R;
import com.eksirsanat.ir.ViewPager_Tablayout_Category.More_Category.Adapter.AdapterProduct_Viewpager;

import java.util.List;

public class Custom_Product_ViewPager extends RelativeLayout { //Example Activity

    TextView Tv_title,Tv_product_all;
    RecyclerView recyclerView;

    AdapterProduct_Viewpager adapterProduct;

    public Custom_Product_ViewPager(Context context) {
        super(context);
        ViewAndCast(null,null);
    }

    public Custom_Product_ViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewAndCast(null,null);

    }

    public Custom_Product_ViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewAndCast(null,null);

    }

    public Custom_Product_ViewPager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        super(context, attrs, defStyleAttr, defStyleRes); //this error for version android and not import
        ViewAndCast(null,null);

    }




    //We Define ....................................................................................

    public void ViewAndCast(final String idCat, final String nameCat){

        //View view=View.inflate(getContext(),R.layout.product_layout,this); //is true

        View view= LayoutInflater.from(getContext()).inflate(R.layout.product_layout,this,true); //this: means: be is hear And true:means:attach hear
        recyclerView=view.findViewById(R.id.recyclerview_product);
        Tv_title=view.findViewById(R.id.Tv_title);
        Tv_product_all=view.findViewById(R.id.Tv_product_all);

        Tv_product_all.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Act_ShowListProduct.class);
                intent.putExtra("idcat",idCat);
                intent.putExtra("name",nameCat);
                getContext().startActivity(intent);
            }
        });
    }

    public void getList(List<All_ListProduct_Model> listProductList){

        LinearLayoutManager manager=new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);

        adapterProduct=new AdapterProduct_Viewpager(getContext(),listProductList);

        recyclerView.setAdapter(adapterProduct);
    }


    public void setTitle(String title){
        Tv_title.setText(title);
        Tv_product_all.setText("مشاهده همه");
    }

}
