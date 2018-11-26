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

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;



public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {

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
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BookmarkViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder vHolder, int position) {
              final Multiple_view l = views.get(position);
        vHolder.bname.setText(l.getName());
//        vHolder.bdescription.setText(l.getDescription());
//        vHolder.bkey.setText(l.getKey());
//        Picasso.get().load(l.getImage()).into(vHolder.img);
        vHolder.bbtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent i =new Intent(v.getContext(),Single_view.class);
                i.putExtra("data",l);
                context.startActivity(i);
                Log.d("Tag", "clicked");


            }
        });
        vHolder.btnDlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Testing", "clicked");
            }
        });
    }

    @Override
    public int getItemCount() {
        return views.size();
    }

    public static class BookmarkViewHolder extends RecyclerView.ViewHolder{

        TextView bname,bdescription, bkey;
        ImageView img;
        Button bbtn, btnDlt;

        public BookmarkViewHolder(@NonNull View itemView) {
            super(itemView);
            bname = (TextView) itemView.findViewById(R.id.bname);
            bkey = (TextView) itemView.findViewById(R.id.bkey_value);
            bdescription = (TextView) itemView.findViewById(R.id.bdescription);
           img = (ImageView) itemView.findViewById(R.id.bimage);
            bbtn = (Button) itemView.findViewById(R.id.bbtnView);
            btnDlt = (Button) itemView.findViewById(R.id.bbtnDelete);
        }
    }


}
