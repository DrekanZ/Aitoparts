package com.example.aitoparts;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyConnection {

    private static VolleyConnection vInstance;
    private RequestQueue requestQueue;
    private static Context vCtxt;

    private VolleyConnection (Context context)
    {
        vCtxt = context;
        requestQueue = getRequestQueue();
    }
    private RequestQueue getRequestQueue() {
        if (requestQueue == null)
        {
            requestQueue= Volley.newRequestQueue(vCtxt.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized VolleyConnection getInstance(Context context) {
        if (vInstance==null) {
            vInstance = new VolleyConnection(context);
        }
        return vInstance;
    }

    public<T> void addToRequestQue (Request<T> request) {
        getRequestQueue().add(request);
    }
}

