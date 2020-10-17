package com.example.animalrecordkeeper.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animalrecordkeeper.AnimalDetail;
import com.example.animalrecordkeeper.Entities.AnimalEntity;
import com.example.animalrecordkeeper.Entities.FeedingEntity;
import com.example.animalrecordkeeper.FeedingActivity;
import com.example.animalrecordkeeper.FeedingAnimals;
import com.example.animalrecordkeeper.GroupDetail;
import com.example.animalrecordkeeper.ManageAnimalsActivity;
import com.example.animalrecordkeeper.ManageGroupsActivity;
import com.example.animalrecordkeeper.R;
import com.example.animalrecordkeeper.ViewModel.AnimalViewModel;
import com.example.animalrecordkeeper.ViewModel.FeedingViewModel;

import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {
    class AnimalViewHolder extends RecyclerView.ViewHolder {
        private final TextView animalItemView;

        private AnimalViewHolder(View itemView) {
            super(itemView);
            animalItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final AnimalEntity current = mAnimals.get(position);
                    Intent intent;
                    if(context.getClass() == ManageAnimalsActivity.class) {
                        intent = new Intent(context, AnimalDetail.class);
                    }
                    else {
                        intent = new Intent(context, FeedingActivity.class);
                    }
                    intent.putExtra("animalId", current.getAnimalId());
                    intent.putExtra("name", current.getName());
                    intent.putExtra("dateReceived", current.getDateReceived());
                    intent.putExtra("species", current.getSpecies());
                    intent.putExtra("gender", current.getGender());
                    intent.putExtra("healthStatus", current.getHealthStatus());
                    intent.putExtra("groupId", current.getGroupId());
                    intent.putExtra("notes", current.getNotes());
                    intent.putExtra("recentFeeding", current.getRecentFeeding());
                    intent.putExtra("basicStatus", current.getBasicStatus());
                    intent.putExtra("position", position);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private List<AnimalEntity> mAnimals;
    private final Context context;

    public AnimalAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public AnimalAdapter.AnimalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.part_list_item, parent, false);
        return new AnimalAdapter.AnimalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AnimalAdapter.AnimalViewHolder holder, int position) {
        if (mAnimals != null) {
            AnimalEntity current = mAnimals.get(position);
            String recentFeeding = current.getRecentFeeding();
            if (recentFeeding == null) {
                recentFeeding = " ";
            }
            else {
                recentFeeding = ", " + recentFeeding;
            }
            String text = current.getName() + ", " + current.getSpecies() + ", " + current.getGender() + recentFeeding;
            holder.animalItemView.setText(text);
        }
        else {
            holder.animalItemView.setText("No animals");
        }
    }

    public void setAnimals(List<AnimalEntity> animals) {
        mAnimals = animals;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAnimals != null)
            return mAnimals.size();
        else return 0;
    }
}
