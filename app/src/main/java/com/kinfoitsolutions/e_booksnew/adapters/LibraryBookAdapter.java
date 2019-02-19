package com.kinfoitsolutions.e_booksnew.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kinfoitsolutions.e_booksnew.R;
import com.kinfoitsolutions.e_booksnew.response.LibraryResponse.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LibraryBookAdapter extends RecyclerView.Adapter<LibraryBookAdapter.MyViewHolder> {

    Context context;
    private List<Book> books;
    private mLibraryBookClickListener mLibraryBookClickListener;

    public interface mLibraryBookClickListener {
        void mClickRecommeded(View v, int pos, String listsName, String name, String file, String bookFile);
    }

    public LibraryBookAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }


    @NonNull
    @Override
    public LibraryBookAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_library, parent, false);
        return new LibraryBookAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryBookAdapter.MyViewHolder holder, int position) {
        final Book lists = books.get(position);
        try {
            Picasso.get().load(lists.getBookImage()).fit()
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.no_image)
                    .into(holder.book_image);
        } catch (Exception e) {
            e.printStackTrace();
            holder.book_image.setImageResource(R.drawable.no_image);

        }

        holder.download_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(lists.getBookFile())));
            }
        });

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView book_image,download_book;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_image = (ImageView)itemView.findViewById(R.id.book_image);
            download_book = (ImageView)itemView.findViewById(R.id.download_book);
        }
    }
}
