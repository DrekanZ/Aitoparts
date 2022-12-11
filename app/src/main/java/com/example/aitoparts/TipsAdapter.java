package com.example.aitoparts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.TipsViewHolder> {
    private final RecyclerTipsInterface recyclerTipsInterface;

    private Context mCtx;
    private List<Tips> tipsList;

    public TipsAdapter(Context mCtx, List<Tips> tipsList, RecyclerTipsInterface recyclerTipsInterface) {
        this.mCtx = mCtx;
        this.tipsList = tipsList;
        this.recyclerTipsInterface = recyclerTipsInterface;
    }

    @NonNull
    @Override
    public TipsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recyclertipslayout,null);
        return new TipsViewHolder(view, recyclerTipsInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull TipsViewHolder holder, int position) {
        Tips tips = tipsList.get(position);
        holder.textView.setText(tips.getText());
        holder.textViewTitle.setText(tips.getTitle());

        Glide.with(mCtx).load(tips.getImageLink()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return tipsList.size();

    }

    public static class TipsViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView, textViewTitle;

        public TipsViewHolder(View itemView, RecyclerTipsInterface recyclerTipsInterface) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageTipsRecycler);
            textView = (TextView) itemView.findViewById(R.id.textViewTipsRecycler);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitleRecycler);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerTipsInterface != null) {
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
