package com.example.aitoparts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TipsPage extends AppCompatActivity {

    ImageView imageViewTitleTipsPage;
    TextView textViewTitleTipsPage;
    TextView textViewContentTipsPage;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_page);

        imageViewTitleTipsPage = (ImageView) findViewById(R.id.imageViewTipsPage);
        textViewTitleTipsPage = (TextView) findViewById(R.id.textViewTitleTipsPage);
        textViewContentTipsPage = (TextView) findViewById(R.id.textViewContentTipsPage);

        ConstraintLayout backTipsPage = (ConstraintLayout) findViewById(R.id.tipsPageBackButton);
        backTipsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TipsActivity.class);
                startActivity(intent);
            }
        });

        id = getIntent().getStringExtra("id");

        loadTipsPage();
    }

    private void loadTipsPage() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_LOADTIPSPAGE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            JSONObject tip = array.getJSONObject(0);

                            textViewTitleTipsPage.setText(tip.getString("title"));
                            textViewContentTipsPage.setText(tip.getString("teks"));

                            Glide.with(getApplicationContext()).load(tip.getString("image")).into(imageViewTitleTipsPage);


//                            TipsAdapter adapter = new TipsAdapter(TipsActivity.this, tipsList, TipsActivity.this);
//                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}