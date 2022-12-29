package com.example.aitoparts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText username, nama, password, confPassword, noTelp;
    Button registerButton;
    TextView gotoLogin;
    ProgressDialog progressDialog;


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sharedPreferences = getSharedPreferences("loginSession",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        checkSession();

        username = (EditText) findViewById(R.id.editTextUsernameRegister);
        nama = (EditText) findViewById(R.id.editTextNameRegister);
        password = (EditText) findViewById(R.id.editTextPasswordRegister);
        confPassword = (EditText) findViewById(R.id.editTextKonfirmasiPasswordRegister);
        noTelp = (EditText) findViewById(R.id.editTextTeleponRegister);
        registerButton = (Button) findViewById(R.id.buttonRegister);
        gotoLogin = (TextView) findViewById(R.id.textViewToLogin);
        progressDialog = new ProgressDialog(Register.this);

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(Register.this,Login.class);
                startActivity(loginIntent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sUsername = username.getText().toString();
                String sPassword = password.getText().toString();
                String sName = nama.getText().toString();
                String sConfPassword = confPassword.getText().toString();
                String sNoTelp = noTelp.getText().toString();

                if (!sUsername.equals("")&&!sPassword.equals("")&&!sName.equals("")&&!sNoTelp.equals(""))
                {
                    if (sPassword.equals(sConfPassword)&& !sPassword.equals("")) {
                        createDataToServer(sUsername,sName,sPassword, sNoTelp);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Password Tidak Cocok", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Semua kolom harus diisi!       ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void createDataToServer(final String username, final String name, final String password, final String notelp) {
        if (checkNetworkConnection()) {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (resp.equals("[{\"status\":\"OK\"}]")) {
                                    Toast.makeText(getApplicationContext(),"Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                                    Intent loginIntent = new Intent(Register.this,Login.class);
                                    startActivity(loginIntent);
                                } else {
                                    Toast.makeText(getApplicationContext(), resp,Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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
                    params.put("username", username);
                    params.put("name", name);
                    params.put("password", password);
                    params.put("notelp", notelp);
                    return params;
                }
            };

            VolleyConnection.getInstance(Register.this).addToRequestQue(stringRequest);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.cancel();
                }
            }, 2000);
        } else
        {
            Toast.makeText(getApplicationContext(),"Koneksi Gagal", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
        return ((networkinfo != null) && (networkinfo.isConnected()));
    }

    private void checkSession()
    {
        if (sharedPreferences.getBoolean("logged_in",false))
        {
            Intent intent = new Intent(Register.this,MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Register.this,Login.class);
        startActivity(intent);
    }

}