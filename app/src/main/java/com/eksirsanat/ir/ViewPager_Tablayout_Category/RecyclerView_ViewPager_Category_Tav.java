package com.eksirsanat.ir.ViewPager_Tablayout_Category;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.eksirsanat.ir.R;
import com.eksirsanat.ir.ViewPager_Tablayout_Category.More_Category.Act_Cat_Home;

import java.util.List;

public class RecyclerView_ViewPager_Category_Tav extends RecyclerView.Adapter<RecyclerView_ViewPager_Category_Tav._Holder> {


    Context context;
    List<DataModel_Category_ViewPager_Tab> datamodel;
    public RecyclerView_ViewPager_Category_Tav(Context context,List<DataModel_Category_ViewPager_Tab> datamodel){

        this.context=context;
        this.datamodel=datamodel;
    }

    @NonNull
    @Override
    public _Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new _Holder(LayoutInflater.from(context).inflate(R.layout.item_viewpager_adapter_category,null,false));
    }

    @Override
    public void onBindViewHolder(@NonNull _Holder holder, int position) {

        final DataModel_Category_ViewPager_Tab model=datamodel.get(position);

        holder.textView.setText(model.getName());

        Glide.with(context).load(model.getIcon()).into(holder.imageView);

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Act_Cat_Home.class);
                intent.putExtra("nameCat",model.getName());
                intent.putExtra("idCat",model.getIdcat());
                intent.putExtra("subid",model.getSubid());
                intent.putExtra("nameEn",model.getNameEn());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return datamodel.size();
    }

    public class _Holder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        public _Holder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.Img_Category);
            textView=itemView.findViewById(R.id.Tv_Title_ViewpageTablayout);
        }
    }

}
