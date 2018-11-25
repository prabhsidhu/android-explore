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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.MyViewHolder> {

    Context context;
    public List<Multiple_view> views;
    private FirebaseAuth mAuth;
    FirebaseDatabase databaseRef = FirebaseDatabase.getInstance();
    DatabaseReference mref = databaseRef.getReference("Users") ;
public BookmarkAdapter(Context c, List<Multiple_view> v){
        context = c;
        views = v;
        }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.bookmarkview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkAdapter.MyViewHolder holder, int position) {
        final Multiple_view list = views.get(position);
        holder.name.setText(list.getName());
        holder.description.setText(list.getDescription());
        holder.key.setText(list.getKey());
        Picasso.get().load(list.getImage()).into(holder.image);
        holder.btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent i =new Intent(v.getContext(),Single_view.class);
                i.putExtra("data",list);

                i.putExtra("title", "Event");
                i.putExtra("Name",list.getName());
                i.putExtra("ID",list.getKey());

                i.putExtra("description",list.getDescription());
                i.putExtra("image",list.getImage());
                context.startActivity(i);
                Log.d("Tag", "clicked");

            }
        });

        holder.btnDlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                delete();
                FirebaseUser user = mAuth.getInstance().getCurrentUser();
                DatabaseReference bookmarkRef = mref.child(user.getUid()).child("Bookmark");
                bookmarkRef.child(list.getKey().toString()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d("Delete", "onComplete: Removed " );

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

public  class MyViewHolder extends RecyclerView.ViewHolder{

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

        btnDlt = (Button) itemView.findViewById(R.id.btnDelete);

    }

}

}
