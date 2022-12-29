package com.example.aitoparts;

import static com.example.aitoparts.BookFragment.bookList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.aitoparts.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements BookFragment.OnItemClickListener {

    ActivityMainBinding binding;
    BookFragment bookFragment;
    HomeFragment homeFragment;
    ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Aitoparts);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setSelectedItemId(R.id.home);
        bookFragment = new BookFragment();
        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();

        if (getIntent().getBooleanExtra("toBook",false))
        {
            replaceFragment(bookFragment);
            binding.bottomNavigationView.setSelectedItemId(R.id.booking);
        }

        binding.bottomNavigationView.setOnItemSelectedListener(item ->{

                switch (item.getItemId()) {
                    case R.id.booking:
                        replaceFragment(bookFragment);
                        break;
                    case R.id.home:
                        replaceFragment(homeFragment);
                        break;
                    case R.id.profile:
                        replaceFragment(profileFragment);
                        break;
                }
                        return true;
    });


        bookFragment.setOnItemClickListener(this);
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(MainActivity.this,BookDetails.class);
        intent.putExtra("position",String.valueOf(position));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Keluar dari aplikasi?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Exit the app if the user confirms
                        finishAffinity();
                    }
                })
                .setNegativeButton("Tidak", null)
                .show();
    }
}