package com.example.aitoparts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Riwayat extends AppCompatActivity implements RecyclerClickInterface{

    ArrayList<Book> bookList;
    RecyclerView recyclerView;

    ConstraintLayout backButtonRiwayat;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);
        sharedPreferences = getSharedPreferences("loginSession",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerRiwayat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        backButtonRiwayat = (ConstraintLayout) findViewById(R.id.riwayatBackButton);
        backButtonRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Riwayat.this,MainActivity.class);
                startActivity(intent);
            }
        });

        loadBook(sharedPreferences.getString("user_id",""));
//        Toast.makeText(this, sharedPreferences.getString("user_id",""), Toast.LENGTH_SHORT).show();
    }


    private BookFragment.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(BookFragment.OnItemClickListener listener)
    {
        onItemClickListener = listener;
    }

    private void loadBook(final String id) {
        bookList = new ArrayList<>();

        if (id.equals("fail"))
        {
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_LOADRIWAYAT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject book = array.getJSONObject(i);

                                //adding the product to product list
                                bookList.add(new Book(
                                        book.getInt("id"),
                                        book.getString("nama"),
                                        book.getString("mobil"),
                                        book.getString("tanggal"),
                                        book.getString("jam"),
                                        book.getString("namamekanik")
                                ));


                            }
                            HistoryAdapter historyAdapter = new HistoryAdapter(Riwayat.this,bookList,Riwayat.this);
                            recyclerView.setAdapter(historyAdapter);
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
        Volley.newRequestQueue(Riwayat.this).add(stringRequest);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(Riwayat.this,RiwayatDetails.class);
//        intent.putExtra("position",String.valueOf(position));
        intent.putExtra("nomor", String.valueOf(bookList.get(position).getId()));
        intent.putExtra("nama",bookList.get(position).getNama());
        intent.putExtra("kendaraan",bookList.get(position).getMobil());
        intent.putExtra("tanggal",bookList.get(position).getTanggal());
        intent.putExtra("jam",bookList.get(position).getJam());
        intent.putExtra("montir", bookList.get(position).getNamaMontir());
        startActivity(intent);
    }
}