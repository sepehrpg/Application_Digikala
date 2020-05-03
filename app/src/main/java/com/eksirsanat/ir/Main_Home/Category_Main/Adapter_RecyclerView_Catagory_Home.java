package com.eksirsanat.ir.Main_Home.Category_Main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eksirsanat.ir.Act_Home;
import com.eksirsanat.ir.R;

import java.util.List;

public class Adapter_RecyclerView_Catagory_Home extends RecyclerView.Adapter<Adapter_RecyclerView_Catagory_Home._Holder> {

    Context context;
    List<Datamodel_Category_Home> datamodelCategoryHomes;
    public Adapter_RecyclerView_Catagory_Home(Context context, List<Datamodel_Category_Home> datamodelCategoryHomes){
        this.context=context;
        this.datamodelCategoryHomes=datamodelCategoryHomes;
    }



    @NonNull
    @Override
    public _Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.items_category_home,parent,false);
        return new _Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull _Holder holder, int position) {

        final Datamodel_Category_Home datamodel_category_home=datamodelCategoryHomes.get(position);
        holder.Btn_category.setText(datamodel_category_home.getTitle());

        //Typeface iransans=Typeface.createFromAsset(context.getAssets(),"iranian_sans.ttf");

        holder.Btn_category.setTypeface(Act_Home.iransans);

        /*holder.Btn_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return datamodelCategoryHomes.size();
    }

    public class _Holder extends RecyclerView.ViewHolder {
        Button Btn_category;

        public _Holder(@NonNull View itemView) {
            super(itemView);
            Btn_category=itemView.findViewById(R.id.Btn_category);
        }
    }
}
