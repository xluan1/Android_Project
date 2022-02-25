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
import com.xuanluan.myapplication.model.ViewAll;

import org.fabiomsr.moneytextview.MoneyTextView;

import java.util.List;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder> {
    private Context context;
    private List<ViewAll> viewAllList;

    public ViewAllAdapter(Context context, List<ViewAll> viewAllList) {
        this.context = context;
        this.viewAllList = viewAllList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(viewAllList.get(position).getImage()).into(holder.imageView);
        holder.name.setText(viewAllList.get(position).getName());
        holder.rating.setText(viewAllList.get(position).getRating());
        holder.price.setAmount(viewAllList.get(position).getPrice());
        holder.description.setText(viewAllList.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("detail",viewAllList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewAllList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,description,rating;
        MoneyTextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.view_img);
            name=itemView.findViewById(R.id.view_name);
            description=itemView.findViewById(R.id.view_description);
            price=itemView.findViewById(R.id.view_price);
            rating=itemView.findViewById(R.id.view_rating);
        }
    }
}
