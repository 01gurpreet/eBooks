package com.kinfoitsolutions.e_booksnew.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.kinfoitsolutions.e_booksnew.R;
import com.kinfoitsolutions.e_booksnew.response.GetAllBooksReponse.RecommendedPayload;
import com.squareup.picasso.Picasso;

import java.util.List;


public class BookListRecycleAdapter extends RecyclerView.Adapter<BookListRecycleAdapter.MyViewHolder> {

    Context context;
    private List<RecommendedPayload> OfferList;
    private mRecommendedBookClickListener mRecommendedBookClickListener;

    public interface mRecommendedBookClickListener{
        void mClickRecommeded(View v, int pos, String listsName, String name, String file, String bookFile);
    }

    public BookListRecycleAdapter(Context context, List<RecommendedPayload> offerList, BookListRecycleAdapter.mRecommendedBookClickListener mRecommendedBookClickListener) {
        this.context = context;
        OfferList = offerList;
        this.mRecommendedBookClickListener = mRecommendedBookClickListener;
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

    @Override
    public BookListRecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book_list, parent, false);


        return new BookListRecycleAdapter.MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {


        final RecommendedPayload lists = OfferList.get(position);


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
                mRecommendedBookClickListener.mClickRecommeded(view,
                        position,lists.getName()
                        ,lists.getBookFile(),
                        lists.getBookImage(),
                        lists.getAuthorName());
            }
        });


    }


    @Override
    public int getItemCount() {
        return OfferList.size();

    }

}


