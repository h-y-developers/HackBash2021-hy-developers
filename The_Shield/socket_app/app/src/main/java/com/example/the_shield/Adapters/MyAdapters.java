package com.example.the_shield.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.the_shield.Model.MyContacts;
import com.example.the_shield.R;

import java.util.ArrayList;

public class MyAdapters extends RecyclerView.Adapter<MyAdapters.MyAdaptersViewHolder> {

    Context context;
    ArrayList<MyContacts> myContactsArrayList;
    public MyAdapters(Context context, ArrayList<MyContacts> myContactsArrayList){
        this.context=context;
        this.myContactsArrayList = myContactsArrayList;
    }
    @NonNull
    @Override
    public MyAdaptersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list,parent,false);
        return new MyAdaptersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdaptersViewHolder holder, int position) {
        MyContacts myContacts = myContactsArrayList.get(position);
        holder.tvName.setText(myContacts.getName());
        holder.tvNumber.setText(myContacts.getNumber());

    }

    @Override
    public int getItemCount() {
        return myContactsArrayList.size();
    }

    public class MyAdaptersViewHolder extends RecyclerView.ViewHolder{
        TextView tvName,tvNumber;
        AppCompatImageButton callBtn,messageBtn;
        public MyAdaptersViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.txtName);
            tvNumber = itemView.findViewById(R.id.txtNumber);
            callBtn = itemView.findViewById(R.id.ibCall);
            messageBtn = itemView.findViewById(R.id.ibMessage);

            callBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + myContactsArrayList.get(getAdapterPosition()).getNumber()));
                    context.startActivity(intent);
                }
            });

            messageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + myContactsArrayList.get(getAdapterPosition()).getNumber()));
                    context.startActivity(i);

                }
            });
        }
    }
}
