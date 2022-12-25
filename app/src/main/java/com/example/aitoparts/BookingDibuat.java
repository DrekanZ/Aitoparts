    package com.example.aitoparts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

    public class BookingDibuat extends AppCompatActivity {


        ConstraintLayout backtomainmenu;

        TextView nomor, nama, kendaraan, tanggal, jam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookingdibuat);

        nomor = (TextView) findViewById(R.id.noBooking);
        nama = (TextView) findViewById(R.id.namapemboking);
        kendaraan = (TextView) findViewById(R.id.jeniskendaraanbaruT);
        tanggal = (TextView) findViewById(R.id.tanggalbaru);
        jam = (TextView) findViewById(R.id.jambaru);

        Intent intent = getIntent();
        String id = intent.getStringExtra("booking_id");
        bookingBaru(id);

        // back
        backtomainmenu = (ConstraintLayout) findViewById(R.id.backBokingBaru);
        backtomainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToMainBooking = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(backToMainBooking);
                BookingDibuat.this.finish();
            }
        });

    }

        private void bookingBaru(final String id) {
            StringRequest request = new StringRequest(Request.Method.POST, DbContract.SERVER_BOOKINGSUCCESS_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        JSONObject object = jsonArray.getJSONObject(0);

                        nomor.setText(String.valueOf(object.getInt("id")));
                        nama.setText(object.getString("nama"));
                        kendaraan.setText(object.getString("mobil"));
                        tanggal.setText(object.getString("tanggal"));
                        jam.setText(object.getString("jam"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Error handling
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("booking_id", id);
                    return params;
                }
            };

            Volley.newRequestQueue(this).add(request);
        }
}