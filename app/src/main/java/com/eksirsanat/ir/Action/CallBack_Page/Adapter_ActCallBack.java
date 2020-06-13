package com.eksirsanat.ir.Action.CallBack_Page;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eksirsanat.ir.Action.DateConverter;
import com.eksirsanat.ir.Action.FormatNumber_Decimal;
import com.eksirsanat.ir.R;

import java.util.ArrayList;

public class Adapter_ActCallBack extends RecyclerView.Adapter<Adapter_ActCallBack._Holder> {

    ArrayList<ContentValues> list;
    Context context;

    public Adapter_ActCallBack(Context context,ArrayList<ContentValues> list){
        this.context=context;
        this.list=list;
    }


    @NonNull
    @Override
    public _Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.item_user_orderfinish,null,false);
        return new _Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull _Holder holder, int position) {
      ContentValues contentValues=(ContentValues)list.get(position);

        holder.code.setText(contentValues.getAsString("code"));
        holder.price.setText(FormatNumber_Decimal.GetFormatInteger(contentValues.getAsString("price_total")));


        DateConverter dateConverter=new DateConverter();
        String getDate=contentValues.getAsString("datepay").substring(0,10);

        dateConverter.gregorianToPersian(Integer.parseInt(getDate.substring(0,4)), Integer.parseInt(getDate.substring(5,7)), Integer.parseInt(getDate.substring(8,10)));
        String year=String.valueOf(dateConverter.getYear());
        String mounth=String.valueOf(dateConverter.getMonth());
        String day=String.valueOf(dateConverter.getDay());
        holder.date.setText(year+"/"+mounth+"/"+day);


        holder.status.setText(contentValues.getAsString("status"));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class _Holder extends RecyclerView.ViewHolder {

        TextView code,price,date,status;

        public _Holder(@NonNull View itemView) {
            super(itemView);
            status=itemView.findViewById(R.id.StatusItem);
            price=itemView.findViewById(R.id.ItemPrice);
            date=itemView.findViewById(R.id.ItemDate);
            code=itemView.findViewById(R.id.itemCode);
        }
    }
}
