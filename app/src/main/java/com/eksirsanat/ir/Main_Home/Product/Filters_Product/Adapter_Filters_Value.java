package com.eksirsanat.ir.Main_Home.Product.Filters_Product;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eksirsanat.ir.Action.FormatNumber_Decimal;
import com.eksirsanat.ir.R;


import java.util.ArrayList;
import java.util.List;

public class Adapter_Filters_Value extends RecyclerView.Adapter<Adapter_Filters_Value._Holder> {
    Context context;
    List<FilterModel_ColorAndBrand> list;
    List<String> property;

    ArrayList<String> ListFromPrice=new ArrayList<>();
    ArrayList<String> ListToPrice=new ArrayList<>();

    GetDataFromFilter getDataFromFilter;

    String from;
    String to;

    public Adapter_Filters_Value(Context context, List<FilterModel_ColorAndBrand> list,List<String> property,GetDataFromFilter getDataFromFilter){
        this.context=context;
        this.list=list;
        this.property=property;
        this.getDataFromFilter=getDataFromFilter;

    }

    @NonNull
    @Override
    public _Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType==0){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_price,null,false);
        }
        else if (viewType==2){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_color,null,false);
        }
        else {
             view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_filters,null,false);
        }
        return new Adapter_Filters_Value._Holder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull final _Holder holder, final int position) {
        final FilterModel_ColorAndBrand model=list.get(position);

        //Price
        if (model.getFrom_Price()!=null){
            PriceFilter(holder,model);
        }

        //Property
        else if (model.getProperty()!=null){
            PropertyFilter(holder,model,position);
        }

        //Colors
        else if (model.getColor()!=null){
            ColorsFilter(holder,model,position);
        }

        //Brand
        else {
            BrandFilter(holder,model,position);
        }

    }

    public interface GetDataFromFilter{
        void get_Price(String from,String to);
    }






    @Override
    public int getItemViewType(int position) {
        FilterModel_ColorAndBrand model=list.get(position);
        if (model.getFrom_Price()!=null){
            return 0;
        }
        else if (model.getColor()!=null){
            return 2;
        }

        else {
            return 1;
        }
    }



    @Override
    public int getItemCount() {
        return list.size();
    }





    public  class _Holder extends RecyclerView.ViewHolder {

        TextView Txt_textView,txt_color;
        View view;
        CheckBox textView,color;
        Spinner sp_from,sp_to;

        public _Holder(@NonNull View itemView) {
            super(itemView);
            txt_color=itemView.findViewById(R.id.Txt_Color_Filter);
            view=itemView.findViewById(R.id.ViewColor);
            textView=itemView.findViewById(R.id.Value_Child_Filter);
            color=itemView.findViewById(R.id.Checkecd_Color);
            sp_from=itemView.findViewById(R.id.Sp_From_Filter);
            sp_to=itemView.findViewById(R.id.Sp_To_Filter);
        }
    }






    public void PropertyFilter(_Holder holder, final FilterModel_ColorAndBrand model, final int position){

        holder.textView.setText(model.getProperty());
        SharedPreferences sharedPreferences=context.getSharedPreferences("saveFilter",context.MODE_PRIVATE);
        if (position==sharedPreferences.getInt(model.getProperty()+position,-10)){
            holder.textView.setChecked(true);
        }else {
            holder.textView.setChecked(false);
        }

        holder.textView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPreferences=context.getSharedPreferences("saveFilter",context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                if (isChecked){

                    editor.putInt(model.getProperty()+position,position);
                    editor.apply();
                }
                else {
                    editor.remove(model.getProperty()+position);
                    editor.apply();
                }

            }
        });

    }


    public void ColorsFilter(_Holder holder, FilterModel_ColorAndBrand model, final int position){
        holder.txt_color.setText(model.getColor());
        GradientDrawable gradientDrawable=new GradientDrawable();
        gradientDrawable.setCornerRadius(100);
        gradientDrawable.setStroke(2,context.getResources().getColor(R.color.meshkii));
        gradientDrawable.setColor(Color.parseColor(model.getValueColor()));
        holder.view.setBackground(gradientDrawable);

        SharedPreferences sharedPreferences=context.getSharedPreferences("saveFilter",context.MODE_PRIVATE);
        if (position==sharedPreferences.getInt("color"+position,-10)){
            holder.color.setChecked(true);
        }else {
            holder.color.setChecked(false);
        }

        holder.color.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPreferences=context.getSharedPreferences("saveFilter",context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                if (isChecked){
                    editor.putInt("color"+position,position);
                    editor.apply();

                }else {
                    editor.remove("color"+position);
                    editor.apply();
                }

            }
        });
    }


    public void BrandFilter(_Holder holder, FilterModel_ColorAndBrand model, final int position){
        holder.textView.setText(model.getName());
        SharedPreferences sharedPreferences=context.getSharedPreferences("saveFilter",context.MODE_PRIVATE);
        if (position==sharedPreferences.getInt("brand"+position,-10)){
            holder.textView.setChecked(true);
        }else {
            holder.textView.setChecked(false);
        }

        holder.textView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPreferences=context.getSharedPreferences("saveFilter",context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                if (isChecked){
                    editor.putInt("brand"+position,position);
                    editor.apply();
                }
                else {
                    editor.remove("brand"+position);
                    editor.apply();
                }

            }
        });
    }


    public void PriceFilter(final _Holder holder, final FilterModel_ColorAndBrand model){
        ListFromPrice.add("مهم نیست");
        //ListFromPrice.add("0 تومان");


        int from_price=Integer.parseInt(model.getFrom_Price());
        final int to_price=Integer.parseInt(model.getTo_price());


        int addFrom=0;
        //ListFromPrice.add(FormatNumber_Decimal.GetFormatInteger(model.getFrom_Price()));
        while (addFrom<to_price){
            ListFromPrice.add(FormatNumber_Decimal.GetFormatInteger(String.valueOf(addFrom)));

            if (to_price<500000){
                addFrom+=100000;
            }
            else if (to_price<1000000 && to_price>500000){
                addFrom+=200000;
            }
            else if (to_price<5000000 && to_price>1000000){
                addFrom+=500000;
            }
            else {
                addFrom+=1000000;
            }
        }




        final ArrayAdapter<String> adapter_from=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,ListFromPrice);
        holder.sp_from.setAdapter(adapter_from);



        holder.sp_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int positionP, long id) {
                SharedPreferences sharedPreferences=context.getSharedPreferences("saveFilter",context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();

                if (positionP!=0){
                    editor.putInt("priceF",positionP);
                    editor.apply();


                    ListToPrice.clear();

                    String Change=ListFromPrice.get(positionP).replaceAll(",","");
                    from=Change;
                    getDataFromFilter.get_Price(from,to);//for interface

                    int min=Integer.parseInt(Change);

                    ListToPrice.add("مهم نیست");
                    while (min<to_price){

                        ListToPrice.add(FormatNumber_Decimal.GetFormatInteger(String.valueOf(min)));

                        if (to_price<500000){
                            min+=100000;
                        }
                        else if (to_price<1000000 && to_price>500000){
                            min+=200000;
                        }
                        else if (to_price<5000000 && to_price>1000000){
                            min+=500000;
                        }
                        else {
                            min+=1000000;
                        }

                    }
                    ListToPrice.add(FormatNumber_Decimal.GetFormatInteger(model.getTo_price()));
                    ArrayAdapter<String> adapter_to=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,ListToPrice);
                    holder.sp_to.setAdapter(adapter_to);

                    int getSelectPrice_To=sharedPreferences.getInt("priceT",-10);
                    if (getSelectPrice_To!=-10){
                        holder.sp_to.setSelection(getSelectPrice_To);
                    }

                }
                else {
                    ListToPrice.clear();
                    ArrayAdapter<String> adapter_to=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,ListToPrice);
                    holder.sp_to.setAdapter(adapter_to);
                    editor.remove("priceF");
                    editor.remove("priceT");
                    editor.apply();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.sp_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int positionP, long id) {
                SharedPreferences sharedPreferences=context.getSharedPreferences("saveFilter",context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("priceT",positionP);
                editor.apply();
                to=ListToPrice.get(positionP).replaceAll(",","");

                getDataFromFilter.get_Price(from,to);//for interface

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SharedPreferences sharedPreferences=context.getSharedPreferences("saveFilter",context.MODE_PRIVATE);
        int getSelectPrice_From=sharedPreferences.getInt("priceF",-10);
        if (getSelectPrice_From!=-10){
            holder.sp_from.setAdapter(adapter_from);
            holder.sp_from.setSelection(getSelectPrice_From);
        }
    }


}
