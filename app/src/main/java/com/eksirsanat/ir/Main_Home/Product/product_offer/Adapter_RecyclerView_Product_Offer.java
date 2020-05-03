package com.eksirsanat.ir.Main_Home.Product.product_offer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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
import com.eksirsanat.ir.Main_Home.Product.Datamodel_ListProduct;
import com.eksirsanat.ir.More_Product.More_Product;
import com.eksirsanat.ir.R;

import java.util.List;

public class Adapter_RecyclerView_Product_Offer extends RecyclerView.Adapter<Adapter_RecyclerView_Product_Offer._Holder> {

    Context context;
    List<Datamodel_ListProduct> datamodel_listProducts;



    public Adapter_RecyclerView_Product_Offer(Context context, List<Datamodel_ListProduct> datamodel_listProducts){
        this.context=context;
        this.datamodel_listProducts=datamodel_listProducts;
    }

    @NonNull
    @Override
    public _Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.items_product_offer,parent,false);

        return new _Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull _Holder holder, int position) {

        final Datamodel_ListProduct datamodelListProduct=datamodel_listProducts.get(position);
        Glide.with(context).load(datamodelListProduct.getPic()).into(holder.imaage_post);

        holder.Tv_price.setText(FormatNumber_Decimal.GetFormatInteger(datamodelListProduct.getPrice_sale())+"تومان");
        holder.Tv_price_offer.setText(FormatNumber_Decimal.GetFormatInteger(datamodelListProduct.getOffer())+"تومان");
        holder.Tv_title.setText(datamodelListProduct.getName());

        holder.Tv_price.setPaintFlags(holder.Tv_price.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG); //line on price when exist price offer

        holder.CardViewProductOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, More_Product.class);
                intent.putExtra("idproduct",datamodelListProduct.getIdproduct());

                if (datamodelListProduct.getOffer()==null){
                    intent.putExtra("offer","0");
                }

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
        TextView Tv_title,Tv_price,Tv_price_offer;
        CardView CardViewProductOffer;


        public _Holder(@NonNull View itemView) {
            super(itemView);
            imaage_post=itemView.findViewById(R.id.Image_post);
            Tv_title=itemView.findViewById(R.id.Tv_title2);
            Tv_price=itemView.findViewById(R.id.Tv_price);
            Tv_price_offer=itemView.findViewById(R.id.Tv_price_offer);
            CardViewProductOffer=itemView.findViewById(R.id.CardViewProductOffer);

        }
    }
}
