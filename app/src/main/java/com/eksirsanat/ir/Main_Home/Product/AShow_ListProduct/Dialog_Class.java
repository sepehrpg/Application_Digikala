package com.eksirsanat.ir.Main_Home.Product.AShow_ListProduct;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.eksirsanat.ir.R;

public class Dialog_Class extends Dialog {

    Context context;

    RadioGroup radioGroup;
    getText getText;
    public Dialog_Class(Context context,getText getText) {
        super(context);
        this.context=context;
        this.getText=getText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_item_listproduct);


        radioGroup=findViewById(R.id.RG_Dialog);
        SharedPreferences sharedPreferences=context.getSharedPreferences("radio",0);
        radioGroup.check(sharedPreferences.getInt("id",R.id.r1));

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton radioButton=findViewById(checkedId);
                String nameradio=radioButton.getText().toString().trim();

                SharedPreferences sharedPreferences=context.getSharedPreferences("radio",0);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("id",checkedId);
                editor.putString("name",nameradio);
                editor.apply();

                getText.get_Text(nameradio);

                dismiss();
            }
        });

    }


    public interface getText{
        void get_Text(String text);
    }

}
