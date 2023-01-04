package com.example.aitoparts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword2 extends AppCompatActivity {

    Button changePassword;
    EditText editTextPassword, editTextConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password2);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        changePassword = (Button) findViewById(R.id.btnGantiPasswordLupa);
        editTextPassword = (EditText) findViewById(R.id.editTextPasswordLupa);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPasswordLupa);

        changePassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String sPassword = editTextPassword.getText().toString().trim();
                String sConfPassword = editTextConfirmPassword.getText().toString().trim();

                if (!PasswordValidator.isValid(sPassword))
                {
                    editTextPassword.setError("Password harus berisi huruf kecil, huruf kapital, nomor, dan simbol\nDengan panjang 8 - 20 karakter");
                    editTextPassword.requestFocus();
                    return;
                }

                if (sPassword.equals(sConfPassword)&& !sPassword.equals("")) {
                    changePasswordLupa(username,sPassword);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Password Tidak Cocok", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void btnMasukLUpaPassword(View view) {
        Intent baliklogin = new Intent(ForgotPassword2.this, Login.class);
        startActivity(baliklogin);
    }

    private void changePasswordLupa(String username, String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.SERVER_CHANGEPASSWORD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Password berhasil dirubah"))
                {
                    Toast.makeText(ForgotPassword2.this, response, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgotPassword2.this,Login.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ForgotPassword2.this, response, Toast.LENGTH_SHORT).show();
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
                params.put("password",password);
                params.put("username",username);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

}