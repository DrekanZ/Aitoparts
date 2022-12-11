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

public class PartsActivity extends AppCompatActivity {

    RecyclerView recyclerViewParts;
    PartsAdapter adapterParts;

    List<Parts> partsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parts);

        ConstraintLayout partsBackButton = (ConstraintLayout) findViewById(R.id.partsBackButton);
        partsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        partsList = new ArrayList<>();
        recyclerViewParts = (RecyclerView) findViewById(R.id.recyclerParts);
        recyclerViewParts.setHasFixedSize(true);

        recyclerViewParts.setLayoutManager(new LinearLayoutManager(this));

        loadParts();


    }

    private void loadParts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, DbContract.SERVER_LOADPARTS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray partsJson = new JSONArray(response);

                    for(int i=0;i<partsJson.length();i++)
                    {
                        JSONObject partsObject = partsJson.getJSONObject(i);

                        int id = partsObject.getInt("id");
                        String title = partsObject.getString("title");
                        int price = partsObject.getInt("price");
                        int stock = partsObject.getInt("stock");
                        String imageLink = partsObject.getString("image");

                        Parts parts = new Parts(id, title, price, stock, imageLink);
                        partsList.add(parts);
                    }

                    adapterParts = new PartsAdapter(PartsActivity.this, partsList);
                    recyclerViewParts.setAdapter(adapterParts);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PartsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }

}