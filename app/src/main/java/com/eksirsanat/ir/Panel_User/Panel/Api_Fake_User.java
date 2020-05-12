package com.eksirsanat.ir.Panel_User.Panel;

import android.content.Context;

import androidx.core.content.res.ResourcesCompat;

import com.eksirsanat.ir.R;

import java.util.ArrayList;
import java.util.List;


public class Api_Fake_User {

    public static List<DataModel_CLass> ListModel(Context context){

        String[] listName={"ویرایش اطلاعات","سفارشات من","دیجی بن","لیست مورد علاقه",
                            "پیام ها","آدرس های من","شماره کارت بانکی","تغییر رمز عبور","خروج"};

        int[] icon={R.drawable.ic_user_edit,R.drawable.ic_user_basket,R.drawable.ic_user_money,R.drawable.ic_favorite_border_black_24dp,
                R.drawable.ic_user_message,R.drawable.ic_uset_address,R.drawable.ic_user_card,R.drawable.ic_lock_black_24dp,R.drawable.ic_user_exit};

        List<DataModel_CLass> List=new ArrayList<>();

        for (int i=0;i<9;i++){
            DataModel_CLass dataModel_cLass=new DataModel_CLass();
            dataModel_cLass.setName(listName[i]);
            dataModel_cLass.setId(i+1);
            dataModel_cLass.setIcon(ResourcesCompat.getDrawable(context.getResources(),icon[i] ,null));

            List.add(dataModel_cLass);
        }


        return List;

    }

}
