package com.xuanluan.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xuanluan.myapplication.R;
import com.xuanluan.myapplication.model.Popular;
import com.xuanluan.myapplication.model.Recommend;
import com.xuanluan.myapplication.model.ViewAll;

import org.fabiomsr.moneytextview.MoneyTextView;

public class DetailActivity extends AppCompatActivity {
    ImageView imageView;
    TextView rating,description;
    MoneyTextView price;
    Toolbar toolbar;
    ViewAll viewAll = null;
    Recommend recommend = null;
    Popular popular = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        AnhXa();
        final Object object = getIntent().getSerializableExtra("detail");
        //all product
        if(object instanceof ViewAll){
            viewAll = (ViewAll) object;
        }
        if (viewAll !=null){
            Glide.with(getApplicationContext()).load(viewAll.getImage()).into(imageView);
            price.setAmount(viewAll.getPrice());
            rating.setText(viewAll.getRating());
            description.setText(viewAll.getDescription());
        }
        //recommend
        if(object instanceof Recommend){
            recommend = (Recommend) object;
        }
        if (recommend !=null){
            Glide.with(getApplicationContext()).load(recommend.getImage()).into(imageView);
            price.setAmount(recommend.getPrice());
            rating.setText(recommend.getRating());
            description.setText(recommend.getDescription());
        }
        //popular
        if(object instanceof Popular){
            popular = (Popular) object;
        }
        if (popular !=null){
            Glide.with(getApplicationContext()).load(popular.getImage()).into(imageView);
            price.setAmount(popular.getPrice());
            rating.setText(popular.getRating());
            description.setText(popular.getDescription());
        }
    }

    private void AnhXa(){
        toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = findViewById(R.id.detail_image);
        price = findViewById(R.id.detail_price);
        rating = findViewById(R.id.detail_rating);
        description = findViewById(R.id.detail_description);
    }
}