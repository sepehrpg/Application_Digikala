package com.eksirsanat.ir.Main_Home.pack_timer;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Main_Home.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class Api_Timer implements Config {
    static int timer[];
    static int zero=0;

    public static void GetMethod_timer(final Context context, final TextView Tv__sec, final TextView Tv_min, final TextView Tv_hour){


        try {
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, urlHome + "timer.php", null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    timer=new int[response.length()];

                    for (int i=0;i<response.length(); i++){ //length()=5

                        try {
                            timer[i]=response.getInt("timer");//Example=> [0]=20588,[1]=20587,[2]=20586,...

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    gethandlerMethod(context,Tv__sec,Tv_min,Tv_hour);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(context).add(jsonObjectRequest);
            //Request_Volley.getInstance(context).add(jsonObjectRequest); //Never User This for Timer
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }




    }

    public static void gethandlerMethod(final Context context, final TextView Tv__sec, final TextView Tv_min, final TextView Tv_hour){

        try {
            for (int i=0; i<timer.length ;i++){ //timer.length=5

                if (timer[i]>zero){ //Example=>First:20588>0

                    zero=timer[i]; //Example=>zero=20588
                }
            }

            final Handler handler=new Handler();
            new Thread(new Runnable() {
                @Override
                public void run() {

                    while (zero>0){ //20588>0

                        try {
                            handler.post(new Runnable() { //use handler until use from this code in UI
                                @Override
                                public void run() {

                                    //algorritm Change number of hour and sec and mint to  hour and sec and mint now

                                    float hour=zero/3600; //one hour=3600 sec //Example 20588/3600=5.71 That Change to 6 when int
                                    int Hour=(int)hour;

                                    float min=(zero-(Hour)*3600)/60;
                                    int Min=(int)min;

                                    float sec=(zero-((Hour)*3600)-(Min*60));
                                    int Sec=(int)sec;

                                    Tv_hour.setText(String.valueOf(String.format(Locale.US,"%02d",Hour))); //Change int to String and Locale=US
                                    Tv_min.setText(String.valueOf(String.format(Locale.US,"%02d",Min)));//Change int to String and Locale=US
                                    Tv__sec.setText(String.valueOf(String.format(Locale.US,"%02d",Sec)));//Change int to String and Locale=US

                                    zero--;

                                }
                            });

                            Thread.sleep(1000);


                        }catch (Exception e){
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }



                    }


                }
            }).start();

        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }

}
