package com.eksirsanat.ir.Panel_User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eksirsanat.ir.R;
import com.eksirsanat.ir.Search_Product.Act_Search_Product;

public class Act_Registeri extends AppCompatActivity {

    TextView Title_Custom_Toolbar;

    ImageView img_back,img_search,img_store;

    EditText edt_email,edt_pass;
    Button btn_register;

    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__registeri);
        Cast();
        onClick();
    }


    void Cast(){
        Title_Custom_Toolbar=findViewById(R.id.Title_Custom_Toolbar);
        Title_Custom_Toolbar.setText("ورود به فروشگاه");
        img_back=findViewById(R.id.Close_Main_Toolbar);
        img_search=findViewById(R.id.Img_search_Main_Toolbar);
        img_store=findViewById(R.id.Img_store_Main_toolbar);
        edt_email=findViewById(R.id.Edt_Register_Email);
        edt_pass=findViewById(R.id.Edt_Register_Pass);
        btn_register=findViewById(R.id.Btn_Register);
        checkBox=findViewById(R.id.Check_Register);
        Title_Custom_Toolbar.setText("ثبت نام");


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
                startActivity(new Intent(Act_Registeri.this, Act_Search_Product.class));
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    edt_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); //set visibility pass
                }
                else {
                    edt_pass.setTransformationMethod(PasswordTransformationMethod.getInstance()); //set gone pass

                }

            }
        });



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_Registeri.this, Act_Viryfy_Code.class));

            }
        });


    }

}
