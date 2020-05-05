package com.eksirsanat.ir.Panel_User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eksirsanat.ir.R;
import com.eksirsanat.ir.Search_Product.Act_Search_Product;

public class Act_ForgetPass extends AppCompatActivity {
    ImageView img_back,img_search,img_store;
    TextView Title_Custom_Toolbar;
    Button btn_ok;

    EditText edt_emailForget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__forget_pass);

        Title_Custom_Toolbar=findViewById(R.id.Title_Custom_Toolbar);
        Title_Custom_Toolbar.setText("تغییر رمز عبور");
        img_back=findViewById(R.id.Close_Main_Toolbar);
        img_search=findViewById(R.id.Img_search_Main_Toolbar);
        img_store=findViewById(R.id.Img_store_Main_toolbar);
        edt_emailForget=findViewById(R.id.Edt_ForgetPassChange);
        btn_ok=findViewById(R.id.Btn_ChangeForgetPass);
        onClick();
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
                startActivity(new Intent(Act_ForgetPass.this, Act_Search_Product.class));
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_ForgetPass.this, Act_ChangePassword.class));
            }
        });


    }
}
