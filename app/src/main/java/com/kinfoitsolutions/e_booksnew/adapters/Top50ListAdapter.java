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
import com.kinfoitsolutions.e_booksnew.response.GetAllBooksReponse.RecommendedPayload;
import com.kinfoitsolutions.e_booksnew.response.GetAllBooksReponse.Top50Payload;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Top50ListAdapter extends RecyclerView.Adapter<Top50ListAdapter.MyViewHolder> {

    Context context;
    private List<Top50Payload> top50PayloadList;
    private mTop50ListClickListener mTop50ListClickListener;

    public interface mTop50ListClickListener{
        void mClickList50(View v, int pos, String listsName, String name, String file, String bookFile);

    }

    public Top50ListAdapter(Context context, List<Top50Payload> top50PayloadList, Top50ListAdapter.mTop50ListClickListener mTop50ListClickListener) {
        this.context = context;
        this.top50PayloadList = top50PayloadList;
        this.mTop50ListClickListener = mTop50ListClickListener;
    }

    @NonNull
    @Override
    public Top50ListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book_list, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Top50ListAdapter.MyViewHolder holder, final int position) {
        final Top50Payload lists = top50PayloadList.get(position);


        holder.title.setText(lists.getName());
        holder.rating.setText("3");
        holder.author_name.setText(lists.getAuthorName());

        try {
            Picasso.get().load(lists.getBookImage()).fit()
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
                mTop50ListClickListener.mClickList50(view,
                        position,lists.getName(),
                        lists.getBookFile(),
                        lists.getBookImage(),
                        lists.getAuthorName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return top50PayloadList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView title, rating,author_name;
        ImageView imageView;
        LinearLayout bookRowClick;


        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            author_name = (TextView) view.findViewById(R.id.author_name);
            rating = (TextView) view.findViewById(R.id.rating);
            imageView = (ImageView)view.findViewById(R.id.image);
            bookRowClick = view.findViewById(R.id.bookRowClick);

        }


    }
}
