package com.example.aitoparts;

import static com.example.aitoparts.BookFragment.bookList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BookDetails extends AppCompatActivity {

    TextView textViewNomor, textViewNama, textViewKendaraan, textViewTanggal, textViewJam, textViewMontir;
    ConstraintLayout bookDetailsBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        bookDetailsBackButton = (ConstraintLayout) findViewById(R.id.bookDetailsBackButton);
        textViewNomor = (TextView) findViewById(R.id.bookDetailsNomor);
        textViewNama = (TextView) findViewById(R.id.bookDetailsNama);
        textViewKendaraan = (TextView) findViewById(R.id.bookDetailsKendaraan);
        textViewTanggal = (TextView) findViewById(R.id.bookDetailsTanggal);
        textViewJam = (TextView) findViewById(R.id.bookDetailsJam);
        textViewMontir = (TextView) findViewById(R.id.bookDetailsMontir);

        Intent intent = getIntent();
        int position = Integer.parseInt(intent.getStringExtra("position"));

        textViewNomor.setText(String.valueOf(bookList.get(position).getId()));
        textViewNama.setText(bookList.get(position).getNama());
        textViewKendaraan.setText(bookList.get(position).getMobil());
        textViewTanggal.setText(bookList.get(position).getTanggal());
        textViewJam.setText(bookList.get(position).getJam());
        textViewMontir.setText(bookList.get(position).getNamaMontir());

        bookDetailsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookDetails.this,MainActivity.class);
                intent.putExtra("toBook",true);
                startActivity(intent);
            }
        });
    }
}