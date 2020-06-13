package com.eksirsanat.ir.More_Product.Comment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.eksirsanat.ir.Action.Get_Token;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Config;
import com.eksirsanat.ir.Panel_User.Act_LoginActivity;
import com.eksirsanat.ir.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Act_More_AddComment extends AppCompatActivity implements Config {

    EditText name,mosbat,manfi,comment;
    TextView txt_ok;

    String idProduct;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Get_Token.getToken(this)==null && Get_Token.getToken(this).length()<10 ){
            Intent intent=new Intent(Act_More_AddComment.this, Act_LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            setContentView(R.layout.activity_act__more__add_comment);
            Cast();
            sendComment();
        }

    }

    public void Cast(){
        idProduct=getIntent().getStringExtra("idproduct");
        Log.i("idpro",idProduct+"");
        name=findViewById(R.id.Edt_NameCommentUser);
        mosbat=findViewById(R.id.Edt_Mosbat);
        manfi=findViewById(R.id.Edt_Manfi);
        comment=findViewById(R.id.Txt_Comment_MoreUser);
        txt_ok=findViewById(R.id.Txt_Btn_AddCommnt);
    }

    public void sendComment(){


        txt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Name=name.getText().toString().trim();
                final String Mosbat=mosbat.getText().toString().trim();
                final String Manfi=manfi.getText().toString().trim();
                final String Comment=comment.getText().toString().trim();


                if (Name.equals("")){
                    name.requestFocus();
                    name.setError("لطفا نام خود را کامل بنویسید");
                }

                else if (Comment.equals("")){
                    comment.requestFocus();
                    comment.setError("لطفا نظر خود را درباره محصول بنویسید");
                    return;

                }

                else if (Mosbat.equals("")){
                    mosbat.requestFocus();
                    mosbat.setError("لطفا درخواست را کامل کنید");
                    return;

                }
                else if (Manfi.equals("")){
                    manfi.requestFocus();
                    manfi.setError("لطفا درخواست را کامل کنید");
                    return;
                }


                //POINT::you cant send message with url and you should use map
                else {
                    String url=urlHome+"addcomment.php?idproduct="+idProduct+"&token="+Get_Token.getToken(Act_More_AddComment.this);
                    Log.i("urlHome",url+"");

                    StringRequest stringRequest=new StringRequest(1, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject=new JSONObject(response);

                                if (jsonObject.getString("status").equals("ok")){

                                    Toast.makeText(Act_More_AddComment.this, "با موفقیت ثبت شد ", Toast.LENGTH_SHORT).show();

                                }else {
                                    Toast.makeText(Act_More_AddComment.this, "خطا در ثبت امتیاز ", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Act_More_AddComment.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })  {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> map=new HashMap<String, String>();
                            map.put("name",Name);
                            map.put("mosbat",Mosbat);
                            map.put("manfi",Manfi);
                            map.put("comment",Comment);

                            return map;

                        }
                    };


                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        //Volley.newRequestQueue(context).add(jsonObjectRequest);
                        Request_Volley.getInstance(Act_More_AddComment.this).add(stringRequest);






                }





            }
        });


    }
}
