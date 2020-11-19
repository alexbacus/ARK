package com.example.animalrecordkeeper.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.animalrecordkeeper.Entities.IntakeFormEntity;
import com.example.animalrecordkeeper.IntakeFormDetail;
import com.example.animalrecordkeeper.R;

import java.util.List;

public class IntakeFormAdapter  extends RecyclerView.Adapter<IntakeFormAdapter.IntakeFormViewHolder>{
    class IntakeFormViewHolder extends RecyclerView.ViewHolder {
        private final TextView intakeFormItemView;

        private IntakeFormViewHolder(View itemView) {
            super(itemView);
            intakeFormItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final IntakeFormEntity current = mIntakeForms.get(position);
                    Intent intent = new Intent(context, IntakeFormDetail.class);
                    intent.putExtra("intakeFormId", current.getIntakeFormId());
                    intent.putExtra("donationAmount", current.getDonationAmount());
                    intent.putExtra("donationType", current.getDonationType());
                    intent.putExtra("name", current.getName());
                    intent.putExtra("email", current.getEmail());
                    intent.putExtra("mailingAddress", current.getMailingAddress());
                    intent.putExtra("city", current.getCity());
                    intent.putExtra("state", current.getState());
                    intent.putExtra("zip", current.getZip());
                    intent.putExtra("date", current.getDate());
                    intent.putExtra("dateFound", current.getDateFound());
                    intent.putExtra("animalType", current.getAnimalType());
                    intent.putExtra("animalLocation", current.getAnimalLocation());
                    intent.putExtra("animalFood", current.getAnimalFood());
                    intent.putExtra("animalMedical", current.getAnimalMedical());
                    intent.putExtra("circumstances", current.getCircumstances());
                    intent.putExtra("basicStatus", current.getBasicStatus());
                    intent.putExtra("position", position);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private List<IntakeFormEntity> mIntakeForms;
    private final Context context;

    public IntakeFormAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public IntakeFormAdapter.IntakeFormViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.part_list_item, parent, false);
        return new IntakeFormAdapter.IntakeFormViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IntakeFormAdapter.IntakeFormViewHolder holder, int position) {
        if (mIntakeForms != null) {
            IntakeFormEntity current = mIntakeForms.get(position);
            holder.intakeFormItemView.setText(current.getName());
        }
        else {
            holder.intakeFormItemView.setText("No intake forms");
        }
    }

    public void setIntakeForms(List<IntakeFormEntity> intakeForms) {
        mIntakeForms = intakeForms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mIntakeForms != null)
            return mIntakeForms.size();
        else return 0;
    }
}
