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

public class ViewAllActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ViewAllAdapter viewAllAdapter;
    List<ViewAll> viewAllList;
    FirebaseFirestore firestore;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        toolbar =findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        String type = getIntent().getStringExtra("type");
        recyclerView = findViewById(R.id.view_all_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewAllList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(this,viewAllList);
        recyclerView.setAdapter(viewAllAdapter);

        //tat ca sach
        firestore.collection("AllProduct")
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