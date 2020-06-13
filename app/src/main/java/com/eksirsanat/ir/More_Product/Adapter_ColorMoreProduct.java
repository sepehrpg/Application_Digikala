package com.eksirsanat.ir.More_Product;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.eksirsanat.ir.R;

import java.util.List;

public class Adapter_ColorMoreProduct extends RecyclerView.Adapter<Adapter_ColorMoreProduct._Holder> {

    List<ModelColor_Moreproduct> colorList;
    Context context;

    boolean posOne;

    public Adapter_ColorMoreProduct(Context context,List<ModelColor_Moreproduct> colorList){
        posOne=false;
        this.context=context;
        this.colorList=colorList;
    }


    @NonNull
    @Override
    public _Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View V=LayoutInflater.from(context).inflate(R.layout.item_color_more_product,null,false);
        return new _Holder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull final _Holder holder, final int position) {
        final ModelColor_Moreproduct model=colorList.get(position);

        SharedPreferences sharedPreferences=context.getSharedPreferences("SelectColor",0);
        int getPos=sharedPreferences.getInt("posColor",-10);

        if (getPos==-10){
            if (position==0){
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("posColor",position);
                editor.putString("idColor",model.getId());
                editor.putString("valueColor",model.getValue());
                editor.putString("nameColor",model.getName());
                editor.apply();
                GradientDrawable border=new GradientDrawable();
                border.setColor(Color.parseColor("#eeeeee"));
                if (model.getValue().equals("#FCF9F9")){
                    border.setStroke(2,context.getResources().getColor(R.color.meshkii));
                }else {
                    border.setStroke(2,Color.parseColor(model.getValue()));
                }
                border.setSize(100,100);
                holder.cardView.setBackground(border);
            }else {
                GradientDrawable border=new GradientDrawable();
                border.setColor(context.getResources().getColor(R.color.sefidd));
                border.setSize(100,100);
                holder.cardView.setBackground(border);
            }
        }
        else if (position==sharedPreferences.getInt("posColor",-10)){
            GradientDrawable border=new GradientDrawable();
            border.setColor(Color.parseColor("#eeeeee"));
            if (model.getValue().equals("#FCF9F9")){
                border.setStroke(2,context.getResources().getColor(R.color.meshkii));
            }else {
                border.setStroke(2,Color.parseColor(model.getValue()));
            }
            border.setSize(100,100);
            holder.cardView.setBackground(border);
        }
        else {
            GradientDrawable border=new GradientDrawable();
            border.setColor(context.getResources().getColor(R.color.sefidd));
            border.setSize(100,100);
            holder.cardView.setBackground(border);
        }


        holder.textView.setText(model.getName());

        GradientDrawable gradientDrawable=new GradientDrawable();
        gradientDrawable.setCornerRadius(100);
        gradientDrawable.setStroke(2,context.getResources().getColor(R.color.meshkii));
        gradientDrawable.setColor(Color.parseColor(model.getValue()));
        holder.view.setBackground(gradientDrawable);



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                GradientDrawable border=new GradientDrawable();
                border.setColor(Color.parseColor("#eeeeee"));

                if (model.getValue().equals("#FCF9F9")){
                    border.setStroke(2,context.getResources().getColor(R.color.meshkii));
                }else {
                    border.setStroke(2,Color.parseColor(model.getValue()));
                }
                posOne=true;
                border.setSize(100,100);
                holder.cardView.setBackground(border);

                SharedPreferences sharedPreferences=context.getSharedPreferences("SelectColor",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("posColor",position);
                editor.putString("idColor",model.getId());
                editor.putString("valueColor",model.getValue());
                editor.putString("nameColor",model.getName());
                editor.apply();
                notifyDataSetChanged();
            }
        });







    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    public class _Holder extends RecyclerView.ViewHolder {

        TextView textView;
        View view;
        CardView cardView;

        public _Holder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.Txt_Item_Color);
            view=itemView.findViewById(R.id.View_ColorProduct);
            cardView=itemView.findViewById(R.id.CardView_Moreproduct_Color);
        }
    }
}
