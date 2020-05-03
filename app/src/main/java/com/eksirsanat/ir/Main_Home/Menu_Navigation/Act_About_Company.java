package com.eksirsanat.ir.Main_Home.Menu_Navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eksirsanat.ir.R;

public class Act_About_Company extends AppCompatActivity {

    TextView VersionName;
    ImageView back_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__about__company);
        Cast();
        ShowVersion();
    }

    public void Cast(){
        VersionName=findViewById(R.id.VersionName);
        back_about=findViewById(R.id.back_about);


        back_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }



    public void ShowVersion(){

        PackageManager manager=getPackageManager();
        PackageInfo packageInfo=null;
        try {
            packageInfo=manager.getPackageInfo(getPackageName(),0);

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        VersionName.setText(packageInfo.versionName);
    }




    public void Btn_Email(View V){

        Intent intent=new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","sepehrfz77@gmail.com",null));
        intent.putExtra("android.intent.extra.SUBJECT","send from Application android");
        startActivity(Intent.createChooser(intent,"لطفا انتخاب کنید..."));

    }


    public void Btn_Call(View V){

        String phone="09377945956";
        Intent intent=new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",phone,null));
        startActivity(intent);

    }


}
