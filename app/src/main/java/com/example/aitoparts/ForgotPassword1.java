package com.example.aitoparts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword1 extends AppCompatActivity {

    Button lupaPassword;
    ConstraintLayout backToLogin;

    EditText editTextUsername, editTextHari, editTextBulan, editTextTahun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password1);

        backToLogin = (ConstraintLayout) findViewById(R.id.lupaPasswordBackButton);

        lupaPassword = (Button) findViewById(R.id.btnLupaPassword);
        editTextUsername = (EditText) findViewById(R.id.usernameLupaPassword);
        editTextHari = (EditText) findViewById(R.id.lupaPasswordHari);
        editTextBulan = (EditText) findViewById(R.id.lupaPasswordBulan);
        editTextTahun = (EditText) findViewById(R.id.lupaPasswordTahun);

        lupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString().trim();
                String hari = editTextHari.getText().toString().trim();
                String bulan = editTextBulan.getText().toString().trim();
                String tahun = editTextTahun.getText().toString().trim();

                if (username.isEmpty() || hari.isEmpty() || bulan.isEmpty() || tahun.isEmpty()) {
                    Toast.makeText(ForgotPassword1.this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show();
                } else {
                    String tanggal = tahun+"-"+bulan+"-"+hari;
                    checkLupa(tanggal,username);
                }
            }
        });
    }


    private void checkLupa(String date, String username) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,DbContract.SERVER_LUPAPASSWORD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    String status = json.optString("status");
                    if (status.equals("success")){
                        Intent intent = new Intent(ForgotPassword1.this, ForgotPassword2.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                    } else
                    {
                        Toast.makeText(ForgotPassword1.this, "Tidak ditemukan pasangan yang cocok", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ForgotPassword1.this, "Server Response Error", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("date",date);
                params.put("username",username);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

}