package com.example.aitoparts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{

    Context context;
    ArrayList<Book> bookArrayList;


    public BookAdapter(Context context, ArrayList<Book> bookList) {
        this.context = context;
        this.bookArrayList = bookList;
    }

    public BookAdapter() {

    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recyclerbooklayout,parent,false);

        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookArrayList.get(position);
        holder.textView.setText("Janji Booking Pemeriksaan Kendaraan\n\n" + book.mobil + "\n" + book.tanggal + "\n" + book.jam);
    }

    @Override
    public int getItemCount() {
        return bookArrayList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ConstraintLayout buttonDetails;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textViewBookMessage);
            buttonDetails = itemView.findViewById(R.id.buttonBookDetails);
        }
    }
}
