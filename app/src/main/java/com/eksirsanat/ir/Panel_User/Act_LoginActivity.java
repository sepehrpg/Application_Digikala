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

import com.eksirsanat.ir.Action.Get_Info;
import com.eksirsanat.ir.Panel_User.Api.Api_Panel;
import com.eksirsanat.ir.R;
import com.eksirsanat.ir.Search_Product.Act_Search_Product;
import com.pnikosis.materialishprogress.ProgressWheel;

public class Act_LoginActivity extends AppCompatActivity {

    ImageView img_back,img_search,img_store;

    TextView txt_titleTollbar,txt_forget_pass,txt_register;
    CheckBox checkBox;

    EditText edt_email,edt_pass;

    Button btn_login;


    ProgressWheel progressWheel;

    Api_Panel api_panel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__login);
        Get_Info.get_Info(Act_LoginActivity.this);
        Cast();
        onClick();
        ok_Login();
    }

    void Cast(){
        txt_titleTollbar=findViewById(R.id.Title_Custom_Toolbar);
        progressWheel=findViewById(R.id.progress_wheel_Login);
        img_back=findViewById(R.id.Close_Main_Toolbar);
        img_search=findViewById(R.id.Img_search_Main_Toolbar);
        img_store=findViewById(R.id.Img_store_Main_toolbar);
        txt_forget_pass=findViewById(R.id.Txt_Login_Pass_Forgt);
        txt_register=findViewById(R.id.Txt_Login_Register);
        checkBox=findViewById(R.id.Check_Login);
        edt_email=findViewById(R.id.Edt_Login_Email);
        edt_pass=findViewById(R.id.Edt_Login_Pass);
        btn_login=findViewById(R.id.Btn_Login);

        progressWheel.setVisibility(View.GONE);

        api_panel=new Api_Panel(Act_LoginActivity.this,progressWheel);


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
                startActivity(new Intent(Act_LoginActivity.this, Act_Search_Product.class));
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

        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Act_LoginActivity.this,Act_Registeri.class);
                intent.putExtra("reg","reg");
                startActivity(intent);
            }
        });


        txt_forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username=edt_email.getText().toString().trim();


                if (username.length()<2){
                    edt_email.requestFocus();
                    edt_email.setError("لطفا ایمیل را وارد کنید");
                }

                else {
                  api_panel.Get_SendCode_Reply(username);
                }



            }
        });


    }


    void ok_Login(){

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username=edt_email.getText().toString().trim();
                String password=edt_pass.getText().toString().trim();

                if (username.length()<2){
                    edt_email.requestFocus();
                    edt_email.setError("لطفا ایمیل را وارد کنید");
                    //Toast.makeText(Act_Registeri.this, "ایمیل خود را صحیح وارد کنید", Toast.LENGTH_SHORT).show();
                    //return;
                }

                else if (password.length()<4){
                    edt_pass.requestFocus();
                    edt_pass.setError("پسورد شما باید حداقل 4 کاراکتر باشد");

                    //Toast.makeText(Act_Registeri.this, "پسورد شما باید حداقل 4 کاراکتر باشد", Toast.LENGTH_SHORT).show();
                    // return;
                }

                else {
                    api_panel.Get_Login(username,password);
                }




            }
        });

    }
}
