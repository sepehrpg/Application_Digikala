package com.eksirsanat.ir.Panel_User.Panel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eksirsanat.ir.R;

import java.util.List;

public class Adapter_Rec_User extends RecyclerView.Adapter<Adapter_Rec_User._Holedr> {

    Context context;
    List<DataModel_CLass> list;
    View.OnClickListener Click;

    public Adapter_Rec_User(Context context, List<DataModel_CLass> list, View.OnClickListener Click){
        this.context=context;
        this.list=list;
        this.Click=Click;
    }


    @NonNull
    @Override
    public _Holedr onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new _Holedr(LayoutInflater.from(context).inflate(R.layout.item_user_info,null,false));
    }

    @Override
    public void onBindViewHolder(@NonNull _Holedr holder, int position) {

        DataModel_CLass dataModel_cLass=list.get(position);
        holder.txt.setText(dataModel_cLass.getName());

        holder.imgag.setImageDrawable(dataModel_cLass.getIcon());
        holder.Line.setTag(dataModel_cLass);
        holder.Line.setOnClickListener(Click);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class _Holedr extends RecyclerView.ViewHolder {

        ImageView imgag;
        TextView txt;
        LinearLayout Line;

        public _Holedr(@NonNull View itemView) {
            super(itemView);
            imgag=itemView.findViewById(R.id.Img_user_imnfo);
            txt=itemView.findViewById(R.id.Txt_UserInfo);
            Line=itemView.findViewById(R.id.LineUserInfo);
        }
    }
}
