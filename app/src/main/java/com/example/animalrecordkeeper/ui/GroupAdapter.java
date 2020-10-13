package com.example.animalrecordkeeper.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.animalrecordkeeper.Entities.GroupEntity;
import com.example.animalrecordkeeper.GroupDetail;
import com.example.animalrecordkeeper.R;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder>{
    class GroupViewHolder extends RecyclerView.ViewHolder {
        private final TextView groupItemView;

        private GroupViewHolder(View itemView) {
            super(itemView);
            groupItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final GroupEntity current = mGroups.get(position);
                    Intent intent = new Intent(context, GroupDetail.class);
                    intent.putExtra("name", current.getName());
                    intent.putExtra("groupId", current.getGroupId());
                    intent.putExtra("onFeedingList", current.getOnFeedingList());
                    intent.putExtra("basicStatus", current.getBasicStatus());
                    intent.putExtra("position", position);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private List<GroupEntity> mGroups;
    private final Context context;

    public GroupAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.part_list_item, parent, false);
        return new GroupViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        if (mGroups != null) {
            GroupEntity current = mGroups.get(position);
            holder.groupItemView.setText(current.getName());
        }
        else {
            holder.groupItemView.setText("No groups");
        }
    }

    public void setGroups(List<GroupEntity> groups) {
        mGroups = groups;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mGroups != null)
            return mGroups.size();
        else return 0;
    }
}
