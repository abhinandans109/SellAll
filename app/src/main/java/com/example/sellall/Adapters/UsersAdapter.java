package com.example.sellall.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sellall.ImageClicked;
import com.example.sellall.MainActivity;
import com.example.sellall.R;
import com.example.sellall.AdapterData.UserSellData;
import com.squareup.picasso.Picasso;
//import com.google.firebase.database.core.Context;

import java.util.ArrayList;

//import static androidx.core.content.ContextCompat.startActivity;

public class UsersAdapter extends  RecyclerView.Adapter<UsersAdapter.MyViewHolder>{
    Context context;
    ArrayList<UserSellData> mlist;

    public UsersAdapter(Context context, ArrayList<UserSellData> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.listitem,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
                   UserSellData u= mlist.get(position);
                   holder.name.setText(u.getPersonname());
                   holder.itemname.setText(u.getItemname());
                   holder.address.setText(u.getAddress());
//                   holder.pincode.setText(u.getPincode());
                   holder.quantity.setText(u.getQuantity()+" Kg");
                   holder.contactno.setText(u.getContactno());
        Intent in=new Intent(context,ImageClicked.class);
                 Picasso.get().load(u.getA().get(0)).into(holder.im);
                 holder.mob.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                        Intent i=new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+u.getContactno()));
                context.startActivity(i);

                     }
                 });
                 ArrayList<String> a=new ArrayList<>();
                 a=u.getA();
              ImageAdapter i=new ImageAdapter(context,a);

        ArrayList<String> finalA = a;
        holder.im.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {

//                        in.putExtra("array", finalA);
                        in.putStringArrayListExtra("a",finalA);
                        context.startActivity(in);

                     }
                 });


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView address,contactno,itemname,name,quantity,mob;
//        ArrayList<String> images=new ArrayList<>();
        ImageView im;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            address=itemView.findViewById(R.id.layoutAddress);
            contactno=itemView.findViewById(R.id.layoutmobile);
            itemname=itemView.findViewById(R.id.layoutname);
//            pincode=itemView.findViewById(R.id.layoutpin);
            quantity=itemView.findViewById(R.id.layoutQuantityAvailable);
            im=itemView.findViewById(R.id.layoutimage);
            name=itemView.findViewById(R.id.layoutpersonname);
            mob=itemView.findViewById(R.id.layoutmobile);

//            Picasso.get().load(images.get(0)).into((ImageView) itemView.findViewById(R.id.layoutimage));
        }
    }

}
