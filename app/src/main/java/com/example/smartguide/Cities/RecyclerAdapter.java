package com.example.smartguide.Cities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartguide.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    //String data[];
    //String Phone[];
    Context context;

    List<String> data;

    int row_index;
    public RecyclerAdapter(Context context, List<String> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.cutome_design,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        int x=position;

        holder.textView.setText(data.get(position));

        //holder.textViewPhone.setText(Phone[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //holder.cardView.setCardBackgroundColor(2);
                // setBackgroundResource(R.color.green);

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("https://maps.google.com/?q="+holder.textView.getText().toString()));
                v.getContext().startActivity(intent);

                row_index=x;
                notifyDataSetChanged();
            }
        });
        if(row_index==position){
            //holder.cardView.setBackgroundColor(2);
        }
        else
        {
            //holder.cardView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
       
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        TextView textViewPhone;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            textView = itemView.findViewById(R.id.textView1);
            //textViewPhone = itemView.findViewById(R.id.textView2);
            cardView=itemView.findViewById(R.id.CardView);

        }
    }
}