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
import com.kinfoitsolutions.e_booksnew.R;
import com.kinfoitsolutions.e_booksnew.response.GetAllBooksReponse.Top50Payload;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Top50Adapter extends RecyclerView.Adapter<Top50Adapter.MyViewHolder> {

    private List<Top50Payload> top50BooksPayloadList;
    private Context context;
    private mTop50ClickListener mTop50ClickListener;

    public interface mTop50ClickListener{
        void mTop50Click(View c, int pos, Integer id, String booksPayloadName, String top50BooksPayloadName, String name, String top50BooksPayloadBookFile, String file, String bookFile);
    }

    public Top50Adapter(List<Top50Payload> top50BooksPayloadList, Context context, mTop50ClickListener mTop50ClickListener) {
        this.top50BooksPayloadList = top50BooksPayloadList;
        this.context = context;
        this.mTop50ClickListener = mTop50ClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recommanded_list, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        final Top50Payload top50BooksPayload = top50BooksPayloadList.get(position);

        holder.title.setText(top50BooksPayload.getName());
        holder.rating.setText("2");
        holder.author_name.setText(top50BooksPayload.getAuthorName());


        try {
            Picasso.get().load(top50BooksPayload.getBookImage()).fit()
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
                mTop50ClickListener.mTop50Click(view, position,
                        top50BooksPayload.getId(),
                        top50BooksPayload.getName(),
                        top50BooksPayload.getBookFile(),
                        top50BooksPayload.getBookImage(),
                        top50BooksPayload.getAuthorName(),
                        top50BooksPayload.getBook_text(),
                        top50BooksPayload.getBookAudio());
            }
        });

    }

    @Override
    public int getItemCount() {
        return top50BooksPayloadList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {



        TextView title, rating,author_name;
        ImageView imageView;
        LinearLayout bookRowClick;


        public MyViewHolder(View view) {
            super(view);

            title =  view.findViewById(R.id.title);
            author_name =  view.findViewById(R.id.author_name);
            rating = (TextView) view.findViewById(R.id.rating);
            imageView = (ImageView)view.findViewById(R.id.image);
            bookRowClick = view.findViewById(R.id.bookRowClick);




        }

    }
}
