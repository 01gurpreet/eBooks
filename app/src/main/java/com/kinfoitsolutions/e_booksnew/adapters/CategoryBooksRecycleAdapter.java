package com.kinfoitsolutions.e_booksnew.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kinfoitsolutions.e_booksnew.R;
import com.kinfoitsolutions.e_booksnew.models.CategorySelectModelClass;
import com.kinfoitsolutions.e_booksnew.response.CategoryResponse.CategoryPayload;


import java.util.List;


public class CategoryBooksRecycleAdapter extends RecyclerView.Adapter<CategoryBooksRecycleAdapter.MyViewHolder> {

    Context context;
    private List<CategoryPayload> categoryPayloadList;
    private mCategoryClickRow mCategoryClickRow;

    public interface mCategoryClickRow{
        void mClick(View v, int pos);
    }

    public CategoryBooksRecycleAdapter(Context context, List<CategoryPayload> categoryPayloadList, CategoryBooksRecycleAdapter.mCategoryClickRow mCategoryClickRow) {
        this.context = context;
        this.categoryPayloadList = categoryPayloadList;
        this.mCategoryClickRow = mCategoryClickRow;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView title, txtCount;
        ImageView imageView;
        FrameLayout frameLayout;


        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            txtCount = (TextView) view.findViewById(R.id.txtCount);
            imageView = (ImageView)view.findViewById(R.id.triangle);
            frameLayout = (FrameLayout) view.findViewById(R.id.frameLayout);


        }

    }


    @Override
    public CategoryBooksRecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_select_list, parent, false);


        return new CategoryBooksRecycleAdapter.MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final CategoryPayload lists = categoryPayloadList.get(position);


        holder.title.setText(lists.getName());
        holder.txtCount.setText(lists.getBookCount()+" Books");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (lists.isSelected() == false){
                    lists.setSelected(true);
                    //holder.tick.setImageResource(R.drawable.ic_radio_on_button);

                    holder.frameLayout.setBackgroundResource(R.drawable.orange_light_rect);
                    holder.imageView.setVisibility(View.VISIBLE);

                    mCategoryClickRow.mClick(view,position);

                }else {
                    lists.setSelected(false);
                    holder.frameLayout.setBackgroundResource(R.drawable.gray_light_rect);
                    holder.imageView.setVisibility(View.GONE);
                }



            }
        });


    }


    @Override
    public int getItemCount() {
        return categoryPayloadList.size();

    }

}


