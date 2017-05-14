package edu.nccu.mis.passpair;


import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolderFriends>{
    private Context context;
    private ArrayList<String> list;

    public HomeAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public HomeAdapter.ViewHolderFriends onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View view = inflater.from(parent.getContext())
                .inflate(R.layout.cardview_post,parent,false);
        ViewHolderFriends viewHolder = new ViewHolderFriends(view);
        return new ViewHolderFriends(view);
    }

    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolderFriends holder, int position) {
        String post = list.get(position);
        Uri uri = Uri.parse(post);
        Picasso.with(holder.cardviewpostimg1.getContext())
                .load(uri)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.cardviewpostimg1);
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }else {
            return 0;
        }
    }
    public static class ViewHolderFriends extends RecyclerView.ViewHolder{
        public ImageView cardviewpostimg1;
        public ViewHolderFriends(View itemView) {
            super(itemView);
            cardviewpostimg1 = (ImageView) itemView.findViewById(R.id.cardviewpostimg1);
//            cardviewpostimg2 = (ImageView) itemView.findViewById(R.id.cardviewpostimg2);
//            cardviewpostimg3 = (ImageView) itemView.findViewById(R.id.cardviewpostimg3);
        }
    }
}
