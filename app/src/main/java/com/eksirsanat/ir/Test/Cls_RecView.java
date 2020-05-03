package com.eksirsanat.ir.Test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class Cls_RecView extends RecyclerView.Adapter<Cls_RecView._Holder> {

    Context Ctx;
    public Cls_RecView(Context Ctx){
        this.Ctx=Ctx;
    }


    @NonNull
    @Override
    public _Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull _Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class _Holder extends RecyclerView.ViewHolder {
        public _Holder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
