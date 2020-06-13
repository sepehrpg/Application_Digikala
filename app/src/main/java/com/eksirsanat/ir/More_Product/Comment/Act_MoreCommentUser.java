package com.eksirsanat.ir.More_Product.Comment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.eksirsanat.ir.Action.DateConverter;
import com.eksirsanat.ir.More_Product.Comment.ApiAndDataModel_Vote.Api_GetSumVote;
import com.eksirsanat.ir.More_Product.Comment.ApiAndDataModel_Vote.DataModel_GetVote;
import com.eksirsanat.ir.More_Product.More_Product;
import com.eksirsanat.ir.R;

import java.util.ArrayList;
import java.util.List;

public class Act_MoreCommentUser extends AppCompatActivity {

    TextView txt_date,txt_name,txt_mosbat,txt_manfi,txt_comment,txt_sumStar;
    RecyclerView recyclerView;
    RatingBar ratingBar;

    DateConverter dateConverter;


    String manfi,mosbat,comment,sumStar,name,date,idComment;
    int IDPRODUCT;

    public static List<Float> listVote;
    public static List<DataModel_GetVote> listNameVote;

    Adapter_Vote_Product adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__more_comment_user);
        Cast();
        setRecycler();
    }

    public void Cast(){
        txt_date=findViewById(R.id.Txt_MoreCommentDate);
        txt_name=findViewById(R.id.Txt_MoreCommentName);
        txt_mosbat=findViewById(R.id.Txt_Mosbat);
        txt_manfi=findViewById(R.id.Txt_Manfi);
        txt_comment=findViewById(R.id.Txt_Comment_MoreUser);
        txt_sumStar=findViewById(R.id.Txt_DesAllVote_MoreComment);
        recyclerView=findViewById(R.id.Rec_GetVote_MoreComment);
        ratingBar=findViewById(R.id.starVote_MoreComment);
        dateConverter=new DateConverter();

        IDPRODUCT=Act_All_CommentUser.ID_PRODUCT;
        listVote=new ArrayList<>();
        listNameVote=new ArrayList<>();
        listVote= More_Product.listVote;
        listNameVote=More_Product.listNameVote;

        manfi=getIntent().getStringExtra("manfi");
        comment=getIntent().getStringExtra("comment");
        mosbat=getIntent().getStringExtra("mosbat");
        idComment=getIntent().getStringExtra("idcomment");
        sumStar=getIntent().getStringExtra("sumstar");
        name=getIntent().getStringExtra("name");
        date=getIntent().getStringExtra("date");

        if (manfi!=null){
            txt_manfi.setText(manfi);
        }
        if (comment!=null){
            txt_comment.setText(comment);
        }

        if (mosbat!=null){
            txt_mosbat.setText(mosbat);
        }

        if (name!=null){
            txt_name.setText(name);
        }
        if (date!=null){
            String getDate=date.substring(0,10);
            dateConverter.gregorianToPersian(Integer.parseInt(getDate.substring(0,4)), Integer.parseInt(getDate.substring(5,7)), Integer.parseInt(getDate.substring(8,10)));
            String year=String.valueOf(dateConverter.getYear());
            String mounth=String.valueOf(dateConverter.getMonth());
            String day=String.valueOf(dateConverter.getDay());
            txt_date.setText(year+"/"+mounth+"/"+day);

        }

        if (sumStar!=null){
            if (sumStar.length()>1){
                sumStar=sumStar.substring(0,3);
                ratingBar.setRating(Float.parseFloat(sumStar));
                txt_sumStar.setText("امتیاز ثبت شده: "+ sumStar+" از 5 ");


            }else {
                ratingBar.setRating(Float.parseFloat(sumStar));
                txt_sumStar.setText("امتیاز ثبت شده: "+ sumStar+" از 5 ");
            }
        }
    }


    public void setRecycler(){

        Api_GetSumVote.GetComment_MoreUser(Act_MoreCommentUser.this, String.valueOf(IDPRODUCT), idComment, new Api_GetSumVote.GetMore_CommentUser() {
            @Override
            public void getVote(List<Float> listStarUser) {
                adapter=new Adapter_Vote_Product(Act_MoreCommentUser.this,listNameVote,listStarUser);
                RecyclerView.LayoutManager manager=new GridLayoutManager(Act_MoreCommentUser.this,1);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);

            }
        });

    }


}
