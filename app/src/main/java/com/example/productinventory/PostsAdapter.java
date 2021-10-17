package com.example.productinventory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    List<Post> allPosts;
    OnClickListener listener = null;

    public interface OnClickListener {
        void onClick(int position);
    }

    public PostsAdapter(List<Post> allPosts, OnClickListener listener){
        this.allPosts = allPosts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_view,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.postTitle.setText(allPosts.get(position).getTitle());
        Picasso.get().load(allPosts.get(position).getImages()[0]).fit().placeholder(R.drawable.placeholder).into(holder.postImage);

        holder.constraintLayout.setOnClickListener(view -> {
            if (listener != null)
                listener.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return allPosts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout constraintLayout;
        ImageView postImage;
        TextView postTitle;
        View view;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            view = itemView;
            postImage = itemView.findViewById(R.id.post_image);
            postTitle = itemView.findViewById(R.id.post_title);
            constraintLayout = itemView.findViewById(R.id.cl);
        }
    }
}
