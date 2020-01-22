package com.tangria.spa.bookingku.Activity.History;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tangria.spa.bookingku.Activity.History.utils.DateTimeUtils;
import com.tangria.spa.bookingku.Model.HistoryBooking;
import com.tangria.spa.bookingku.R;
import com.tangria.spa.bookingku.Util.onItemClickListener;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

/**
 * Created by HP-HP on 05-12-2015.
 */
public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder> {

    private List<HistoryBooking> historyBookingList;
    private Context mContext;
    private onItemClickListener listener;

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public TimeLineAdapter(List<HistoryBooking> historyBookingList, Context mContext) {
        this.historyBookingList = historyBookingList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_timeline_line_padding, viewGroup, false);
        return new TimeLineViewHolder(view, i);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder timeLineViewHolder, int i) {
        HistoryBooking historyBooking = historyBookingList.get(i);
        if (!historyBooking.getDate().isEmpty()) {
            timeLineViewHolder.mDate.setVisibility(View.VISIBLE);
            timeLineViewHolder.mDate.setText(DateTimeUtils.parseDateTime(historyBooking.getDate(), "yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy"));
        } else
            timeLineViewHolder.mDate.setVisibility(View.GONE);
        timeLineViewHolder.mMessage.setText(historyBooking.getOrder());
        timeLineViewHolder.tvStatus.setText(historyBooking.getStatus().toUpperCase());
        //change text color tvStatus
        if(historyBooking.getStatus().equals("pending")){
            timeLineViewHolder.tvStatus.setTextColor(mContext.getResources().getColor(android.R.color.holo_orange_light));
        }
        if(historyBooking.getStatus().equals("cancel")){
            timeLineViewHolder.tvStatus.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_light));
        }
        if (historyBooking.getStatus().equals("diterima")){
            timeLineViewHolder.tvStatus.setTextColor(mContext.getResources().getColor(android.R.color.holo_green_light));
        }
        if (historyBooking.getStatus().equals("selesai")){
            timeLineViewHolder.tvStatus.setTextColor(mContext.getResources().getColor(android.R.color.holo_green_light));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }


    @Override
    public int getItemCount() {
        return (historyBookingList != null ? historyBookingList.size() : 0);
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_timeline_date)
        TextView mDate;
        @BindView(R.id.text_timeline_title)
        TextView mMessage;
        @BindView(R.id.tvStatus)
        TextView tvStatus;

        public TimeLineViewHolder(View itemView, int viewType) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

}
