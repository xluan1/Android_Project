package com.xuanluan.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.xuanluan.myapplication.R;
import com.xuanluan.myapplication.adapter.ViewAllAdapter;
import com.xuanluan.myapplication.model.ViewAll;

import java.util.ArrayList;
import java.util.List;

public class ViewAllCategoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ViewAllAdapter viewAllAdapter;
    List<ViewAll> viewAllList;
    FirebaseFirestore firestore;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_category);
        toolbar =findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        String type = getIntent().getStringExtra("type");
        recyclerView = findViewById(R.id.view_cat_all_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewAllList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(this,viewAllList);
        recyclerView.setAdapter(viewAllAdapter);

        //sach giai tri
        if(type != null && type.equalsIgnoreCase("giaitri")){
            firestore.collection("AllProduct").whereEqualTo("type","giaitri")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                        ViewAll viewAll = documentSnapshot.toObject(ViewAll.class);
                        viewAllList.add(viewAll);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        //sach giao duc
        if(type != null && type.equalsIgnoreCase("giaoduc")){
            firestore.collection("AllProduct").whereEqualTo("type","giaoduc")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                        ViewAll viewAll = documentSnapshot.toObject(ViewAll.class);
                        viewAllList.add(viewAll);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        //sach hoc tap
        if(type != null && type.equalsIgnoreCase("hoctap")){
            firestore.collection("AllProduct").whereEqualTo("type","hoctap")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                        ViewAll viewAll = documentSnapshot.toObject(ViewAll.class);
                        viewAllList.add(viewAll);
                        viewAllAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }
}