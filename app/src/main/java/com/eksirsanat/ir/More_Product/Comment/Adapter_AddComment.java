package com.eksirsanat.ir.More_Product.Comment;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eksirsanat.ir.More_Product.Comment.ApiAndDataModel_Vote.DataModel_GetVote;
import com.eksirsanat.ir.R;

import java.util.List;

public class Adapter_AddComment extends RecyclerView.Adapter<Adapter_AddComment._Holder> {

    Context context;
    List<DataModel_GetVote> listNameVote;
    public Adapter_AddComment(Context context, List<DataModel_GetVote> listNameVote){

        this.context=context;
        this.listNameVote=listNameVote;
    }


    @NonNull
    @Override
    public _Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.item_add_vote,null,false);

        return new _Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final _Holder holder, int position) {

        final DataModel_GetVote model=listNameVote.get(position);
        if (model.getName()!=null){
            holder.name.setText(model.getName());
        }

        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Log.i("idVoteAndStar","idVote="+model.getIdvote()+"Star="+progress);

                SharedPreferences sharedPreferences=context.getSharedPreferences("seekbar",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(model.getName(),progress+"");
                editor.putString(model.getName()+"id",model.getIdvote());
                editor.apply();

                String progresNumber=String.valueOf(progress);
                holder.number.setText(progresNumber);
                //notifyDataSetChanged();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




    }

    //public interface GetVote


    @Override
    public int getItemCount() {
        return listNameVote.size();
    }

    public class _Holder extends RecyclerView.ViewHolder {
        TextView name,number;
        SeekBar seekBar;
        public _Holder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.Txt_AddComment_Name);
            number=itemView.findViewById(R.id.Txt_Seekbar);
            seekBar=itemView.findViewById(R.id.Seekbar_AddVote);
        }
    }
}
