package com.xuanluan.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xuanluan.myapplication.R;
import com.xuanluan.myapplication.activity.DetailActivity;
import com.xuanluan.myapplication.model.Recommend;

import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {
    private Context context;
    private List<Recommend> recommendList;

    public RecommendAdapter(Context context, List<Recommend> recommendList) {
        this.context = context;
        this.recommendList = recommendList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(recommendList.get(position).getImage()).into(holder.recImg);
        holder.name.setText(recommendList.get(position).getName());
        holder.rating.setText(recommendList.get(position).getRating());
        holder.description.setText(recommendList.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("detail",recommendList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recommendList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView recImg;
        TextView name,description,rating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recImg=itemView.findViewById(R.id.rec_img);
            name=itemView.findViewById(R.id.rec_name);
            description=itemView.findViewById(R.id.rec_des);
            rating=itemView.findViewById(R.id.rec_rating);
        }
    }
}
