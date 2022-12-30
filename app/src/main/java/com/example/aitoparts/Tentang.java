package com.example.aitoparts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Tentang extends AppCompatActivity {

    ConstraintLayout tentangBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);

        tentangBackButton = (ConstraintLayout) findViewById(R.id.tentangBackButton);
        tentangBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tentang.this,MainActivity.class);
                intent.putExtra("toProfile",true);
                startActivity(intent);
            }
        });

    }
}