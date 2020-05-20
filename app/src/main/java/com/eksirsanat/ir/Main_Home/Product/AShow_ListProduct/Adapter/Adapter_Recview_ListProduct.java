package com.eksirsanat.ir.Main_Home.Product.AShow_ListProduct.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eksirsanat.ir.Action.FormatNumber_Decimal;
import com.eksirsanat.ir.Main_Home.Product.AShow_ListProduct.All_ListProduct_Model;
import com.eksirsanat.ir.More_Product.More_Product;
import com.eksirsanat.ir.R;

import java.util.List;

public class Adapter_Recview_ListProduct extends RecyclerView.Adapter<Adapter_Recview_ListProduct._holder> {

    List<All_ListProduct_Model> list;
    Context context;
    int order;
    public Adapter_Recview_ListProduct(Context context,int order, List<All_ListProduct_Model> list){

        this.context=context;
        this.list=list;
        this.order=order;
    }

    @NonNull
    @Override
    public _holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (order==2) {
            view = LayoutInflater.from(context).inflate(R.layout.item_all_list_product_grid,null,false);
        }

        else {
          view= LayoutInflater.from(context).inflate(R.layout.item_all_list_product,null,false);
        }

        return new _holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull _holder holder, int position) {

        final All_ListProduct_Model model=list.get(position);
        holder.txt_name.setText(model.getName());
        holder.txt_nameEn.setText(model.getNameEn());

       if (!model.getPrice().equals("null")){
            try {
                holder.txt_price.setText(FormatNumber_Decimal.GetFormatInteger(model.getPrice())+" تومان ");
                holder.txt_price.setTextColor(context.getResources().getColor(R.color.sabzporrang));

            }catch (Exception e){
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else {
           holder.txt_price.setText("نا موجود");
           holder.txt_price.setTextColor(context.getResources().getColor(R.color.ghermez));
       }

        if (model.getImg()!=null){
            Glide.with(context).load(model.getImg()).into(holder.img);
        }

        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, More_Product.class);
                intent.putExtra("idproduct",model.getId());
                intent.putExtra("offer","0");
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class _holder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView txt_price,txt_name,txt_nameEn;
        LinearLayout line;

        public _holder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.Img_FavList);
            txt_price=itemView.findViewById(R.id.Txt_ListProductPrice);
            txt_nameEn=itemView.findViewById(R.id.Txt_ListProductEn);
            txt_name=itemView.findViewById(R.id.Txt_ListProductPe);
            line=itemView.findViewById(R.id.ClickProductShow);
        }
    }

}
