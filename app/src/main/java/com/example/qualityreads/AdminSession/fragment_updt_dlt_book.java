package com.example.qualityreads.AdminSession;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.qualityreads.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_updt_dlt_book#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_updt_dlt_book extends Fragment {
    private Button update, selectImage, dltBtn;
    private EditText etName, etISBN, etCategory, etPagination, etPublisher, etPublishDate, etPrice, etDescription;
    RelativeLayout progressBar;
    private ImageView bookImage;
    private Uri filePath;
    private static final int PICK_IMAGE_REQUEST = 200;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseFirestore db;
    // TODO: Rename and change types of parameters
    private String bookID;
    private String mParam2;

    public fragment_updt_dlt_book() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_updt_dlt_book.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_updt_dlt_book newInstance(String param1, String param2) {
        fragment_updt_dlt_book fragment = new fragment_updt_dlt_book();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookID = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_updt_dlt_book, container, false);
        db = FirebaseFirestore.getInstance();
        bookImage = view.findViewById(R.id.imageViewUpdate);
        progressBar = view.findViewById(R.id.progressWrapper);
        etName = view.findViewById(R.id.editTextName2);
        etISBN = view.findViewById(R.id.editTextIsbn2);
        etCategory = view.findViewById(R.id.editTextCategory2);
        etPagination = view.findViewById(R.id.editTextNumberPagination2);
        etPublisher = view.findViewById(R.id.editTextPublisher2);
        etPublishDate = view.findViewById(R.id.editTextDate2);
        etPrice = view.findViewById(R.id.editTextPrice2);
        etDescription = view.findViewById(R.id.editTextMultiLineUpdate);
        dltBtn = view.findViewById(R.id.buttonDelete);
        selectImage = view.findViewById(R.id.imgBtn);
        update = view.findViewById(R.id.buttonUpdate);
        dltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getContext()).setTitle("Delete book")
                        .setMessage("Are you sure you need to delete this book?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                progressBar.setVisibility(View.VISIBLE);
                                db.collection("books").document(bookID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {

                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getContext(), "Book deleted successfully", Toast.LENGTH_LONG).show();
                                        getActivity().onBackPressed();
                                    }
                                });
                            }
                        }).setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_delete).show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                String stName = etName.getText().toString();
                String stISBN = etISBN.getText().toString();
                String stCategory = etCategory.getText().toString();
                String stPagination = etPagination.getText().toString();
                String stPublisher = etPublisher.getText().toString();
                String stPublishDate = etPublishDate.getText().toString();
                Double stPrice = !(etPrice.getText().toString().isEmpty()) ? Double.parseDouble(etPrice.getText().toString()) : 0.0;
                String stDescription = etDescription.getText().toString();
                if (validateBook(stName, stISBN, stCategory, stPagination, stPublisher, stPublishDate, stPrice, stDescription)) {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    Map<String, Object> book = new HashMap<>();
                    book.put("name", stName);
                    book.put("isbn", stISBN);
                    book.put("category", stCategory);
                    book.put("pagination", stPagination);
                    book.put("publisher", stPublisher);
                    book.put("publishDate", stPublishDate);
                    book.put("price", stPrice);
                    book.put("description", stDescription);
                    StorageReference ref = storage.getReference().child("books/" + stISBN + System.currentTimeMillis());
                    if (filePath != null) {
                        ref.putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        book.put("image", uri.toString());
                                        db.collection("books").document(bookID).update(book).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(getContext(), "uploaded", Toast.LENGTH_LONG).show();
                                                getActivity().onBackPressed();

                                            }
                                        });
                                    }
                                });
                            }
                        });
                    } else {
                        db.collection("books").document(bookID).update(book).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "uploaded", Toast.LENGTH_LONG).show();
                                getActivity().onBackPressed();

                            }
                        });
                    }

                }
            }
        });
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
        if (bookID != null) {
            progressBar.setVisibility(View.VISIBLE);
            db.collection("books").document(bookID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc != null) {
                            etName.setText(doc.getString("name"));
                            etISBN.setText(doc.getString("isbn"));
                            etCategory.setText(doc.getString("category"));
                            etPagination.setText(doc.getString("pagination"));
                            etPublisher.setText(doc.getString("publisher"));
                            etPublishDate.setText(doc.getString("publishDate"));
                            etPrice.setText(doc.get("price").toString());
                            etDescription.setText(doc.getString("description"));
                            Picasso.get().load(doc.getString("image")).into(bookImage);
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
        } else {
            Toast.makeText(getContext(), "Please click on a book", Toast.LENGTH_LONG).show();
        }
        // Inflate the layout for this fragment
        return view;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), filePath);
                bookImage.setImageBitmap(bitmap);
                System.out.println(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean validateBook(String stName, String stISBN, String stCategory, String stPagination, String stPublisher, String stPublishDate, double stPrice, String stDescription) {
        boolean valid = true;

        if (stName == null) {
            etName.setError("Name cannot be null");
            valid = false;
        } else if (stName.length() < 4) {
            etName.setError("Name must contain 4 characters or more");
            valid = false;
        } else {
            etName.setError(null);
        }
        if (stISBN == null) {
            etISBN.setError("ISBN cannot be null");
            valid = false;
        } else if (stISBN.length() < 8) {
            etISBN.setError("ISBN must contain 8 characters or more");
            valid = false;
        } else {
            etISBN.setError(null);
        }
        if (stCategory == null) {
            etCategory.setError("Category cannot be null");
            valid = false;
        } else if (stCategory.length() < 4) {
            etCategory.setError("Category must contain 4 characters or more");
            valid = false;
        } else {
            etCategory.setError(null);
        }
        if (stPagination == null) {
            etPagination.setError("Pagination cannot be null");
            valid = false;
        } else {
            etPagination.setError(null);
        }
        if (stPublisher == null) {
            etPublisher.setError("Publisher cannot be null");
            valid = false;
        } else if (stPublisher.length() < 4) {
            etPublisher.setError("Publisher must contain 4 characters or more");
            valid = false;
        } else {
            etPublisher.setError(null);
        }
        if (stPublishDate == null) {
            etPublishDate.setError("PublishDate cannot be null");
            valid = false;
        } else if (stPublishDate.length() < 8) {
            etPublishDate.setError("PublishDate must contain 8 characters or more");
            valid = false;
        } else {
            etPublishDate.setError(null);
        }
        if (stPrice <= 0.0) {
            etPrice.setError("Price cannot be null");
            valid = false;
        } else {
            etPrice.setError(null);
        }

        if (stDescription == null) {
            etDescription.setError("Description cannot be null");
            valid = false;
        } else if (stDescription.length() < 20) {
            etDescription.setError("Description must contain 20 characters or more");
            valid = false;
        } else {
            etDescription.setError(null);
        }
        return valid;
    }

}