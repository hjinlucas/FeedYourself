package com.example.feedyourself.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.feedyourself.R;
import com.example.feedyourself.main.CommentActivity;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{
    private Context context;
    private List<Review> reviewList;

    public ReviewAdapter(Context context, List<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {
        Review review = reviewList.get(position);
        Glide.with(context)
                .load(review.getProfileImageUrl())
                .circleCrop()
                .into(holder.profileIcon);
        holder.userName.setText(review.getComments());
        holder.userRating.setText(String.valueOf(review.getRating()));
        holder.userReview.setText(review.getComments());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView profileIcon;
        public TextView userName;
        public TextView userRating;
        public TextView userReview;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileIcon = itemView.findViewById(R.id.review_user_profile_icon);
            userName = itemView.findViewById(R.id.review_user_name);
            userRating = itemView.findViewById(R.id.review_rating);
            userReview = itemView.findViewById(R.id.review_comment);
        }
    }
}
