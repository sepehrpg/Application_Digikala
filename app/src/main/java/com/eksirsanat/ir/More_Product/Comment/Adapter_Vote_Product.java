package com.eksirsanat.ir.More_Product.Comment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eksirsanat.ir.Action.Class_PercentDrawable;
import com.eksirsanat.ir.More_Product.Comment.ApiAndDataModel_Vote.DataModel_GetVote;
import com.eksirsanat.ir.R;

import java.util.List;

public class Adapter_Vote_Product extends RecyclerView.Adapter<Adapter_Vote_Product._Holder> {
    Context context;
    List<DataModel_GetVote> list;
    List<Float> listStar;

    public  Adapter_Vote_Product(Context context, List<DataModel_GetVote> list,List<Float> listStar){
        this.context=context;
        this.list=list;
        this.listStar=listStar;
    }


    @NonNull
    @Override
    public _Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_getvote_star,null,false);
        return new _Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull _Holder holder, int position) {
        DataModel_GetVote model=list.get(position);
        holder.txt_name.setText(model.getName());
        holder.v1.setRotation(180);
        holder.v2.setRotation(180);
        holder.v3.setRotation(180);
        holder.v4.setRotation(180);
        holder.v5.setRotation(180);


        if (listStar!=null && list.size()==listStar.size()){
            String getStar=String.valueOf(listStar.get(position));
           try {
               int  S1=Integer.parseInt(getStar.substring(0,1));
               int  S2;
               if (getStar.length()>1){
                     S2=Integer.parseInt(getStar.substring(2,3));
                   Log.i("VoteeeeVal",S1+"   "+S2);
               }else {
                     S2=0;
               }

               switch (S1){

                   case 1:
                       holder.v1.setBackgroundColor(context.getResources().getColor(R.color.sabzporrang));
                       break;

                   case 2:
                       holder.v1.setBackgroundColor(context.getResources().getColor(R.color.sabzporrang));
                       holder.v2.setBackgroundColor(context.getResources().getColor(R.color.sabzporrang));
                       break;


                   case 3:
                       holder.v1.setBackgroundColor(context.getResources().getColor(R.color.sabzporrang));
                       holder.v2.setBackgroundColor(context.getResources().getColor(R.color.sabzporrang));
                       holder.v3.setBackgroundColor(context.getResources().getColor(R.color.sabzporrang));

                       break;


                   case 4:
                       holder.v1.setBackgroundColor(context.getResources().getColor(R.color.sabzporrang));
                       holder.v2.setBackgroundColor(context.getResources().getColor(R.color.sabzporrang));
                       holder.v3.setBackgroundColor(context.getResources().getColor(R.color.sabzporrang));
                       holder.v4.setBackgroundColor(context.getResources().getColor(R.color.sabzporrang));
                       break;


                   case 5:
                       holder.v1.setBackgroundColor(context.getResources().getColor(R.color.sabzporrang));
                       holder.v2.setBackgroundColor(context.getResources().getColor(R.color.sabzporrang));
                       holder.v3.setBackgroundColor(context.getResources().getColor(R.color.sabzporrang));
                       holder.v4.setBackgroundColor(context.getResources().getColor(R.color.sabzporrang));
                       holder.v5.setBackgroundColor(context.getResources().getColor(R.color.sabzporrang));
                       break;
               }

               switch (S1){
                   case 0:
                   break;

                   case 1:

                       if (S2!=0){
                           holder.v2.setBackground(new Class_PercentDrawable(S2*10,context));
                       }

                       break;

                   case 2:
                       if (S2!=0){
                           holder.v3.setBackground(new Class_PercentDrawable(S2*10,context));
                       }
                       break;


                   case 3:

                       if (S2!=0){
                           holder.v4.setBackground(new Class_PercentDrawable(S2*10,context));
                       }
                       break;


                   case 4:
                       if (S2!=0){
                           holder.v5.setBackground(new Class_PercentDrawable(S2*10,context));
                       }
                       break;


                   case 5:
                       break;

               }


           }catch (Exception e){
               Toast.makeText(context, "مشکل در دریافت امتیازات ", Toast.LENGTH_SHORT).show();
           }

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class _Holder extends RecyclerView.ViewHolder {

        View v1,v2,v3,v4,v5;
        TextView txt_name;

        public _Holder(@NonNull View itemView) {
            super(itemView);
            v1=itemView.findViewById(R.id.V1);
            v2=itemView.findViewById(R.id.V2);
            v3=itemView.findViewById(R.id.V3);
            v4=itemView.findViewById(R.id.V4);
            v5=itemView.findViewById(R.id.V5);
            txt_name=itemView.findViewById(R.id.Txt_TitleVote);
        }
    }
}
