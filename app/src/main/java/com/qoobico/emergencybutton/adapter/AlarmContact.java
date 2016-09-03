package com.qoobico.emergencybutton.adapter;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qoobico.emergencybutton.R;

import java.util.List;

public class AlarmContact extends RecyclerView.Adapter<AlarmContact.AlarmContactHolder> {
    private List<Contact> data;

    public AlarmContact(List<Contact> data) {
        this.data = data;
    }

    @Override
    public AlarmContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new AlarmContactHolder(view);
    }

    @Override
    public void onBindViewHolder(AlarmContactHolder holder, int position) {
        holder.title.setText(data.get(position).title);
        holder.titleNumber.setText(data.get(position).tel);
        holder.titleEmail.setText(data.get(position).email);
    }

    @Override
    public int getItemCount() {
        return data.size();

    }

    public static class AlarmContactHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView title;
        TextView titleNumber;
        TextView titleEmail;

        public AlarmContactHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            title = (TextView) itemView.findViewById(R.id.title);
            titleNumber = (TextView) itemView.findViewById(R.id.titleNumber);
            titleEmail = (TextView) itemView.findViewById(R.id.titleEmail);
        }
    }
}
