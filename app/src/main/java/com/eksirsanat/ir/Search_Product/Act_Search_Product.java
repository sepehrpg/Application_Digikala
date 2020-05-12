package com.eksirsanat.ir.Search_Product;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.eksirsanat.ir.R;

import java.util.ArrayList;

public class Act_Search_Product extends AppCompatActivity {

    ImageView back,candel,microphone,barcode;
    EditText edt_searc;


    int Request_Code=1;
    int Request_Code_QR_Scan=101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__search__product);

        try {
            Cast();
            ClickItem();
            MicorPhome();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    void Cast(){
        back=findViewById(R.id.Img_Search_Back_MAin);
        candel=findViewById(R.id.Img_Search_CancelSearch);
        microphone=findViewById(R.id.Img_Search_microphome);
        //barcode=findViewById(R.id.Img_Search_barcode);
        edt_searc=findViewById(R.id.Edt_Search_Main);
    }

    void ClickItem(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });


        edt_searc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


             if (after>=1){
                    microphone.setVisibility(View.GONE);
                   // barcode.setVisibility(View.GONE);
                    candel.setVisibility(View.VISIBLE);
                }
                else {
                    microphone.setVisibility(View.VISIBLE);
                   // barcode.setVisibility(View.VISIBLE);
                    candel.setVisibility(View.GONE);
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        candel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_searc.setText("");
                microphone.setVisibility(View.VISIBLE);
                barcode.setVisibility(View.VISIBLE);
                candel.setVisibility(View.GONE);
            }
        });

        microphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }



    void MicorPhome(){
        microphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName());
                    //intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"com.eksirsanat.ir");
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1000);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"fa"); //support farsi
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"لطفا صحبت کنید...");

                    startActivityForResult(intent,Request_Code);

                }catch (Exception e){

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Request_Code){

            if (resultCode==RESULT_OK){

                ArrayList<String> arrayList=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); //get speach from user
                String check=String.valueOf(arrayList.get(0));
                edt_searc.setText(check);

            }



        }

    }






}
