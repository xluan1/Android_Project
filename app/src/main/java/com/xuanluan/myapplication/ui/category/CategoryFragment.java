package com.xuanluan.myapplication.ui.category;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.xuanluan.myapplication.R;
import com.xuanluan.myapplication.adapter.NavCategoryAdapter;
import com.xuanluan.myapplication.model.NavCategory;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    RecyclerView recyclerView;
    List<NavCategory> navCategoryList;
    NavCategoryAdapter navCategoryAdapter;
    FirebaseFirestore firestore;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle saveBundle){
        View root = inflater.inflate(R.layout.fragment_category,viewGroup,false);
        firestore = FirebaseFirestore.getInstance();
        recyclerView = root.findViewById(R.id.cat_rec);

        //
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        navCategoryList=new ArrayList<>();
        navCategoryAdapter=new NavCategoryAdapter(getActivity(),navCategoryList);
        recyclerView.setAdapter(navCategoryAdapter);

        firestore.collection("NavCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NavCategory navCategory= document.toObject(NavCategory.class);
                                navCategoryList.add(navCategory);
                                navCategoryAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(),"Lỗi"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return root;
    }
}