package com.example.aitoparts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;


public class BookingBaru extends AppCompatActivity {

    //datePicker
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    ConstraintLayout pilihTanggal;


    // Spinner sesi dan paket
    public Spinner sp1;
    public Spinner sp2;

    // menu back
    ConstraintLayout backToMainBooking;

    //butoonsumbit
    EditText jeniskendaraankirim;
    TextView tanggalkirim, biaya;
    ConstraintLayout btnbookingbaru;
    private String KEY_KENDARAAN = "JenisKendaaran";
    private String KEY_TANGGAL = "Tangal";
    List<Sesi> sesiList;
    List<String> sesiString;

    List<Paket> paketList;
    List<String> paketString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookingbaru);



        // back
        backToMainBooking = (ConstraintLayout) findViewById(R.id.backMainBooking);
        backToMainBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToMainBooking = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(backToMainBooking);
            }
        });

        //button booking baru
        jeniskendaraankirim = (EditText) findViewById(R.id.hasilJeniskendaraan);
        biaya = (TextView) findViewById(R.id.estimasiBiayaText);
        tanggalkirim = (TextView) findViewById(R.id.targetTanggal);
        btnbookingbaru = (ConstraintLayout) findViewById(R.id.buttonBuatbooking);
        btnbookingbaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String jeniskendaraan = jeniskendaraankirim.getText().toString();
                    {Intent kirimjenisK = new Intent(BookingBaru.this, BookingDibuat.class);
                    kirimjenisK.putExtra(KEY_KENDARAAN, jeniskendaraan);
                    startActivity(kirimjenisK);}

                    String tanggal = tanggalkirim.getText().toString();
                    {Intent tanggal123 = new Intent(BookingBaru.this, BookingDibuat.class);
                    tanggal123.putExtra(KEY_TANGGAL,tanggal);
                    startActivity(tanggal123);}

            }
        });



        //spinner
        sp1 = (Spinner) findViewById(R.id.pilihSesiSpiner);
        sp2 = (Spinner) findViewById(R.id.pilihPaketSpiner);

        //Untuk membuat List Kota atau bisa menggunakan String[]
        sesiList = new ArrayList<>();
        sesiString = new ArrayList<>();
        loadSesi();

        paketList = new ArrayList<>();
        paketString = new ArrayList<>();
        loadPaket();

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                biaya.setText("Rp. " + getHarga(sp2.getSelectedItem().toString()) + "000");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






        myCalendar = Calendar.getInstance();
        pilihTanggal = findViewById(R.id.pilihTanggal);

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                if (myCalendar.before(Calendar.getInstance()))
                {
                    Toast.makeText(BookingBaru.this, "Tidak bisa booking ke masa lalu", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    TextView tanggal = findViewById(R.id.targetTanggal);
                    String myFormat = "dd-MMMM-yyyy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    tanggal.setText(sdf.format(myCalendar.getTime()));
                }
            }
        };

        pilihTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(BookingBaru.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }



    private void loadSesi() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, DbContract.SERVER_LOADSESI_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            JSONObject sesi;

                            for (int i = 0; i < array.length(); i++) {

                                sesi = array.getJSONObject(i);


                                //adding the product to product list
                                sesiList.add(new Sesi(sesi.getString("id"),sesi.getString("jam")));
                                sesiString.add(sesi.getString("jam"));
                            }

                            ArrayAdapter<String> adapterSesi = new ArrayAdapter<String>(BookingBaru.this,android.R.layout.simple_spinner_dropdown_item, sesiString);
                            adapterSesi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp1.setAdapter(adapterSesi);


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
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void loadPaket() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, DbContract.SERVER_LOADPAKET_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            JSONObject paket;

                            for (int i = 0; i < array.length(); i++) {

                                paket = array.getJSONObject(i);


                                //adding the product to product list
                                paketList.add(new Paket(paket.getString("id"),paket.getString("nama"),paket.getString("harga")));
                                paketString.add(paket.getString("nama"));
                            }

                            ArrayAdapter<String> adapterPaket = new ArrayAdapter<String>(BookingBaru.this,android.R.layout.simple_spinner_dropdown_item, paketString);
                            adapterPaket.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp2.setAdapter(adapterPaket);


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
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private String getHarga(String nama)
    {
        String result ="";

        for (Paket paket : paketList) {
            if (paket.getNamaPaket().equals(nama)) {
                result = paket.getHarga();
                break;
            }
        }
        return result;
    }
}





