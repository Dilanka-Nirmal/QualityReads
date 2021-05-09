package com.example.qualityreads;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qualityreads.AdminSession.fragment_updt_dlt_book;
import com.example.qualityreads.UserSession.fragment_display_book;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    public ArrayList<Book> books;
    public boolean isAdmin;

    public class BookViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView titleTV, isbnTV, priceTV;
        public BookViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.bookImageView);
            titleTV = itemView.findViewById(R.id.titleTV);
            isbnTV = itemView.findViewById(R.id.isbnTV);
            priceTV = itemView.findViewById(R.id.priceTV);
            FragmentManager manager= ((AppCompatActivity)itemView.getContext()).getSupportFragmentManager();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isAdmin){
                        //handle admin click methods
                        FragmentTransaction fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.addToBackStack("inventory");
                        Bundle bundle = new Bundle();
                        bundle.putString("id", books.get(getBindingAdapterPosition()).getId());
                        Fragment update = new fragment_updt_dlt_book();
                        update.setArguments(bundle);
                        fragmentTransaction.replace(R.id.admin_fragment_container, update).commit();

                    }else{
                        // handle user clicked methods
                        FragmentTransaction fragmentTransaction = manager.beginTransaction();
                        fragmentTransaction.addToBackStack("inventory");
                        Bundle bundle = new Bundle();
                        bundle.putString("id", books.get(getBindingAdapterPosition()).getId());
                        Fragment display = new fragment_display_book();
                        display.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragment_container, display).commit();
                    }

                }
            });

        }
    }

    public BookAdapter(ArrayList<Book> books, boolean isAdmin) {
        this.books = books;
        this.isAdmin = isAdmin;
    }

    @NonNull
    @NotNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_book_item, parent, false);
        BookViewHolder bvholder = new BookViewHolder(view);

        return bvholder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BookAdapter.BookViewHolder holder, int position) {
        Book book = books.get(position);

        holder.titleTV.setText(book.getName());
        holder.isbnTV.setText(book.getIsbn());
        holder.priceTV.setText(String.format("%.2f",book.getPrice()));
        Picasso.get().load(book.getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }


}
