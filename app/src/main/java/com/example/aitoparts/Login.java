package com.example.aitoparts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    EditText loginUsername, loginPassword;
    Button buttonLogin;
    TextView gotoRegister;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("loginSession",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        progressDialog = new ProgressDialog(Login.this);
        loginUsername = (EditText) findViewById(R.id.textInputUsername);
        loginPassword = (EditText) findViewById(R.id.textInputPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        gotoRegister = (TextView) findViewById(R.id.textViewToRegister);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sUsername = loginUsername.getText().toString();
                String sPassword = loginPassword.getText().toString();

                checkLoginToServer(sUsername,sPassword);
            }
        });

        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRegister = new Intent(Login.this, Register.class);
                startActivity(toRegister);
            }
        });

    }

    private void checkLoginToServer(final String username, final String password) {
        if (checkNetworkConnection()) {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if (resp.equals("[{\"status\":\"OK\"}]")) {
                                    Toast.makeText(getApplicationContext(),"Login Berhasil", Toast.LENGTH_SHORT).show();
                                    getUserCred(username);

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
                    params.put("password", password);
                    return params;
                }
            };

            VolleyConnection.getInstance(Login.this).addToRequestQue(stringRequest);

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


    private void getUserCred(final String username) {
        if (checkNetworkConnection()) {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_GETUSERCRED_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);

                                JSONObject jsonObject = jsonArray.getJSONObject(0);

                                editor.putBoolean("logged_in",true);
                                editor.putString("nama",jsonObject.getString("nama"));
                                editor.putString("user_id",jsonObject.getString("id"));

                                while (!editor.commit())
                                {
                                    editor.commit();

                                }
                                Intent loginIntent = new Intent(Login.this, MainActivity.class);
                                startActivity(loginIntent);
//                                loginUsername.setText(jsonObject.getString("nama"));
//                                Toast.makeText(Login.this, jsonObject.getString("nama"), Toast.LENGTH_SHORT).show();
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
                    return params;
                }
            };

            VolleyConnection.getInstance(Login.this).addToRequestQue(stringRequest);

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
}