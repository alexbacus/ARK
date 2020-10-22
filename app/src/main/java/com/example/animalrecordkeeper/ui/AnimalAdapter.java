package com.example.animalrecordkeeper.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.animalrecordkeeper.AnimalDetail;
import com.example.animalrecordkeeper.Entities.AnimalEntity;
import com.example.animalrecordkeeper.FeedingActivity;
import com.example.animalrecordkeeper.GroupDetail;
import com.example.animalrecordkeeper.ManageAnimalsActivity;
import com.example.animalrecordkeeper.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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
                    if(context.getClass() == ManageAnimalsActivity.class || context.getClass() == GroupDetail.class) {
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
            int feedingDate = 0;
            String weight;

            if (recentFeeding == null) {
                recentFeeding = " ";
                weight = " ";
            }
            else {
                recentFeeding = ", " + recentFeeding;
                feedingDate = current.getRecentFeeding().indexOf(", ") + 1;
                weight = ", " + current.getRecentFeeding().substring(0, feedingDate - 1);
            }

            String text;

            if(context.getClass() == ManageAnimalsActivity.class || context.getClass() == GroupDetail.class) {
                text = current.getName() + ": " + current.getSpecies() + ", " + current.getGender() + weight;
            }
            else {
                text = current.getName() + ": " + current.getSpecies() + ", " + current.getGender() + recentFeeding;
            }

            if (current.getRecentFeeding() != null) {
                String input = current.getRecentFeeding().substring(feedingDate);
                //Format of the date defined in the input String
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
                //Desired format: 24 hour format: Change the pattern as per the need
                DateFormat outputformat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                Date date = null;
                Date now = new Date();
                String output = null;
                try{
                    //Converting the input String to Date
                    date= df.parse(input);
                    if (now.toInstant().isBefore((date.toInstant().plus(4, ChronoUnit.HOURS)))) {
                        holder.animalItemView.setBackgroundColor(Color.parseColor("#A5E6BA"));
                    }
                    else {
                        holder.animalItemView.setBackgroundColor(Color.parseColor("#FF0000"));
                    }
                    //Changing the format of date and storing it in String
                    output = outputformat.format(date);
                }catch(ParseException pe){
                    pe.printStackTrace();
                }
            }
            else {
                holder.animalItemView.setBackgroundColor(Color.parseColor("#FF0000"));
            }
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
