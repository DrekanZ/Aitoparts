package com.example.aitoparts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TipsActivity extends AppCompatActivity implements RecyclerTipsInterface {

    ConstraintLayout backButtonTips;
    RecyclerView recyclerView;

    List<Tips> tipsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Aitoparts);
        setContentView(R.layout.activity_tips);

        backButtonTips = (ConstraintLayout) findViewById(R.id.tipsBackButton);
        backButtonTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tipsBack = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(tipsBack);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerTips);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tipsList = new ArrayList<>();


        loadTips();


    }


    private void loadTips() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, DbContract.SERVER_LOADTIPS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject tip = array.getJSONObject(i);

                                //adding the product to product list
                                tipsList.add(new Tips(
                                        tip.getInt("id"),
                                        tip.getString("title"),
                                        tip.getString("image"),
                                        tip.getString("teks")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            TipsAdapter adapter = new TipsAdapter(TipsActivity.this, tipsList, TipsActivity.this);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void onItemClick(int position) {
//        Toast.makeText(this, tipsList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(TipsActivity.this,TipsPage.class);
        intent.putExtra("id",String.valueOf(tipsList.get(position).getId()));
        startActivity(intent);
    }
}