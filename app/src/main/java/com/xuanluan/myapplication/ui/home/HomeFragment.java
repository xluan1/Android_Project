package com.xuanluan.myapplication.ui.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.xuanluan.myapplication.R;
import com.xuanluan.myapplication.activity.ViewAllActivity;
import com.xuanluan.myapplication.activity.ViewAllCategoryActivity;
import com.xuanluan.myapplication.adapter.CategoryAdapter;
import com.xuanluan.myapplication.adapter.PopularAdapter;
import com.xuanluan.myapplication.adapter.RecommendAdapter;
import com.xuanluan.myapplication.model.Category;
import com.xuanluan.myapplication.model.Popular;
import com.xuanluan.myapplication.model.Recommend;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    TextView txtPoppular, txtRecommend;

    List<Popular> popularList;
    List<Category> categoryList;
    List<Recommend> recommendList;

    RecommendAdapter recommendAdapter;
    CategoryAdapter categoryAdapter;
    PopularAdapter popularAdapter;

    RecyclerView popularRec, catRec, recommendRec;
    FirebaseFirestore firestore;

    ScrollView scrollView;
    ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle saveBundle){
        View root = inflater.inflate(R.layout.fragment_home,viewGroup,false);

        firestore=FirebaseFirestore.getInstance();
        popularRec = root.findViewById(R.id.pop_rec);
        catRec = root.findViewById(R.id.explore_rec);
        recommendRec = root.findViewById(R.id.recommend_rec);
        progressBar = root.findViewById(R.id.progressbar);
        scrollView = root.findViewById(R.id.scroll_view);
        txtPoppular = root.findViewById(R.id.txt_viewpouplar);
        txtRecommend = root.findViewById(R.id.txt_viewrecommend);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        //san pham
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popularList=new ArrayList<>();
        popularAdapter=new PopularAdapter(getActivity(),popularList);
        popularRec.setAdapter(popularAdapter);

        //nhan vao xem tat ca de xem tat ca sach
        txtPoppular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(txtPoppular.getContext(), ViewAllActivity.class);
                startActivity(intent);
            }
        });

        //sach pho bien
        firestore.collection("PopularBook")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Popular popular= document.toObject(Popular.class);
                                popularList.add(popular);
                                popularAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(),"Lỗi"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //loai san pham
        catRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryList=new ArrayList<>();
        categoryAdapter=new CategoryAdapter(getActivity(),categoryList);
        catRec.setAdapter(categoryAdapter);

        firestore.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Category category= document.toObject(Category.class);
                                categoryList.add(category);
                                categoryAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(),"Lỗi"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //san pham nen mua
        recommendRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        recommendList=new ArrayList<>();
        recommendAdapter=new RecommendAdapter(getActivity(),recommendList);
        recommendRec.setAdapter(recommendAdapter);

        txtRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(txtRecommend.getContext(), ViewAllActivity.class);
                startActivity(intent);
            }
        });

        firestore.collection("Recommend")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Recommend recommend= document.toObject(Recommend.class);
                                recommendList.add(recommend);
                                recommendAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(),"Lỗi"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return root;
    }
}