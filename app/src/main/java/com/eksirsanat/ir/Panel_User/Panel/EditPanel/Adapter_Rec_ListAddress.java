package com.eksirsanat.ir.Panel_User.Panel.EditPanel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.eksirsanat.ir.Panel_User.Panel.EditPanel.Api_Pack_And_Datamodel.DataModel_List_Address;
import com.eksirsanat.ir.R;

import java.util.List;

public class Adapter_Rec_ListAddress extends RecyclerView.Adapter<Adapter_Rec_ListAddress._Holder> {

    Context context;
    List<DataModel_List_Address> list;

    public Adapter_Rec_ListAddress(Context context,List<DataModel_List_Address> list){
        this.context=context;
        this.list=list;
    }



    @NonNull
    @Override
    public _Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new _Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list_address,null,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final _Holder holder, final int position) {

        final DataModel_List_Address model=list.get(position);
        holder.name.setText(model.getName());
        holder.phone.setText(model.getPhone());
        holder.mobile.setText(model.getMobile());
        holder.address.setText(model.getAddress());
        holder.codeposti.setText(model.getCodeposti());



        if (list.size()==1){
            SharedPreferences sharedPreferences=context.getSharedPreferences("address",0);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putInt("position",position);
            editor.apply();
            holder.select.setText("انتخاب شده");
            holder.select.setTextColor(context.getResources().getColor(R.color.ghermez));
        }

        else {
            SharedPreferences sharedPreferences=context.getSharedPreferences("address",0);

            if (position==sharedPreferences.getInt("position",-10)){
                holder.select.setText("انتخاب شده");
                holder.select.setTextColor(context.getResources().getColor(R.color.ghermez));
            }
            else {
                holder.select.setText("انتخاب آدرس");
                holder.select.setTextColor(context.getResources().getColor(R.color.meshkii));
            }
            holder.select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder alertdialog=new AlertDialog.Builder(context,R.style.AlertDialogCustom);

                    alertdialog.setTitle("انتخاب آدرس");
                    alertdialog.setMessage("آیا از انتخاب شدن این آدرس برای خرید مطمعن هستید؟");
                    alertdialog.setPositiveButton("بله ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences sharedPreferences=context.getSharedPreferences("address",0);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putInt("position",position);
                            editor.apply();
                            holder.select.setText("انتخاب شده");
                            holder.select.setTextColor(context.getResources().getColor(R.color.ghermez));

                            notifyDataSetChanged();
                        }
                    });

                    alertdialog.setNegativeButton("لغو عملیات", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertdialog.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class _Holder extends RecyclerView.ViewHolder {

        TextView name,select,phone,mobile,address,codeposti;

        public _Holder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.Txt_NameAddressList);
            select=itemView.findViewById(R.id.Txt_SelectAddressList);
            phone=itemView.findViewById(R.id.Txt_PhoneAddressList);
            mobile=itemView.findViewById(R.id.Txt_MobilAddressList);
            address=itemView.findViewById(R.id.Txt_AddressMain);
            codeposti=itemView.findViewById(R.id.Txt_cityAddressList);
        }
    }
}
