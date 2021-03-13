package com.example.the_shield;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {
    private List<ContactModel> contactModelList;

    public ContactAdapter(List<ContactModel> contactModelList) {
        this.contactModelList = contactModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ContactModel model = contactModelList.get(position);
        if (model != null){
            if (model.getName() != null){
                holder.name.setText(model.getName());
            }

            if (model.getNumber() != null){
                StringBuffer buffer = new StringBuffer();
                for (String number:model.getNumber()){
                    buffer.append(number).append("\n");
                }
                holder.number.setText(buffer);
            }
        }
    }

    @Override
    public int getItemCount() {
        return contactModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, number;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvName);
            number = itemView.findViewById(R.id.tvNumber);
        }
    }
}
