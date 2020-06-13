package com.eksirsanat.ir.More_Product.Comment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eksirsanat.ir.Action.DateConverter;
import com.eksirsanat.ir.More_Product.Comment.ApiAndDataModel_Vote.DataModel_AllCommentUser;
import com.eksirsanat.ir.R;

import java.util.List;

public class Adapter_AllComment extends RecyclerView.Adapter<Adapter_AllComment._Holder> {
    Context context;
    List<DataModel_AllCommentUser> list;
    public Adapter_AllComment(Context context, List<DataModel_AllCommentUser> list){

        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public _Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.item_comment_user_one,null,false);

        return new _Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull _Holder holder, int position) {

        final DataModel_AllCommentUser model=list.get(position);
        String Star=String.valueOf(model.getStars());

        if (Star.length()>1){
            Star=Star.substring(0,3);
            holder.ratingBar.setRating(Float.parseFloat(Star));
            holder.star.setText("امتیاز ثبت شده: "+ Star);


        }else {
            holder.ratingBar.setRating(Float.parseFloat(Star));
            holder.star.setText("امتیاز ثبت شده: "+ Star);
        }

        if (model.getComment()!=null){
            holder.comment.setText(model.getComment());
        }
        else {
            holder.comment.setText("این کاربر نظری نداده");
            holder.comment.setTextColor(context.getResources().getColor(R.color.ghermez));

        }
        if (model.getName()!=null){
            holder.name.setText(model.getName());
        }else {
            holder.name.setText("نا مشخص");
            holder.name.setTextColor(context.getResources().getColor(R.color.ghermez));
        }

        if (model.getDate()!=null){
            DateConverter dateConverter=new DateConverter();
            String getDate=model.getDate().substring(0,10);

            dateConverter.gregorianToPersian(Integer.parseInt(getDate.substring(0,4)), Integer.parseInt(getDate.substring(5,7)), Integer.parseInt(getDate.substring(8,10)));
            String year=String.valueOf(dateConverter.getYear());
            String mounth=String.valueOf(dateConverter.getMonth());
            String day=String.valueOf(dateConverter.getDay());
            holder.date.setText(year+"/"+mounth+"/"+day);
        }


        final String sumSTAR=Star;

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context,Act_MoreCommentUser.class);
                intent.putExtra("manfi",model.getManfi());
                intent.putExtra("mosbat",model.getMosbat());
                intent.putExtra("comment",model.getComment());
                intent.putExtra("idcomment",model.getIdVote());
                intent.putExtra("iduser",model.getIdUser());
                intent.putExtra("name",model.getName());
                intent.putExtra("date",model.getDate());
                intent.putExtra("sumstar",sumSTAR);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class _Holder extends RecyclerView.ViewHolder {

        TextView comment,name,star,more,date;
        RatingBar ratingBar;
        public _Holder(@NonNull View itemView) {
            super(itemView);
            comment=itemView.findViewById(R.id.Txt_Comment_comment);
            name=itemView.findViewById(R.id.Txt_Commment_NameUser);
            star=itemView.findViewById(R.id.Txt_Comment_VoteUser);
            date=itemView.findViewById(R.id.DateCommentAllUser);
            more=itemView.findViewById(R.id.Txt_CommentMore);
            ratingBar=itemView.findViewById(R.id.Rat_Comment);
        }
    }
}
