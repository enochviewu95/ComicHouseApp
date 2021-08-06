package com.knowhouse.comichouseapp.RecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knowhouse.comichouseapp.Data.Category;
import com.knowhouse.comichouseapp.Data.ComicSeries;
import com.knowhouse.comichouseapp.Data.Comics;
import com.knowhouse.comichouseapp.Data.Creators;
import com.knowhouse.comichouseapp.Data.Marvel;
import com.knowhouse.comichouseapp.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private ArrayList<ArrayList<Marvel>> categoryDataSet;
    private CustomAdapter customAdapter;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final LinearLayout linearlayoutView;

        public ViewHolder(LinearLayout linearLayout){
            super(linearLayout);
            this.linearlayoutView = linearLayout;

        }
    }

    public CategoryAdapter(ArrayList<ArrayList<Marvel>> categoryDataSet, Context context) {
        this.categoryDataSet = categoryDataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_category_view,parent,false);
        return new ViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinearLayout linearLayout = holder.linearlayoutView;
        String[] names = {"Preview","Series","Creators"};
        TextView categoryTitle = linearLayout.findViewById(R.id.category_title);
        RecyclerView recyclerView = linearLayout.findViewById(R.id.category_recycler);
        categoryTitle.setText(names[position]);
        ArrayList<Marvel> categories = categoryDataSet.get(position);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        customAdapter = new CustomAdapter(categories);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public int getItemCount() {
        return categoryDataSet.size();
    }
}
