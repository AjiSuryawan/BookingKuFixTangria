package com.example.guru.bookingku.Activity.Booking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import com.example.guru.bookingku.Model.AvailableTime;
import com.example.guru.bookingku.R;

import java.util.List;

public class adapter_time_booking extends RecyclerView.Adapter<adapter_time_booking.Holder> {

    Context context;
    List<AvailableTime> availableTimeList;
    adapter_time_booking.onItemClickListener listener;

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
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        final AvailableTime availableTime = availableTimeList.get(i);
        holder.tvTime.setText(availableTime.getTime());
        //holder.rdoTime.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return availableTimeList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView tvTime;
        private boolean selected = true;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.rdoTime);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    public interface onItemClickListener{
        void onItemClick(int position);
    }
}
