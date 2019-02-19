package com.kinfoitsolutions.e_booksnew.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kinfoitsolutions.e_booksnew.R;
import com.kinfoitsolutions.e_booksnew.models.CheckBoxModel;

import java.util.List;

public class CheckBoxAdapter extends RecyclerView.Adapter<CheckBoxAdapter.MyViewHolder> {

    private List<CheckBoxModel> stList;

    public CheckBoxAdapter(List<CheckBoxModel> stList) {
        this.stList = stList;
    }

    @NonNull
    @Override
    public CheckBoxAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.check_box_row, null);

        // create ViewHolder

        MyViewHolder myViewHolder = new MyViewHolder(itemLayoutView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CheckBoxAdapter.MyViewHolder viewHolder, int position) {

        final int pos = position;

        viewHolder.tvName.setText(stList.get(position).getName());


        viewHolder.chkSelected.setChecked(stList.get(position).isSelected());

        viewHolder.chkSelected.setTag(stList.get(position));

        viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                CheckBoxModel checkBoxModel = (CheckBoxModel) cb.getTag();

                checkBoxModel.setSelected(cb.isChecked());
                stList.get(pos).setSelected(cb.isChecked());

           /*     Toast.makeText(
                        v.getContext(),
                        "Clicked on Checkbox: " + cb.getText() + " is "
                                + cb.isChecked(), Toast.LENGTH_LONG).show();*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return stList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvEmailId;

        public CheckBox chkSelected;

        public CheckBoxModel singleName;


        public MyViewHolder(@NonNull View itemLayoutView) {
            super(itemLayoutView);

            tvName =  itemLayoutView.findViewById(R.id.tvName);

            chkSelected =  itemLayoutView
                    .findViewById(R.id.chkSelected);


        }
    }

    // method to access in activity after updating selection
    public List<CheckBoxModel> getNameList() {
        return stList;
    }

}
