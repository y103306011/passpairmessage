package edu.nccu.mis.passpair;


import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{
    private Context context;
    private ArrayList<String> list;
    public PostAdapter(Context context,ArrayList<String> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View view = inflater.from(parent.getContext())
                .inflate(R.layout.cardview_post,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostAdapter.ViewHolder holder, int position) {
        String post = list.get(position);
        Uri uri = Uri.parse(post);
            Picasso.with(holder.cardviewpostimg1.getContext())
                    .load(uri)
                    .error(android.R.drawable.stat_notify_error)
                    .into(holder.cardviewpostimg1);

//            Picasso.with(context)
//                    .load(uri2)
//                    .placeholder(R.drawable.ic_favorite_black_24dp)
//                    .error(android.R.drawable.stat_notify_error)
//                    .into(holder.cardviewpostimg2);
//
//            Picasso.with(context)
//                    .load(post)
//                    .placeholder(R.drawable.ic_favorite_black_24dp)
//                    .error(android.R.drawable.stat_notify_error)
//                    .into(holder.cardviewpostimg3);


    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }else {
            return 0;
        }
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView cardviewpostimg1;
        public ViewHolder(View itemView) {
            super(itemView);
            cardviewpostimg1 = (ImageView) itemView.findViewById(R.id.cardviewpostimg1);
//            cardviewpostimg2 = (ImageView) itemView.findViewById(R.id.cardviewpostimg2);
//            cardviewpostimg3 = (ImageView) itemView.findViewById(R.id.cardviewpostimg3);
        }
    }
}

