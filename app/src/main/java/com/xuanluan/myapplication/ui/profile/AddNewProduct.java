package com.xuanluan.myapplication.ui.profile;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.xuanluan.myapplication.R;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddNewProduct extends Fragment {
    private ImageView selectImage;
    private Button btnSave;
    private EditText edtName,edtDescription,edtPrice,edtType;
    private FirebaseFirestore firestore;
    private FirebaseStorage firebaseStorage;
    private FirebaseDatabase database;
    private String downloadImageUrl = "https://firebasestorage.googleapis.com/v0/b/ngoxuanluan-project.appspot.com/o/9ce4b3a5-6240-4f70-9241-f9cb55a14219?alt=media&token=61c46b73-8b36-4a90-aed5-ce8ee50906c1";
    private Uri imageUri;
    StorageReference reference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle saveBundle){
        View root = inflater.inflate(R.layout.fragment_add_product,viewGroup,false);
        firestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        btnSave = root.findViewById(R.id.add_new_product);
        selectImage = root.findViewById(R.id.select_product_image);
        edtName = root.findViewById(R.id.product_name);
        edtPrice = root.findViewById(R.id.product_price);
        edtType = root.findViewById(R.id.product_type);
        edtDescription = root.findViewById(R.id.product_description);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetConntent.launch("image/*");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = edtName.getText().toString();
                String productDescription = edtDescription.getText().toString();
                String productType = edtType.getText().toString();
                String productPrice = edtPrice.getText().toString();
                Integer nIntFromPrice = Integer.parseInt(productPrice);

                Map<String,Object> productMap = new HashMap<>();
                productMap.put("name",productName);
                productMap.put("description",productDescription);
                productMap.put("type",productType);
                productMap.put("price",nIntFromPrice);
                productMap.put("image",downloadImageUrl);

                firestore.collection("AllProduct").add(productMap)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getContext(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                            }
                        });
                updateImage();
            }
        });
        return root;
    }

    private void updateImage(){
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.show();
        if (imageUri!=null) {
            reference = firebaseStorage.getReference().child("product_images/" + UUID.randomUUID().toString());

            reference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        dialog.dismiss();
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                database.getReference().child("AllProduct").child("image").setValue(imageUri.toString());
                            }
                        });
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    ActivityResultLauncher<String> mGetConntent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if(result != null){
                        selectImage.setImageURI(result);
                        imageUri = result;
                    }
                }
            });

}
