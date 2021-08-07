package com.knowhouse.comichouseapp.RecyclerViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.knowhouse.comichouseapp.Data.Comics;
import com.knowhouse.comichouseapp.Data.Marvel;
import com.knowhouse.comichouseapp.Interfaces.RecyclerViewClickInterface;
import com.knowhouse.comichouseapp.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<Marvel> localDataSet;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final CardView cardView;
        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }

    }

    public CustomAdapter(ArrayList<Marvel> dataSet, RecyclerViewClickInterface recyclerViewClickInterface){
        localDataSet = dataSet;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }


    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_cardview,parent,false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.comic_thumbnail);
        TextView textView = cardView.findViewById(R.id.comic_title);

        Marvel marvelContent = localDataSet.get(position);

        String comicTitle = marvelContent.getName();
        String comicId = marvelContent.getId();
        String thumbnailUrl = marvelContent.getImageUrl();
        final String comicUrl = marvelContent.getResourceUrl();

        Glide.with(cardView).load(thumbnailUrl).into(imageView);
        textView.setText(comicTitle);
        cardView.setOnClickListener(view->{
            recyclerViewClickInterface.onItemClick(position,comicUrl,view);
        });

    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
