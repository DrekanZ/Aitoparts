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

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    Context context;
    ArrayList<Book> bookArrayList;
    RecyclerClickInterface onItemClickListener;

    public HistoryAdapter(Context context, ArrayList<Book> bookArrayList, RecyclerClickInterface onItemClickListener) {
        this.context = context;
        this.bookArrayList = bookArrayList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.recyclerbooklayout,null);
        return new HistoryViewHolder(v,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        Book book = bookArrayList.get(position);
        holder.textView.setText("Janji Booking Pemeriksaan Kendaraan\n\n" + book.getMobil() + "\n" + book.getTanggal() + "\n" + book.getJam());

    }


    @Override
    public int getItemCount() {
        return bookArrayList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ConstraintLayout detailsButton;

        public HistoryViewHolder(@NonNull View itemView, RecyclerClickInterface recyclerTipsInterface) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.textViewBookMessage);
            detailsButton = (ConstraintLayout) itemView.findViewById(R.id.buttonBookDetails);

            detailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerTipsInterface != null)
                    {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION)
                        {
                            recyclerTipsInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
