package com.example.aitoparts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;


public class BookingBaru extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    //datePicker
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    int freeMekanik;
    ConstraintLayout pilihTanggal;


    // Spinner sesi dan paket
    public Spinner sp1;
    public Spinner sp2;

    // menu back
    ConstraintLayout backToMainBooking;

    boolean initDate;
    String sendDate, sendKendaraan, sendIdMekanik, sendIdPelanggan, sendIdSesi,sendIdPaket;
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
        initDate = false;

        sharedPreferences = getSharedPreferences("loginSession",MODE_PRIVATE);
        editor = sharedPreferences.edit();


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

                if (jeniskendaraankirim.getText() != null && initDate ==true)
                {
                    getFreeMekanikId(sendDate, String.valueOf(getSesiId(sp1.getSelectedItem().toString())));
                }
                else
                {
                    Toast.makeText(BookingBaru.this, "Tolong masukan semua kolom", Toast.LENGTH_SHORT).show();
                }
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
                biaya.setText("Rp. " + getPaketHarga(sp2.getSelectedItem().toString()) + "000");
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
                    sendDate = year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
                    initDate = true;
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

    private String getPaketHarga(String nama)
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

    private String getPaketId(String nama)
    {
        String result ="";

        for (Paket paket : paketList) {
            if (paket.getNamaPaket().equals(nama)) {
                result = paket.getId();
                break;
            }
        }
        return result;
    }

    private int getSesiId(String nama)
    {
        String result ="";

        for (Sesi sesi : sesiList) {
            if (sesi.getJam().equals(nama)) {
                result = sesi.getId();
                break;
            }
        }
        return Integer.parseInt(result);
    }


    private void getFreeMekanikId(final String tanggal, final String id_sesi) {
        StringRequest request = new StringRequest(Request.Method.POST, DbContract.SERVER_GETMEKANIK_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    if (array.length() > 0)
                    {
                        JSONObject object = array.getJSONObject(0);
                        freeMekanik = object.getInt("id");
                        setSendStrings();
                        bookingBaru(sendKendaraan,sendDate,sendIdSesi,sendIdPaket,sendIdPelanggan,sendIdMekanik);
                    } else {
                        Toast.makeText(BookingBaru.this, "Tidak ada Mekanik Tersedia.\nSilakan Pilih Sesi atau Tanggal lain", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Error handling
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
                params.put("id_sesi", id_sesi);
                params.put("tanggal", tanggal);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    private void bookingBaru(final String kendaraan, final String tanggal, final String id_sesi, final String id_paket, final String id_pelanggan, final String id_mekanik) {
        StringRequest request = new StringRequest(Request.Method.POST, DbContract.SERVER_BOOKINGBARU_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int id = jsonObject.getInt("id");

                    Intent intent = new Intent(BookingBaru.this,BookingDibuat.class);
                    intent.putExtra("booking_id",String.valueOf(id));
                    startActivity(intent);
                    Toast.makeText(BookingBaru.this, String.valueOf(id), Toast.LENGTH_SHORT).show();
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
                params.put("id_sesi", id_sesi);
                params.put("tanggal", tanggal);
                params.put("kendaraan", kendaraan);
                params.put("id_paket", id_paket);
                params.put("id_pelanggan", id_pelanggan);
                params.put("id_mekanik", id_mekanik);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    private void setSendStrings()
    {
        sendIdMekanik =  String.valueOf(freeMekanik);
        sendIdPaket = getPaketId(sp2.getSelectedItem().toString());
        sendIdPelanggan = sharedPreferences.getString("user_id","0");
        sendIdSesi = String.valueOf(getSesiId(sp1.getSelectedItem().toString()));
        sendKendaraan = jeniskendaraankirim.getText().toString();
    }

}





