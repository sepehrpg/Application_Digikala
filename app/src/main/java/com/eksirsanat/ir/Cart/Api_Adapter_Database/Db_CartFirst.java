package com.eksirsanat.ir.Cart.Api_Adapter_Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Db_CartFirst extends SQLiteOpenHelper {

    static final String DbName="digikala.db";
    static final  int version=1;
    static final String tbl_Cart="tbl_cart";
    List<DataModel_DbProduct> list;

    SQLiteDatabase sql_w=this.getWritableDatabase();
    SQLiteDatabase sql_r=this.getReadableDatabase();

    Context context;

    public Db_CartFirst(Context context){
        super(context,DbName,null,version);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    /*    db.execSQL("CREATE TABLE IF NOT EXISTS "+ tbl_Cart +" (ID INTEGER PRIMARY KEY AUTOINCREMENT , idproduct TEXT , titlefa TEXT , " +
                " titleen TEXT , image TEXT , color TEXT , service TEXT ,shop TEXT ,number INTEGER" +
                " , price INTEGER ,total_price INTEGER ,final_price INTEGER, offer_price INTEGER); ");*/

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ tbl_Cart +" (ID INTEGER PRIMARY KEY AUTOINCREMENT , idproduct TEXT , titlefa TEXT , " +
                " titleen TEXT , image TEXT , color TEXT , service TEXT ,shop TEXT ,number INTEGER" +
                " , price INTEGER ,total_price INTEGER ,final_price INTEGER, offer_price INTEGER, idstore TEXT); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+tbl_Cart);
        onCreate(db);
    }

    public void get_DataModel_Db(List<DataModel_DbProduct> list){
        this.list=list;
    }

    //Insert_Post(int idproduct,String name,String image,String price,int colorId,String service)
    public void  Insert_Post(int idproduct,ContentValues contentValues){

        try {
            sql_w.insert(tbl_Cart,null,contentValues);


        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


       /* if (list.size()>0){

            for (int i = 0; i <list.size() ; i++) {
                DataModel_DbProduct model=new DataModel_DbProduct();

                contentValues.put("titleen",model.getTitleen());
                contentValues.put("service",service);
                contentValues.put("number",1); //first count product is 1

                contentValues.put("total_price",Integer.parseInt(price));
                contentValues.put("final_price",Integer.parseInt(price));
                contentValues.put("offer_price",Integer.parseInt(price));
            }

        }*/

    }


    public List<DataModel_DbProduct> get_Info_Db(){
        List<DataModel_DbProduct> list=new ArrayList<>();

        Cursor cursor=sql_r.rawQuery("SELECT * FROM "+tbl_Cart,null);
        if (cursor.getCount()>0){

            while (cursor.moveToNext()){
                DataModel_DbProduct model=new DataModel_DbProduct();
                model.setIdTable(cursor.getInt(0));
                model.setIdproduct(cursor.getString(1));
                model.setTitlefa(cursor.getString(2));
                model.setTitleen(cursor.getString(3));
                model.setImage(cursor.getString(4));
                model.setColor(cursor.getString(5));
                model.setService(cursor.getString(6));
                model.setShop(cursor.getString(7));

                model.setNumber(String.valueOf(cursor.getInt(8))); //is int but i think cursor back data to string
                model.setPrice(cursor.getString(9));
                model.setTotal_price(cursor.getString(10));
                model.setFinal_price(String.valueOf(cursor.getInt(11)));
                model.setOffer_price(cursor.getString(12));
                model.setIdstore(cursor.getString(13));

                list.add(model);

            }
            cursor.close();

        }

     return list;
    }


    public long Final_price_product(){

        long price=0;

        SQLiteStatement sqLiteStatement=sql_r.compileStatement("SELECT sum(final_price) FROM "+tbl_Cart);
        price=sqLiteStatement.simpleQueryForLong();

        return price;
    }


    public boolean idproduct_Exists(String id_product){

        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM "+ tbl_Cart +" WHERE idproduct = '"+id_product+"' ",null);
        if (cursor.getCount()>0){
            return true;
        }
        else {
            return false;
        }

    }


    public long Number_Count(String idproduct){
        long number=0;
        SQLiteStatement sqLiteStatement=sql_r.compileStatement("SELECT number FROM "+tbl_Cart+" WHERE idproduct="+idproduct); //????
        number=sqLiteStatement.simpleQueryForLong();
        return number;
    }


    public long Final_Price(String idproduct){
        long Final_Price=0;
        SQLiteStatement sqLiteStatement=sql_r.compileStatement("SELECT final_price FROM "+tbl_Cart+" WHERE idproduct="+idproduct); //????
        Final_Price=sqLiteStatement.simpleQueryForLong();
        return Final_Price;
    }



    public void Update_Number(String idproduct){
        ContentValues contentValues=new ContentValues();
        contentValues.put("number",Number_Count(idproduct)+1);
        sql_w.update(tbl_Cart,contentValues,"idproduct=?",new String[]{idproduct});

    }


    public void Update_AllPrice_AndNumber(String idproduct,int count){

        long price=0;
        SQLiteStatement sqLiteStatement=sql_r.compileStatement("SELECT price FROM "+tbl_Cart+" WHERE idproduct="+idproduct); //????
        price=sqLiteStatement.simpleQueryForLong();

        long priceOffer=0;
        SQLiteStatement sqLiteStatementOffer=sql_r.compileStatement("SELECT offer_price FROM "+tbl_Cart+" WHERE idproduct="+idproduct); //????
        priceOffer=sqLiteStatementOffer.simpleQueryForLong();

        if (priceOffer!=0){
            ContentValues contentValues=new ContentValues();
            contentValues.put("number",count);
            contentValues.put("final_price",priceOffer*count);

            //con tentValues.put("total_price",price_final*count);
            sql_w.update(tbl_Cart,contentValues,"idproduct=?",new String[]{idproduct});

        }else {
            ContentValues contentValues=new ContentValues();
            contentValues.put("number",count);
            contentValues.put("final_price",price*count);

            //contentValues.put("total_price",price_final*count);
            sql_w.update(tbl_Cart,contentValues,"idproduct=?",new String[]{idproduct});
        }




    }

    public void Delete_Product(String idproduct){
        sql_w.delete(tbl_Cart,"idproduct=?",new String[]{idproduct});
    }


    public void DeleteAllProduct(){
       sql_w.execSQL("DELETE FROM "+tbl_Cart);
    }


}
