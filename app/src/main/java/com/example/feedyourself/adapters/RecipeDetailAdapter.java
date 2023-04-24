package com.example.feedyourself.adapters;

import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feedyourself.R;

import java.util.List;

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.ViewHolder> {
    private List<Pair<String, String>> details;

    public RecipeDetailAdapter(List<Pair<String, String>> details) {
        this.details = details;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_detail_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pair<String, String> detail = details.get(position);

        holder.detailKey.setTypeface(null, Typeface.BOLD);
        holder.detailKey.setText(detail.first);

        if (detail.first.equals("Directions")) {
            if (detail.second != null) {
                String[] steps = detail.second.split("#");
                String formattedSteps = TextUtils.join("\n\n", steps);
                holder.detailValue.setText(formattedSteps);
            } else {
                holder.detailValue.setText("No directions available.");
            }
        } else {
            holder.detailValue.setText(detail.second);
        }
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView detailKey;
        TextView detailValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            detailKey = itemView.findViewById(R.id.detail_title);
            detailValue = itemView.findViewById(R.id.detail_value);
        }
    }
}
