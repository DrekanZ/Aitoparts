package com.example.aitoparts;

import static com.example.aitoparts.BookFragment.bookList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RiwayatDetails extends AppCompatActivity {

    TextView textViewNomor, textViewNama, textViewKendaraan, textViewTanggal, textViewJam, textViewMontir;
    ConstraintLayout riwayatDetailsBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_details);

        riwayatDetailsBackButton = (ConstraintLayout) findViewById(R.id.riwayatDetailsBackButton);
        textViewNomor = (TextView) findViewById(R.id.riwayatDetailsNomor);
        textViewNama = (TextView) findViewById(R.id.riwayatDetailsNama);
        textViewKendaraan = (TextView) findViewById(R.id.riwayatDetailsKendaraan);
        textViewTanggal = (TextView) findViewById(R.id.riwayatDetailsTanggal);
        textViewJam = (TextView) findViewById(R.id.riwayatDetailsJam);
        textViewMontir = (TextView) findViewById(R.id.riwayatDetailsMontir);

        Intent intent = getIntent();
        String nomor = intent.getStringExtra("nomor");
        String nama = intent.getStringExtra("nama");
        String kendaraan = intent.getStringExtra("kendaraan");
        String tanggal = intent.getStringExtra("tanggal");
        String jam = intent.getStringExtra("jam");
        String montir = intent.getStringExtra("montir");

        textViewNomor.setText(nomor);
        textViewNama.setText(nama);
        textViewKendaraan.setText(kendaraan);
        textViewTanggal.setText(tanggal);
        textViewJam.setText(jam);
        textViewMontir.setText(montir);

        riwayatDetailsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RiwayatDetails.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}