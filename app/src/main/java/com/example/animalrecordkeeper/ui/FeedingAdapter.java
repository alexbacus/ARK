package com.example.animalrecordkeeper.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.animalrecordkeeper.Entities.FeedingEntity;
import com.example.animalrecordkeeper.FeedingActivity;
import com.example.animalrecordkeeper.R;

import java.util.List;

public class FeedingAdapter extends RecyclerView.Adapter<FeedingAdapter.FeedingViewHolder> {
    class FeedingViewHolder extends RecyclerView.ViewHolder {
        private final TextView feedingItemView;

        private FeedingViewHolder(View itemView) {
            super(itemView);
            feedingItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final FeedingEntity current = mFeedings.get(position);
                    Intent intent = new Intent(context, FeedingActivity.class);
                    intent.putExtra("feedingId", current.getFeedingId());
                    intent.putExtra("date", current.getDate());
                    intent.putExtra("time", current.getTime());
                    intent.putExtra("weight", current.getWeight());
                    intent.putExtra("notes", current.getNotes());
                    intent.putExtra("animalId", current.getAnimalId());
                    intent.putExtra("basicStatus", current.getBasicStatus());
                    intent.putExtra("position", position);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private List<FeedingEntity> mFeedings;
    private final Context context;

    public FeedingAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public FeedingAdapter.FeedingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.part_list_item, parent, false);
        return new FeedingAdapter.FeedingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeedingAdapter.FeedingViewHolder holder, int position) {
        if (mFeedings != null) {
            FeedingEntity current = mFeedings.get(position);
            holder.feedingItemView.setText(current.getDate() + " " + current.getTime());
        }
        else {
            holder.feedingItemView.setText("No feedings");
        }
    }

    public void setFeedings(List<FeedingEntity> feedings) {
        mFeedings = feedings;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mFeedings != null)
            return mFeedings.size();
        else return 0;
    }
}
