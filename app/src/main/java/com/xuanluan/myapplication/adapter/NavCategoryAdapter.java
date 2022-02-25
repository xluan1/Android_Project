package com.xuanluan.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xuanluan.myapplication.R;
import com.xuanluan.myapplication.model.NavCategory;

import java.util.List;

public class NavCategoryAdapter extends RecyclerView.Adapter<NavCategoryAdapter.ViewHolder>{
    private Context context;
    private List<NavCategory> navCategoryList;

    public NavCategoryAdapter(Context context, List<NavCategory> navCategoryList) {
        this.context = context;
        this.navCategoryList = navCategoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_cat_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(navCategoryList.get(position).getImage()).into(holder.imageView);
        holder.name.setText(navCategoryList.get(position).getName());
        holder.description.setText(navCategoryList.get(position).getDescription());
        holder.discount.setText(navCategoryList.get(position).getDiscount());
    }

    @Override
    public int getItemCount() {
        return navCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, description, discount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.cat_nav_img);
            name=itemView.findViewById(R.id.nav_cat_name);
            description=itemView.findViewById(R.id.nav_cat_description);
            discount=itemView.findViewById(R.id.nav_cat_discount);
        }
    }
}
