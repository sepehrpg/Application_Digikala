package com.eksirsanat.ir.ViewPager_Tablayout_Category;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eksirsanat.ir.R;

import java.util.List;

public class AdapterViewpager_TabCategory extends PagerAdapter { //use for image and different with fragment pager adapter
    Context context;
    List<String> strings;

    RecyclerView recyclerView;

    RecyclerView_ViewPager_Category_Tav adapter;

    public AdapterViewpager_TabCategory( Context context,List<String> strings){
        this.context=context;
        this.strings=strings;
    }


    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) { //get object from instantiateItem
        return view==object ; //check if  use not () work or no ??
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) { //for tablayout
        return strings.get(position);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) { //presentation view and fill image in this view and send to container



        final View view= LayoutInflater.from(context).inflate(R.layout.recyclerview_category_tablayout,null);

        Api_Category_ViewPager_Tablayout.GetCat(context, position, new Api_Category_ViewPager_Tablayout.GetCatInterface() {
            @Override
            public void ListCat(List<DataModel_Category_ViewPager_Tab> list) {
                recyclerView=view.findViewById(R.id.RecView_Category_Tablayout);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                adapter=new RecyclerView_ViewPager_Category_Tav(context,list);

                recyclerView.setRotationY(180); //for rtl viewpager
                recyclerView.setAdapter(adapter);
            }
        });



        container.addView(view);

        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) { //for delete storage user when item not selected
        ((ViewPager)container).removeView((View)object); //new
    }

}
