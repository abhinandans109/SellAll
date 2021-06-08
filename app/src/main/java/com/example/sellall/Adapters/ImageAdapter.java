package com.example.sellall.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sellall.AdapterData.Imagegetter;
import com.example.sellall.ImageClicked;
import com.example.sellall.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.myViewHolder> {
    Context context;
    ArrayList<String> list;
    public ImageAdapter() {
    }
    public ImageAdapter(Context context, ArrayList<String> list) {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.imageshow,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
            String i=list.get(position);
        Picasso.get().load(i).into(holder.im);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    
    
    public class myViewHolder extends RecyclerView.ViewHolder{
        ImageView im;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            im=itemView.findViewById(R.id.imid);
        }
    }
}
