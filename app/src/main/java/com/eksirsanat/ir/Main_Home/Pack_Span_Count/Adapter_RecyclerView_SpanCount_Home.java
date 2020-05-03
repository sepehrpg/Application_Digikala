package com.eksirsanat.ir.Main_Home.Pack_Span_Count;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eksirsanat.ir.Main_Home.Category_Main.Datamodel_Category_Home;
import com.eksirsanat.ir.R;

import java.util.List;

public class Adapter_RecyclerView_SpanCount_Home extends RecyclerView.Adapter<Adapter_RecyclerView_SpanCount_Home._Holder> {

    Context context;
    List<Datamodel_SpanCount> datamodelSpanCounts;

    public final static int VIEW_TYPE_HEADER=0; //for show header
    public final static int VIEW_TYPE_SPANCOUNT=1; //for show spanCount

    public Adapter_RecyclerView_SpanCount_Home(Context context, List<Datamodel_SpanCount> datamodelSpanCounts){
        this.context=context;
        this.datamodelSpanCounts=datamodelSpanCounts;
    }



    @NonNull
    @Override
    public _Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  //this page adapter we just presentation layout for view


        //return new _Holder(LayoutInflater.from(context).inflate(R.layout.type_header,parent,false));//is true

         switch (viewType){

             case VIEW_TYPE_HEADER: //if viewType=0
                 return new _Holder(LayoutInflater.from(context).inflate(R.layout.type_header,parent,false));

             case  VIEW_TYPE_SPANCOUNT: //if viewType=1
               return new _Holder(LayoutInflater.from(context).inflate(R.layout.type_span,parent,false));

               default:
                   return null;
         }
    }

    @Override
    public void onBindViewHolder(@NonNull _Holder holder, int position) {

        final Datamodel_SpanCount datamodelSpanCount=datamodelSpanCounts.get(position);

        Glide.with(context).load(datamodelSpanCount.getPic()).into(holder.Image_spanCount);


    }


    @Override
    public int getItemCount() {
        return datamodelSpanCounts.size();
    }



    public class _Holder extends RecyclerView.ViewHolder {
        ImageView Image_spanCount;

        public _Holder(@NonNull View itemView) {
            super(itemView);
            Image_spanCount=itemView.findViewById(R.id.Img_Span_Count);
        }
    }




    @Override
    public int getItemViewType(int position) { //this override get back to us one position until work on this

        //position get when user scrolling (first 0 and then 1,2,3,....)

        if (position==0)
            return 0;

       /* else if (position==3){ //is true
            return 0;
        }*/
        else {
            return 1; //this return use in viewType of onCreateViewHolder
        }
    }




}
