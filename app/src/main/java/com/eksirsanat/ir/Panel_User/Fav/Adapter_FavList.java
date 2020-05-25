package com.eksirsanat.ir.Panel_User.Fav;

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
import com.eksirsanat.ir.More_Product.More_Product;
import com.eksirsanat.ir.R;

import java.util.List;

public class Adapter_FavList extends RecyclerView.Adapter<Adapter_FavList._Holder> {
    Context context;
    List<FavList_Datamodel> products;

    View.OnClickListener click;

    public Adapter_FavList(Context context,List<FavList_Datamodel> products, View.OnClickListener click){
       this.context=context;
       this.products=products;
       this.click=click;
    }

    @NonNull
    @Override
    public _Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new _Holder(LayoutInflater.from(context).inflate(R.layout.item_list_fav,null,false));
    }

    @Override
    public void onBindViewHolder(@NonNull _Holder holder, int position) {

        final FavList_Datamodel model=products.get(position);
        holder.Header.setText(model.getName());
        holder.sum.setText(model.getSum());
        Glide.with(context).load(model.getPic()).into(holder.imageView);

        holder.delete.setTag(model);
        holder.delete.setOnClickListener(click);


        holder.Card_Click_Fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, More_Product.class);
                intent.putExtra("idproduct",model.getId());
                context.startActivity(intent);

            }
        });




    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class _Holder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView Header,delete,sum;

        CardView Card_Click_Fav;

        public _Holder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.Img_FavList);
            Header=itemView.findViewById(R.id.Txt_Header_FavList);
            delete=itemView.findViewById(R.id.Delete_FavList);
            sum=itemView.findViewById(R.id.Txt_Sum_FavList);
            Card_Click_Fav=itemView.findViewById(R.id.Card_Click_Fav);
        }
    }
}
