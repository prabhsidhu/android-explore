package com.example.p16.explore;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    public List<Multiple_view> views;
    private FirebaseAuth mAuth;

    public MyAdapter(Context c, List<Multiple_view> v){
        context = c;
        views = v;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Multiple_view list = views.get(position);
        holder.name.setText(list.getName());
        holder.description.setText(list.getDescription());
        holder.key.setText(list.getKey());
        Picasso.get().load(list.getImage()).into(holder.image);
        if(list.getBookmark() == false){
            holder.btnDlt.setVisibility(View.GONE);
        }else{
            holder.btnDlt.setVisibility(View.VISIBLE);
        }

        Log.d("Testing", "onBindViewHolder: " + list.getBookmark());
        holder.btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent i =new Intent(v.getContext(),Single_view.class);
                i.putExtra("data",list);
                context.startActivity(i);
                Log.d("Tag", "clicked");
//                if(list.getDate() == null){
//                    Toast.makeText(v.getContext(),"invisible", Toast.LENGTH_SHORT).show();
//                }

            }
        });
 holder.btnDlt.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(final View v) {

         FirebaseDatabase databaseRef = FirebaseDatabase.getInstance();
         DatabaseReference mref = databaseRef.getReference("Users") ;
         FirebaseUser user = mAuth.getInstance().getCurrentUser();
         DatabaseReference bookmarkRef = mref.child(user.getUid()).child("Bookmark");
         bookmarkRef.child(list.getKey().toString()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
                 if(task.isSuccessful()){
                     Log.d("Delete", "onComplete: Removed " );
                     Toast.makeText(v.getContext(),"Deleted succesfully", Toast.LENGTH_SHORT).show();

                 }
                 else{
                     Log.d("Delete", "onComplete: Not removed");
                 }
             }
         });
     }
 });

    }


    @Override
    public int getItemCount() {
        return  views.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,description, key;
        ImageView image;
        Button btn, btnDlt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.event_name);
            key = (TextView) itemView.findViewById(R.id.key_value);
            description = (TextView) itemView.findViewById(R.id.description);
          image = (ImageView) itemView.findViewById(R.id.image);
          btn = (Button) itemView.findViewById(R.id.btnView);
          btnDlt = (Button) itemView.findViewById(R.id.btnDlt);


        }

    }

}
