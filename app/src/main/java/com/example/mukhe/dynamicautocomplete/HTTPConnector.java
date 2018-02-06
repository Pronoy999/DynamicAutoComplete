package com.example.mukhe.dynamicautocomplete;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by mukhe on 06-Feb-18.
 */

public class HTTPConnector {
    String url;
    JSONObject jsonResponse;
    Context context;
    public HTTPConnector(String url,Context context){
        this.url=url;
        this.context=context;
    }

    public JSONObject getJsonResponse() {
        makeQuery();
        return jsonResponse;
    }
    private void makeQuery(){
        RequestQueue queue= Volley.newRequestQueue(context);
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                jsonResponse=response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"ERROR",Toast.LENGTH_SHORT).show();
                Log.d("ERROR: ", error.toString());
            }
        });
        queue.add(request).setRetryPolicy(new DefaultRetryPolicy());
    }
}
