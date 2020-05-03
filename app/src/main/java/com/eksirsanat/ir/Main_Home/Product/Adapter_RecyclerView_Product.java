package com.eksirsanat.ir.Main_Home.Product;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eksirsanat.ir.Action.FormatNumber_Decimal;
import com.eksirsanat.ir.More_Product.More_Product;
import com.eksirsanat.ir.R;

import java.util.List;

public class Adapter_RecyclerView_Product extends RecyclerView.Adapter<Adapter_RecyclerView_Product._Holder> {

    Context context;
    List<Datamodel_ListProduct> datamodel_listProducts;

    public Adapter_RecyclerView_Product(Context context,List<Datamodel_ListProduct> datamodel_listProducts){
        this.context=context;
        this.datamodel_listProducts=datamodel_listProducts;
    }

    @NonNull
    @Override
    public _Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.items_product,parent,false);
        return new _Holder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull _Holder holder, int position) {

        final Datamodel_ListProduct datamodelListProduct=datamodel_listProducts.get(position);
        Glide.with(context).load(datamodelListProduct.getPic()).into(holder.imaage_post);

        holder.Tv_price.setText(FormatNumber_Decimal.GetFormatInteger(datamodelListProduct.getPrice_sale())+"تومان");
        holder.Tv_title.setText(datamodelListProduct.getName());

        holder.CardViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, More_Product.class);
                intent.putExtra("idproduct",datamodelListProduct.getIdproduct());
                intent.putExtra("offer","0");
                context.startActivity(intent);

            }
        });

        //Log.i("Tv_price",datamodelListProduct.getPrice_sale());
        //Log.i("Tv_title",datamodelListProduct.getName());

    }

    @Override
    public int getItemCount() {
        return datamodel_listProducts.size();
    }

    public class _Holder extends RecyclerView.ViewHolder {

        ImageView imaage_post;
        TextView Tv_title,Tv_price;

        CardView CardViewProduct;


        public _Holder(@NonNull View itemView) {
            super(itemView);
            imaage_post=itemView.findViewById(R.id.Image_post);
            Tv_title=itemView.findViewById(R.id.Tv_title2);
            Tv_price=itemView.findViewById(R.id.Tv_price);
            CardViewProduct=itemView.findViewById(R.id.CardViewProduct);
        }
    }
}
