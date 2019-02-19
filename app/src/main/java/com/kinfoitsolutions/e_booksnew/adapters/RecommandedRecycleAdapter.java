package com.kinfoitsolutions.e_booksnew.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kinfoitsolutions.e_booksnew.AppConstants;
import com.kinfoitsolutions.e_booksnew.R;
import com.kinfoitsolutions.e_booksnew.response.GetAllBooksReponse.RecommendedPayload;
import com.kinfoitsolutions.e_booksnew.response.GetAllBooksReponse.Top50Payload;
import com.orhanobut.hawk.Hawk;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecommandedRecycleAdapter extends RecyclerView.Adapter<RecommandedRecycleAdapter.MyViewHolder> {

    private List<RecommendedPayload> booksList;
    private Context context;
    private mClickListenerRecommended mClickListenerRecommended;


    public interface mClickListenerRecommended {
        public void mRecommendClick(View v, int position,
                                    Integer id,
                                    String s,
                                    String bookName,
                                    String name,
                                    String bookBookFile,
                                    String file,
                                    String bookFile);
    }

    public RecommandedRecycleAdapter(Context context, List<RecommendedPayload> bookList, mClickListenerRecommended listener) {
        this.booksList = bookList;
        this.context = context;
        this.mClickListenerRecommended = listener;
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, rating, author_name;
        ImageView imageView;
        LinearLayout bookRowClick;


        MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.title);
            author_name = view.findViewById(R.id.author_name);
            rating = view.findViewById(R.id.rating);
            imageView = view.findViewById(R.id.image);
            bookRowClick = view.findViewById(R.id.bookRowClick);

        }

    }


    @NotNull
    @Override
    public RecommandedRecycleAdapter.MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommanded_list, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final RecommendedPayload book = booksList.get(position);




        holder.title.setText(book.getName());
        holder.rating.setText("2");
        holder.author_name.setText(book.getAuthorName());

        try {
            Picasso.get().load(book.getBookImage()).fit()
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.no_image)
                    .into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
            holder.imageView.setImageResource(R.drawable.no_image);
        }


        holder.bookRowClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListenerRecommended.mRecommendClick(view,
                        position,
                        book.getId(),
                        book.getName(),
                        book.getBookFile(),
                        book.getBookImage(),
                        book.getAuthorName(),
                        book.getBook_text(),
                        book.getBookAudio());
            }
        });

    }


    @Override
    public int getItemCount() {
        return booksList.size();

    }

}
