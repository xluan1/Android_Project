package com.xuanluan.myapplication.ui.profile;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.xuanluan.myapplication.R;
import com.xuanluan.myapplication.model.User;

import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    CircleImageView profileImg;
    EditText name,email,numberPhone,address;
    Button btnUpdate,btnUpdateImg;
    FirebaseAuth auth;
    FirebaseStorage firebaseStorage;
    FirebaseDatabase database;
    FirebaseUser user;
    Uri imageUri;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle saveBundle){
        View root = inflater.inflate(R.layout.fragment_profile,viewGroup,false);
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        profileImg = root.findViewById(R.id.profile_img);
        name = root.findViewById(R.id.profile_name);
        email = root.findViewById(R.id.profile_email);
        numberPhone = root.findViewById(R.id.profile_phone);
        address = root.findViewById(R.id.profile_address);
        btnUpdate = root.findViewById(R.id.btn_update);
        btnUpdateImg = root.findViewById(R.id.btn_update_img);

        if(auth.getCurrentUser()==null){
            return null;
        }

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetConntent.launch("image/*");
            }
        });

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        name.setText(user.getName());
                        email.setText(user.getEmail());
                        numberPhone.setText(user.getPhoneNumber());
                        address.setText(user.getAddress());
                        Glide.with(getContext()).load(user.getProfileImg()).into(profileImg);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        btnUpdateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfileImg();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });

        return root;
    }

    private void updateUserProfile() {

        String upName = name.getText().toString().trim();
        String upAddress = address.getText().toString().trim();
        String upPhone = numberPhone.getText().toString().trim();
        HashMap<String, Object> updateFbDb = new HashMap<>();
        updateFbDb.put("name", upName);
        updateFbDb.put("address", upAddress);
        updateFbDb.put("numberPhone", upPhone);

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .updateChildren(updateFbDb).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }
                });

    }

    private void updateUserProfileImg() {
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Đang tải ảnh lên");
        dialog.show();

        if (imageUri!=null){
            StorageReference reference = firebaseStorage.getReference().child("images/"
                    + UUID.randomUUID().toString());

            reference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()){
                        dialog.dismiss();
                        Toast.makeText(getContext(),"Đã tải ảnh lên",Toast.LENGTH_SHORT).show();
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                        .child("profileImg").setValue(imageUri.toString());
                                Toast.makeText(getContext(),"Cập nhật ảnh đại diện thành công",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        dialog.dismiss();
                        Toast.makeText(getContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    //start an activity for result
    ActivityResultLauncher<String> mGetConntent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if(result != null){
                        profileImg.setImageURI(result);
                        imageUri = result;
                    }
                }
            });
}