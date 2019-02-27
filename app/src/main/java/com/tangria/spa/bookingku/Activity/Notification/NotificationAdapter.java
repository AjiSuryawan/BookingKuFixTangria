package com.tangria.spa.bookingku.Activity.Notification;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tangria.spa.bookingku.R;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private ArrayList<NotificationModel> notificationList;

    public NotificationAdapter(ArrayList<NotificationModel> notificationList) {
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notification, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        NotificationModel notification = notificationList.get(i);
        viewHolder.setElementValue(notification);
    }

    @Override
    public int getItemCount() {
        return notificationList == null? 0 : notificationList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTimeStamp;
        private TextView tvNotification;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNotification = itemView.findViewById(R.id.tv_content);
            tvTimeStamp = itemView.findViewById(R.id.tv_title);
        }

        private void setElementValue(NotificationModel notification){
            tvTimeStamp.setText(notification.getTitle());
            tvNotification.setText(notification.getContent());
        }
    }
}
