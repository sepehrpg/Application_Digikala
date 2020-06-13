package com.eksirsanat.ir.Panel_User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eksirsanat.ir.Cart.Act_BasketCart;
import com.eksirsanat.ir.Panel_User.Api.Api_Panel;
import com.eksirsanat.ir.R;
import com.eksirsanat.ir.Search_Product.Act_Search_Product;
import com.pnikosis.materialishprogress.ProgressWheel;

public class Act_ChangePassword extends AppCompatActivity {
    ImageView img_back,img_search,img_store;
    TextView Title_Custom_Toolbar;

    EditText edt_pass,edt_rePass;

    Button btn_ok;

    Api_Panel api_panel;

    ProgressWheel progressWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__change_password);
        Cast();
        onClick();
        ok_ChangePass();

    }



    void Cast(){
        Title_Custom_Toolbar=findViewById(R.id.Title_Custom_Toolbar);
        img_back=findViewById(R.id.Close_Main_Toolbar);
        img_search=findViewById(R.id.Img_search_Main_Toolbar);
        img_store=findViewById(R.id.Img_store_Main_toolbar);
        edt_pass=findViewById(R.id.Edt_Pass_Forget);
        edt_rePass=findViewById(R.id.Edt_RePass_Forget);
        progressWheel=findViewById(R.id.progress_wheel_ChangePass);
        btn_ok=findViewById(R.id.Btn_ChangePass);
        Title_Custom_Toolbar.setText("ایجاد کلمه عبور جدید");

        progressWheel.setVisibility(View.GONE);

        api_panel=new Api_Panel(this,progressWheel);



    }

    void onClick(){

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_ChangePassword.this, Act_Search_Product.class));
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        img_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_ChangePassword.this, Act_BasketCart.class));

            }
        });
    }



    void  ok_ChangePass(){
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Pass=edt_pass.getText().toString().trim();
                String rePass=edt_rePass.getText().toString().trim();

                if (Pass.length()<4){
                    edt_pass.requestFocus();
                    edt_pass.setError("پسورد شما باید حداقل 4 کاراکتر باشد");

                }

                else if (!rePass.equals(Pass)){
                    edt_rePass.requestFocus();
                    edt_rePass.setError("تکرار پسورد مطابقت ندارد");

                }

                else {
                    api_panel.Get_ChangePass(Pass,rePass);
                }
            }
        });
    }


}
