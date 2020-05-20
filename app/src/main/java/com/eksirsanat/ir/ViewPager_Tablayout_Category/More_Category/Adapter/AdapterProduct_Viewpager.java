package com.eksirsanat.ir.ViewPager_Tablayout_Category.More_Category.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eksirsanat.ir.Action.FormatNumber_Decimal;
import com.eksirsanat.ir.Main_Home.Product.AShow_ListProduct.All_ListProduct_Model;
import com.eksirsanat.ir.Main_Home.Product.Adapter_RecyclerView_Product;
import com.eksirsanat.ir.Main_Home.Product.Datamodel_ListProduct;
import com.eksirsanat.ir.More_Product.More_Product;
import com.eksirsanat.ir.R;

import java.util.List;

public class AdapterProduct_Viewpager extends RecyclerView.Adapter<AdapterProduct_Viewpager._Holder> {

    Context context;
    List<All_ListProduct_Model> datamodel_listProducts;

    public AdapterProduct_Viewpager(Context context,List<All_ListProduct_Model> datamodel_listProducts){
        this.context=context;
        this.datamodel_listProducts=datamodel_listProducts;
    }

    @NonNull
    @Override
    public AdapterProduct_Viewpager._Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.items_product,parent,false);
        return new AdapterProduct_Viewpager._Holder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProduct_Viewpager._Holder holder, int position) {

        try {
            final All_ListProduct_Model model=datamodel_listProducts.get(position);

            if (!model.getImg().equals("null")){
                Glide.with(context).load(model.getImg()).into(holder.imaage_post);
            }

           if (!model.getPrice().equals("null")){
                holder.Tv_price.setText(FormatNumber_Decimal.GetFormatInteger(model.getPrice())+"تومان");
                holder.Tv_price.setTextColor(context.getResources().getColor(R.color.sabzporrang));
            }
            else {
                holder.Tv_price.setText("نا موجود");
                holder.Tv_price.setTextColor(context.getResources().getColor(R.color.ghermez));
            }

            if (!model.getName().equals("null")){
                holder.Tv_title.setText(model.getName());
            }

            holder.CardViewProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, More_Product.class);
                    intent.putExtra("idproduct",model.getId());
                    intent.putExtra("offer","0");
                    context.startActivity(intent);

                }
            });

        }catch (Exception e){
            //IS A ERROR********************************************************
            //Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


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
