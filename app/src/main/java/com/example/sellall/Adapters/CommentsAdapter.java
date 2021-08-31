package com.example.sellall.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sellall.AdapterData.CommentsData;
import com.example.sellall.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.myViewHolder> {
    Context context;
    ArrayList<CommentsData> list;

    public CommentsAdapter(Context context, ArrayList<CommentsData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.commentlayout,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        CommentsData c=list.get(position);
            holder.commentname.setText(c.getCommentname());
            holder.commentmessage.setText(c.getCommentmessage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView commentname,commentmessage;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            commentname=itemView.findViewById(R.id.comentname);
            commentmessage=itemView.findViewById(R.id.commentmessage);

        }
    }
}
