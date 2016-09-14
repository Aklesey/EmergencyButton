package com.qoobico.emergencybutton.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qoobico.emergencybutton.R;
import com.qoobico.emergencybutton.fragment.EditContactActivity;

import java.util.ArrayList;
import java.util.List;

public class AlarmContactAdapter extends RecyclerView.Adapter<AlarmContactAdapter.AlarmContactHolder> {
    private Context mContext;
    private List<Contact> data = new ArrayList<>();
    private AlarmContactHolder holder1;


    public AlarmContactAdapter(Context mContext, List<Contact> data) {
        this.mContext = mContext;
        this.data = data;
    }

    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.contact_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.contact_edit:
                    return true;
                case R.id.add_new_contact:
                    return true;
                case R.id.contact_delete:
                    return true;
                default:
            }
            return false;
        }

    }

    @Override
    public int getItemCount() {
        return data.size();

    }

    public class AlarmContactHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView title;
        TextView titleNumber;
        TextView titleEmail;
        ImageView overflow;


        public AlarmContactHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardViewContact);
            title = (TextView) itemView.findViewById(R.id.title);
            titleNumber = (TextView) itemView.findViewById(R.id.titleNumber);
            titleEmail = (TextView) itemView.findViewById(R.id.titleEmail);
            overflow = (ImageView) itemView.findViewById(R.id.overflow);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    EditContactActivity.pos = position;
                    showPopupMenu(holder1.overflow);

                }
            });

        }

    }

    @Override
    public AlarmContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new AlarmContactHolder(view);
    }


    @Override
    public void onBindViewHolder(final AlarmContactHolder holder, int position) {
        holder1 = holder;
        holder.title.setText(data.get(position).getTitle());
        holder.titleNumber.setText(data.get(position).getTel());
        holder.titleEmail.setText(data.get(position).getEmail());

    }


}
