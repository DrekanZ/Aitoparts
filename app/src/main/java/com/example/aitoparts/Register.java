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
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    EditText username, nama, password, confPassword, noTelp;
    Button registerButton;
    TextView gotoLogin;
    ProgressDialog progressDialog;

    EditText edittextDayCal, edittextMonthCal, edittextYearCal;

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

        edittextDayCal = (EditText) findViewById(R.id.edittextDayCal);
        edittextMonthCal = (EditText) findViewById(R.id.edittextMonthCal);
        edittextYearCal = (EditText) findViewById(R.id.edittextYearCal);

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
                String year = edittextYearCal.getText().toString();
                String month = edittextMonthCal.getText().toString();
                String day = edittextDayCal.getText().toString();

                if (!sUsername.equals("")&&!sPassword.equals("")&&!sName.equals("")&&!sNoTelp.equals("") && !year.equals("") && !day.equals("") && !    month.equals(""))
                {

                    if (!validateInput())
                    {
                        return;
                    }

                    String sTglLahir = year + "-" + month + "-" + day;
                    if (8 < sNoTelp.length() && sNoTelp.length() > 14)
                    {
                        noTelp.requestFocus();
                        Toast.makeText(Register.this, "Panjang nomor harus dari 9 - 13", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (sPassword.equals(sConfPassword)&& !sPassword.equals("")) {
                        createDataToServer(sUsername,sName,sPassword, sNoTelp, sTglLahir);
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

    public void createDataToServer(final String username, final String name, final String password, final String notelp, final String tanggalLahir) {
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
                    params.put("tgl_lahir",tanggalLahir);
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

    private boolean validateInput() {
        // Get the input values
        String day = edittextDayCal.getText().toString();
        String month = edittextMonthCal.getText().toString();
        String year = edittextYearCal.getText().toString();

        // Validate the input
        if (day.isEmpty() || month.isEmpty() || year.isEmpty()) {
            // One or more input fields are empty, show an error message and request focus on the first empty field
            if (day.isEmpty()) {
                edittextDayCal.setError("This field is required");
                edittextDayCal.requestFocus();
            } else if (month.isEmpty()) {
                edittextMonthCal.setError("This field is required");
                edittextMonthCal.requestFocus();
            } else {
                edittextYearCal.setError("This field is required");
                edittextYearCal.requestFocus();
            }
            return false;
        }

        int dayValue = Integer.parseInt(day);
        int monthValue = Integer.parseInt(month);
        int yearValue = Integer.parseInt(year);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        if (dayValue < 1 || dayValue > 31) {
            // Day input is not valid, show an error message and request focus on the day field
            edittextDayCal.setError("Input is not valid");
            edittextDayCal.requestFocus();
            return false;
        }

        if (monthValue < 1 || monthValue > 12) {
            // Month input is not valid, show an error message and request focus on the month field
            edittextMonthCal.setError("Input is not valid");
            edittextMonthCal.requestFocus();
            return false;
        }

        if (yearValue < 1900 || yearValue > currentYear) {
            // Year input is not valid, show an error message and request focus on the year field
            edittextYearCal.setError("Input is not valid");
            edittextYearCal.requestFocus();
            return false;
        }

        // Input is valid
        return true;
    }

}


