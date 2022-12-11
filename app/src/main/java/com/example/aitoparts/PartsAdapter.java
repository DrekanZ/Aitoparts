package com.example.aitoparts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PartsAdapter extends RecyclerView.Adapter<PartsAdapter.PartsViewHolder> {

    private Context mCtx;
    private List<Parts> partsList;

    public PartsAdapter(Context mCtx, List<Parts> partsList) {
        this.mCtx = mCtx;
        this.partsList = partsList;
    }


    @Override
    public PartsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recyclerpartslayout, null);
        return new PartsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PartsViewHolder holder, int position) {
        Parts parts = partsList.get(position);

        holder.textViewPartsTitle.setText(parts.getName());
        holder.textViewPartsPrice.setText("Rp. " + parts.getPrice());
        holder.textViewStockParts.setText(String.valueOf(parts.getInStock()));

        Glide.with(mCtx).load(parts.getImageLink()).into(holder.imageViewParts);

    }

    @Override
    public int getItemCount() {
        return partsList.size();
    }

    class PartsViewHolder extends RecyclerView.ViewHolder {

            ImageView imageViewParts;
            TextView textViewStockParts, textViewPartsTitle, textViewPartsPrice;

            public PartsViewHolder(View itemView) {
                super(itemView);

                imageViewParts = itemView.findViewById(R.id.imageViewParts);
                textViewStockParts = itemView.findViewById(R.id.stockValue);
                textViewPartsPrice = itemView.findViewById(R.id.textViewPrice);
                textViewPartsTitle = itemView.findViewById(R.id.textViewTitleParts);


            }
        }

}
