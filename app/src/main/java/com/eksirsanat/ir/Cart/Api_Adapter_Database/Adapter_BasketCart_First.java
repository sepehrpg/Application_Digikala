package com.eksirsanat.ir.Cart.Api_Adapter_Database;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eksirsanat.ir.Action.FormatNumber_Decimal;
import com.eksirsanat.ir.More_Product.More_Product;
import com.eksirsanat.ir.R;

import java.util.List;

public class Adapter_BasketCart_First extends RecyclerView.Adapter<Adapter_BasketCart_First._Holder> {

    List<DataModel_DbProduct> list;
    List<String> CountSpiner;

    Context context;
    Db_CartFirst database;
    String idProduct;
    ChangeSpiner changeSpiner;
    boolean hide;


    public Adapter_BasketCart_First(Context context,List<DataModel_DbProduct> list,List<String> CountSpiner,String idProduct,boolean hide,ChangeSpiner changeSpiner){
        this.context=context;
        this.list=list;
        this.CountSpiner=CountSpiner;
        this.idProduct=idProduct;
        this.changeSpiner=changeSpiner;
        this.hide=hide;
        database=new Db_CartFirst(context);

    }

    @NonNull
    @Override
    public _Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.item_first_cart,null,false);
        return new _Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final _Holder holder, final int position) {



        final DataModel_DbProduct model=list.get(position);
        holder.name.setText(model.getTitlefa());


        if (model.getColor()!=null){
            holder.txt_nameColor.setText(" رنگ "+model.getColor());
        }

        if (model.getShop()!=null){
            holder.sum.setText(model.getShop());
        }



        Glide.with(context).load(model.getImage()).into(holder.imageView);

        if (model.getOffer_price()!=null){
            holder.priceOffer.setText(FormatNumber_Decimal.GetFormatInteger(model.getOffer_price())+" تومان ");
            holder.pricePro.setText(FormatNumber_Decimal.GetFormatInteger(model.getPrice())+" تومان ");
            holder.pricePro.setTextColor(context.getResources().getColor(R.color.ghermez));
            holder.pricePro.setPaintFlags(holder.pricePro.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);


        }else {
            holder.pricePro.setTextColor(context.getResources().getColor(R.color.sabzporrang));
            holder.pricePro.setText(FormatNumber_Decimal.GetFormatInteger(model.getPrice())+" تومان ");
        }

        holder.priceTotal.setText(FormatNumber_Decimal.GetFormatInteger((model.getFinal_price()))+" تومان ");


        if (!hide){
            holder.delete.setVisibility(View.GONE);
            holder.LinePrice.setVisibility(View.GONE);

            ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,CountSpiner);
            holder.spinner.setAdapter(adapter);
            long number=database.Number_Count(model.getIdproduct());

            holder.spinner.setSelection(Integer.parseInt(String.valueOf(number))-1);
            holder.spinner.setEnabled(false);

        }else {
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,CountSpiner);
            holder.spinner.setAdapter(adapter);
            holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                    database.Update_AllPrice_AndNumber(model.getIdproduct(),Integer.parseInt(CountSpiner.get(pos)));
                    holder.priceTotal.setText(FormatNumber_Decimal.GetFormatInteger(String.valueOf(database.Final_Price(model.getIdproduct())))+" تومان ");
                    changeSpiner.Change(Integer.parseInt(String.valueOf(database.Final_price_product())));

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            long number=database.Number_Count(model.getIdproduct());
            holder.spinner.setSelection(Integer.parseInt(String.valueOf(number))-1);

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder alertdialog=new AlertDialog.Builder(context,R.style.AlertDialogCustom);

                    alertdialog.setTitle("حذف از  سبد خرید");
                    alertdialog.setMessage("محصول از سبد خرید حذف شود ؟");
                    alertdialog.setPositiveButton("بله ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Api_AddCart.DeleteCart(context, model.getIdstore(), new Api_AddCart.ResponseMessage() {
                                @Override
                                public void response(String message) {
                                    if (message.equals("ok")){
                                        database.Delete_Product(model.getIdproduct());
                                        list.remove(position);

                                        notifyDataSetChanged();
                                        if (list.size()==0){
                                            changeSpiner.ListEmpyty(10);
                                        }
                                    }else {
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });



                        }
                    });
                    alertdialog.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertdialog.show();


                }
            });

        }




        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, More_Product.class);
                intent.putExtra("idproduct",model.getIdproduct());
                intent.putExtra("price",model.getPrice());
                intent.putExtra("price_offer",model.getOffer_price());
                ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)context,holder.imageView,"image_intent");
                context.startActivity(intent,optionsCompat.toBundle());
            }
        });





    }

    public interface ChangeSpiner{
        void Change(int change);
        void ListEmpyty(int empty);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class _Holder extends RecyclerView.ViewHolder {

        TextView name,sum,pricePro,priceOffer,priceTotal,txt_nameColor;
        ImageView delete;
        ImageView imageView;
        Spinner spinner;

        LinearLayout linearLayout;
        LinearLayout LinePrice;

        public _Holder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.Txt_item_nameProductF);
            sum=itemView.findViewById(R.id.Txt_item_sumProductF);
            delete=itemView.findViewById(R.id.Txt_Item_DeleteF);
            pricePro=itemView.findViewById(R.id.Txt_item_PriceProductF);
            priceOffer=itemView.findViewById(R.id.Txt_item_PriceOfferProductF);
            txt_nameColor=itemView.findViewById(R.id.Txt_ColorItem);
            priceTotal=itemView.findViewById(R.id.Txt_item_PriceTotleProductF);
            linearLayout=itemView.findViewById(R.id.LineForHideCount);
            LinePrice=itemView.findViewById(R.id.LinePrice);
            imageView=itemView.findViewById(R.id.Img_Item_productF);
            spinner=itemView.findViewById(R.id.Sp_Item_countProductF);
        }
    }
}
