package com.example.sellall.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sellall.ImageClicked;
import com.example.sellall.MainActivity;
import com.example.sellall.R;
import com.example.sellall.AdapterData.UserSellData;
import com.example.sellall.comments;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
//import com.google.firebase.database.core.Context;

import java.util.ArrayList;

//import static androidx.core.content.ContextCompat.startActivity;

public class UsersAdapter extends  RecyclerView.Adapter<UsersAdapter.MyViewHolder>{
    Context context;
    int likes=0;
    DatabaseReference db;

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
                   likes=0;
        holder.name.setText(u.getPersonname());
                   holder.itemname.setText(u.getItemname());
                   holder.address.setText(u.getAddress());
                   holder.quantity.setText(u.getQuantity()+" Kg");
                   holder.contactno.setText(u.getContactno());
        Intent in=new Intent(context,ImageClicked.class);
        if(!(u.getA().isEmpty())) {
            Picasso.get().load(u.getA().get(0)).placeholder(R.drawable.pp).into(holder.im);
        }

        else {

            Picasso.get().load(R.drawable.pp).into(holder.im);
        }
        db= FirebaseDatabase.getInstance().
                getReference("UserSellData").child(u.getUid()).
                child(u.getPostid());
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("likes")){
//                    Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show();
                    holder.likeText.setText(Integer.parseInt(snapshot.child("likes").getValue().toString())+" Likes");

                }
                if(snapshot.hasChild("comments")){
                    FirebaseDatabase.getInstance().
                            getReference("UserSellData").child(u.getUid()).
                            child(u.getPostid()).child("comments").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            long i=0;
                            for (DataSnapshot d:snapshot.getChildren()){
                                i+=d.getChildrenCount();
                            }
                            holder.commenttext.setText(String.valueOf(i)+" Comments");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

        holder.commenttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,comments.class);
                i.putExtra("uid",u.getUid());
                i.putExtra("postid",u.getPostid());
                context.startActivity(i);
            }
        });
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i=new Intent(context,comments.class);
                    i.putExtra("uid",u.getUid());
                    i.putExtra("postid",u.getPostid());
                    context.startActivity(i);
            }
        });
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=holder.likeText.getText().toString();
               String [] s1=s.split(" ");
               int val=Integer.parseInt(s1[0]);
               val++;
               holder.likeText.setText(val +" Likes");

                      FirebaseDatabase.getInstance().
                              getReference("UserSellData").child(u.getUid()).
                              child(u.getPostid()).child("likes").setValue(String.valueOf(val)).addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void aVoid) {

                          }
                      });

            }
        });


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView address,contactno,itemname,name,quantity,mob,likeText,commenttext;
        ImageView im;
        Button like ,comment;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            address=itemView.findViewById(R.id.layoutAddress);
            contactno=itemView.findViewById(R.id.layoutmobile);
            itemname=itemView.findViewById(R.id.layoutname);
            quantity=itemView.findViewById(R.id.layoutQuantityAvailable);
            im=itemView.findViewById(R.id.layoutimage);
            name=itemView.findViewById(R.id.layoutpersonname);
            mob=itemView.findViewById(R.id.layoutmobile);
            like=itemView.findViewById(R.id.like);
            likeText=itemView.findViewById(R.id.textlikes);
            commenttext=itemView.findViewById(R.id.textcomments);
            comment=itemView.findViewById(R.id.comment);
        }
    }
    public void coments(){

    }

}
