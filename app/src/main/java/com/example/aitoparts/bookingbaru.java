package com.example.aitoparts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;



public class bookingbaru extends AppCompatActivity {

    //datePicker
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    ConstraintLayout pilihTanggal;


    // Spinner sesi dan paket
    public Spinner sp1;
    public Spinner sp2;

    // menu back
    ConstraintLayout backToMainBooking;



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


        //spinner
        sp1 = (Spinner) findViewById(R.id.pilihSesiSpiner);
        sp2 = (Spinner) findViewById(R.id.pilihPaketSpiner);

        //Untuk membuat List Kota atau bisa menggunakan String[]
        List<String> itemSesi = new ArrayList<>();
        itemSesi.add("Sesi 1");
        itemSesi.add("Sesi 2");
        itemSesi.add("Sesi 3");
        itemSesi.add("Sesi 4");

        ArrayAdapter<String> adapterSesi = new ArrayAdapter<String>(bookingbaru.this,android.R.layout.simple_spinner_dropdown_item, itemSesi);
        adapterSesi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapterSesi);

        //Untuk membuat List Kota atau bisa menggunakan String[]
        List<String> itemPaket = new ArrayList<>();
        itemPaket.add("1000 KM");
        itemPaket.add("3000 KM");
        itemPaket.add("5000 KM");
        itemPaket.add("10.000 KM");
        itemPaket.add("10.000 + KM");


        ArrayAdapter<String> adapterPaket = new ArrayAdapter<String>(bookingbaru.this,android.R.layout.simple_spinner_dropdown_item, itemPaket);
        adapterPaket.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adapterPaket);




        //Pilih Tanggal
        pilihTanggal = findViewById(R.id.pilihTanggal);

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TextView tanggal = findViewById(R.id.targetTanggal);
                String myFormat = "dd-MMMM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                tanggal.setText(sdf.format(myCalendar.getTime()));
            }
        };

        pilihTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(bookingbaru.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }




}





