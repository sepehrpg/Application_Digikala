package com.eksirsanat.ir.Panel_User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eksirsanat.ir.R;
import com.eksirsanat.ir.Search_Product.Act_Search_Product;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Act_Viryfy_Code extends AppCompatActivity {
    TextView Title_Custom_Toolbar,txt_infouser,txt_editmobile_email,txt_resendCode;
    ImageView img_back,img_search,img_store;

    EditText code1,code2,code3,code4,code5;

    EditText edt_get_Code,edt_Code;



    int[] arrayCode={R.id.Edt_Code1,R.id.Edt_Code2,R.id.Edt_Code3,R.id.Edt_Code4,R.id.Edt_Code5};


    Timer timer;
    long timerCount;
    int check;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__viryfy__code);
        Cast();
    }


    void Cast(){
        Title_Custom_Toolbar=findViewById(R.id.Title_Custom_Toolbar);
        Title_Custom_Toolbar.setText("تایید مشخصات");
        txt_infouser=findViewById(R.id.Txt_show_InfoUser);
        txt_editmobile_email=findViewById(R.id.Txt_EditEmailOrPhone);
        txt_resendCode=findViewById(R.id.Txt_RepeatCode);
        img_back=findViewById(R.id.Close_Main_Toolbar);
        img_search=findViewById(R.id.Img_search_Main_Toolbar);
        img_store=findViewById(R.id.Img_store_Main_toolbar);
       /* code1=findViewById(R.id.Edt_Code1);
        code2=findViewById(R.id.Edt_Code2);
        code3=findViewById(R.id.Edt_Code3);
        code4=findViewById(R.id.Edt_Code4);
        code5=findViewById(R.id.Edt_Code5);*/

        onClick();


       for (int i=0;i<arrayCode.length;i++){

           int j=i+1;
          EditText editText1=findViewById(arrayCode[i]);
           if (arrayCode.length-1!=i){

               final EditText editText2=findViewById(arrayCode[j]);
               editText1.addTextChangedListener(new TextWatcher() {
                   @Override
                   public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                   }

                   @Override
                   public void onTextChanged(CharSequence s, int start, int before, int count) {

                   }

                   @Override
                   public void afterTextChanged(Editable s) {
                       editText2.requestFocus();
                   }
               });


           }
       }
        TimerMethod();



    }


    void onClick(){

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_Viryfy_Code.this, Act_Search_Product.class));
            }
        });

        txt_editmobile_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Act_Viryfy_Code.this, Act_Registeri.class));
            }
        });

    }



    void TimerMethod(){

        timerCount=60000;
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() { //until set in ui
                    @Override
                    public void run() {
                        timerCount-=1000;
                        txt_resendCode.setText("ارسال  کد تایید "+getTimer(timerCount)+" ثانیه ");

                        if (timerCount==0){
                            timer.cancel();
                            txt_resendCode.setText("00:00");
                            check=1;
                        }


                    }
                });
            }
        },0,1000);

    }

    public String getTimer(long timercount){

        long secend=(timercount/1000);
        long mint=secend/60;
        secend %=60;
        return String.format(String.format(Locale.ENGLISH,"%02d",secend));
    }


}
