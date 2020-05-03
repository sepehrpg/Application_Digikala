package com.eksirsanat.ir.Action;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Request_Volley {

    static RequestQueue requestQueueInstance;

    public static RequestQueue getInstance(Context context){

        if (requestQueueInstance==null){
            requestQueueInstance= Volley.newRequestQueue(context);
        }

        return requestQueueInstance;

    }

}
