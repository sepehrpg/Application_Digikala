package com.eksirsanat.ir.More_Product.Comment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.eksirsanat.ir.More_Product.Comment.ApiAndDataModel_Vote.Api_GetSumVote;
import com.eksirsanat.ir.More_Product.Comment.ApiAndDataModel_Vote.DataModel_AllCommentUser;
import com.eksirsanat.ir.More_Product.Comment.ApiAndDataModel_Vote.DataModel_GetVote;
import com.eksirsanat.ir.More_Product.More_Product;
import com.eksirsanat.ir.R;

import java.util.List;

public class Act_All_CommentUser extends AppCompatActivity {

    public static List<Float> listVote;
    public static List<DataModel_GetVote> listNameVote;
    TextView txt_countUser,txt_desAllVote,txt_show_comment;
    CardView card1,card2;
    RatingBar ratingBar;

    public static int ID_PRODUCT;
    int countUser;
    double allStar;

    String str_countUser;
    String str_allStar;

    Adapter_Vote_Product adapter_vote;
    Adapter_AllComment adapter_allComment;

    RecyclerView rec_main_Vote,rec_commentalluser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__all__comment_user);
        Cast();
        setAllVote();
    }

    public void Cast(){
        ID_PRODUCT=Integer.parseInt(getIntent().getStringExtra("idproduct"));
        str_countUser=getIntent().getStringExtra("countUser");
        str_allStar=getIntent().getStringExtra("allStar");
        txt_countUser=findViewById(R.id.Txt_Comment_CountUser);
        txt_desAllVote=findViewById(R.id.Txt_DesAllVote_Comment);
        ratingBar=findViewById(R.id.starVote_Comment);
        rec_main_Vote=findViewById(R.id.Rec_GetVote_Comment);
        rec_commentalluser=findViewById(R.id.Rec_CommentAllUser);
        txt_show_comment=findViewById(R.id.Txt_ShowComment);
        card1=findViewById(R.id.CardComment1);
        card2=findViewById(R.id.CardComment2);
        listVote= More_Product.listVote;
        listNameVote=More_Product.listNameVote;


        if (str_allStar==null || str_countUser==null){
            card1.setVisibility(View.GONE);
            card2.setVisibility(View.GONE);
            txt_show_comment.setVisibility(View.VISIBLE);
        }

    }


    public void setAllVote(){



        if (str_allStar!=null || str_countUser!=null){

            if (str_allStar.length()>1){
                str_allStar=str_allStar.substring(0,3);
                txt_desAllVote.setText("مجموع امتیاز "+ str_allStar +" از 5 ");
                float star=Float.parseFloat(str_allStar);
                ratingBar.setRating(star);

            }else {
                float star=Float.parseFloat(str_allStar);
                ratingBar.setRating(star);
                txt_desAllVote.setText(" مجموع امتیاز : "+ str_allStar +" از 5 ");
            }



            txt_countUser.setText(str_countUser);
            adapter_vote=new Adapter_Vote_Product(Act_All_CommentUser.this,listNameVote,listVote);
            RecyclerView.LayoutManager manager=new GridLayoutManager(Act_All_CommentUser.this,1);
            rec_main_Vote.setLayoutManager(manager);
            rec_main_Vote.setAdapter(adapter_vote);
        }else {
            card1.setVisibility(View.GONE);
            card2.setVisibility(View.GONE);
            txt_show_comment.setVisibility(View.VISIBLE);
        }


        Api_GetSumVote.GetCommentAllUser(Act_All_CommentUser.this, String.valueOf(ID_PRODUCT), new Api_GetSumVote.getCommentAllUser() {
            @Override
            public void getAllComment(List<DataModel_AllCommentUser> list) {
                adapter_allComment=new Adapter_AllComment(Act_All_CommentUser.this,list);
                RecyclerView.LayoutManager manager=new GridLayoutManager(Act_All_CommentUser.this,1);
                rec_commentalluser.setLayoutManager(manager);
                rec_commentalluser.setAdapter(adapter_allComment);
            }
        });


    }


}
