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

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    public List<Multiple_view> views;

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
    }


    @Override
    public int getItemCount() {
        return  views.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,description, key;
        ImageView image;
        Button btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.event_name);
            key = (TextView) itemView.findViewById(R.id.key_value);
            description = (TextView) itemView.findViewById(R.id.description);
          image = (ImageView) itemView.findViewById(R.id.image);
          btn = (Button) itemView.findViewById(R.id.btnView);
          key.setVisibility(View.INVISIBLE);

        }

    }

}
