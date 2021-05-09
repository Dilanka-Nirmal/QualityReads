package com.example.qualityreads.AdminSession;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.qualityreads.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Objects;

import models.Drivers;

import static android.app.Activity.RESULT_OK;


public class NewDriverFragment extends Fragment {

    EditText e1;
    EditText e2;
    EditText e3;
    EditText e4;
    EditText e5;
    EditText e6;
    Button addbtn;
    ImageView imageView;
    DatabaseReference databaseReference;
    Uri FilePathUri;
    StorageReference storageReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }

        View v1 = inflater.inflate(R.layout.fragment_new_driver, container, false);

        e1 = (EditText) v1.findViewById(R.id.did);
        e2 = (EditText) v1.findViewById(R.id.dname);
        e3 = (EditText) v1.findViewById(R.id.demail);
        e4 = (EditText) v1.findViewById(R.id.dvehicleNo);
        e5 = (EditText) v1.findViewById(R.id.dcontact);
        e6 = (EditText) v1.findViewById(R.id.daddress);
        imageView = (ImageView) v1.findViewById(R.id.capturedriver);
        addbtn = (Button) v1.findViewById(R.id.addd);
        storageReference = FirebaseStorage.getInstance().getReference("Drivers");
        databaseReference = FirebaseDatabase.getInstance().getReference("Drivers");

        progressDialog = new ProgressDialog(getContext());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                UploadImage();

            }
        });

        return v1;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), FilePathUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    public void UploadImage() {

        if (FilePathUri != null) {

            progressDialog.setTitle("Uploading data...");
            progressDialog.show();
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            final String id = e1.getText().toString();
                            final String name = e2.getText().toString();
                            final String email = e3.getText().toString();
                            final String vehicleno = e4.getText().toString();
                            final String contact = e5.getText().toString();
                            final String address = e6.getText().toString();

                            if(id.equals("")) {
                                e1.setError("ID is required");
                            }if(name.equals("")){
                                e2.setError("Name is required");
                            }if(email.equals("")){
                                e3.setError("Email is required");
                            }if(vehicleno.equals("")){
                                e4.setError("Vehicle Number is required");
                            }if(contact.equals("")){
                                e5.setError("Contact number is required");
                            }if(address.equals("")){
                                e6.setError("Address is required");
                            }else {

                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Data Uploaded Successfully ", Toast.LENGTH_LONG).show();
                                @SuppressWarnings("VisibleForTests")
                                Drivers drivers = new Drivers(id,name,email,vehicleno,contact,address,taskSnapshot.getUploadSessionUri().toString());
                                databaseReference.child(id).setValue(drivers);
                            }
                        }
                    });
        } else {

            Toast.makeText(getActivity(), "Please Check details again", Toast.LENGTH_LONG).show();

        }
    }

}


