package com.example.guru.bookingku.Activity.Booking;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.guru.bookingku.Model.AvailableTime;
import com.example.guru.bookingku.R;
import com.example.guru.bookingku.Util.onItemClickListener;

import java.util.List;

public class adapter_time_booking extends RecyclerView.Adapter<adapter_time_booking.Holder> {

    Context context;
    List<AvailableTime> availableTimeList;
    onItemClickListener listener;
    protected int row_index = -1;

    public adapter_time_booking(Context context, List<AvailableTime> availableTimeList) {
        this.context = context;
        this.availableTimeList = availableTimeList;
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_time, viewGroup, false);
        return new adapter_time_booking.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {
        final AvailableTime availableTime = availableTimeList.get(i);


        if (!availableTime.getAvailable()){
            holder.tvTime.setText(availableTime.getTime());
            holder.tvTime.setEnabled(false);
        }
        else {
            holder.tvTime.setText(availableTime.getTime());
            holder.tvTime.setEnabled(true);
            if (row_index == i) {
                holder.tvTime.setBackground(context.getResources().getDrawable(R.drawable.available_time_active_state));
                holder.tvTime.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                holder.tvTime.setBackground(context.getResources().getDrawable(R.drawable.available_time_default_state));
                holder.tvTime.setTextColor(context.getResources().getColor(R.color.greendark));
            }
        }

    }

    @Override
    public int getItemCount() {
        return availableTimeList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView tvTime;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.rdoTime);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    row_index = -1;
                    listener.onItemClick(getAdapterPosition());
                    row_index = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }
}
