package com.eksirsanat.ir.Main_Home.Product.Filters_Product;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eksirsanat.ir.R;

import java.util.List;

public class Adapter_Filters extends RecyclerView.Adapter<Adapter_Filters._Holder> {
    Context context;
    List<Filters_DataModel> List;

    View.OnClickListener clickListener;






    public Adapter_Filters(Context context, List<Filters_DataModel> List,View.OnClickListener clickListener){

        this.context=context;
        this.List=List;
        this.clickListener=clickListener;

    }


    @NonNull
    @Override
    public _Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_list,null,false);
        return new _Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final _Holder holder, final int position) {
        Filters_DataModel filters=List.get(position);
        holder.nameFilter.setText(filters.getName());

        ContentValues contentValues=new ContentValues();
        contentValues.put("name",filters.getName());
        contentValues.put("nameEn",filters.getNameEn());
        contentValues.put("position",position);



        SharedPreferences sharedPreferences=context.getSharedPreferences("PositionFilter",0);
        if (position==sharedPreferences.getInt("pos",-10)){
            holder.nameFilter.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.nameFilter.setTextColor(context.getResources().getColor(R.color.meshkii));
        }
        else {
            holder.nameFilter.setBackgroundColor(context.getResources().getColor(R.color.black));
            holder.nameFilter.setTextColor(context.getResources().getColor(R.color.white));
        }



        holder.nameFilter.setTag(contentValues);
        holder.nameFilter.setOnClickListener(clickListener);

    }



    @Override
    public int getItemCount() {
        return List.size();
    }

    public  class _Holder extends RecyclerView.ViewHolder {

        TextView nameFilter;
        LinearLayout linearLayout;


        public _Holder(@NonNull View itemView) {
            super(itemView);
            nameFilter=itemView.findViewById(R.id.Txt_NameFilter);
            linearLayout=itemView.findViewById(R.id.LineBackItemFiilter);
        }
    }

}
