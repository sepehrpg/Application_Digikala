package com.eksirsanat.ir.More_Product.Images;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eksirsanat.ir.R;

import java.util.List;

public class Slider_PageAdapter_Product extends PagerAdapter { //use for image and different with fragment pager adapter
    Context context;
    List<String> strings;
    ImageView image_slider;


    public Slider_PageAdapter_Product( Context context, List<String> strings  ){
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


    // azinja be bad khodemon neveshtim
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) { //presentation view and fill image in this view and send to container

        //this is one manager that put all view in container and then return view to one object

        View view= LayoutInflater.from(context).inflate(R.layout.slider_layout_product,null);
        image_slider=view.findViewById(R.id.Img_Slider_Home);

        Glide.with(context).load(strings.get(position))
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.logo_sp)
                        .error(R.drawable.ic_launcher_background))
                        .into(image_slider);

        container.addView(view); //very very import // put all view

        return view; //this return view but container exist in this view too
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) { //for delete storage user when item not selected
        ((ViewPager)container).removeView((View)object); //new
    }

}
