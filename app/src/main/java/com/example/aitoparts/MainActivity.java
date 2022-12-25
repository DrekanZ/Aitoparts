package com.example.aitoparts;

import static com.example.aitoparts.BookFragment.bookList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.aitoparts.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements BookFragment.OnItemClickListener {

    ActivityMainBinding binding;
    BookFragment bookFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Aitoparts);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        bookFragment = new BookFragment();
        HomeFragment homeFragment = new HomeFragment();
        ProfileFragment profileFragment = new ProfileFragment();

        binding.bottomNavigationView.setSelectedItemId(R.id.home);
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
        String text = bookList.get(position).getMobil();
        Toast.makeText(this,text, Toast.LENGTH_SHORT).show();
    }
}