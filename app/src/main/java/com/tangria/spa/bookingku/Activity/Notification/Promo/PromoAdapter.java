package com.tangria.spa.bookingku.Activity.Notification.Promo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.tangria.spa.bookingku.Activity.Promo.PromoDetailActivity;
import com.tangria.spa.bookingku.Activity.Promo.PromoDetailActivity_ViewBinding;
import com.tangria.spa.bookingku.R;

import org.json.JSONObject;

import java.util.List;

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.ItemViewHolder> {
    private Context context;
    private List<PromoModel> mList;

    public PromoAdapter(Context context, List<PromoModel> mList ) {
        this.context = context;
        this.mList = mList;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_notification, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int i) {
        final PromoModel Amodel = mList.get(i);
        holder.tv_title.setText(Amodel.getTitle());
        holder.tv_content.setText(Amodel.getContent());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void clearData() {
        int size = this.mList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.mList.remove(0);
            }
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_content;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, PromoDetailActivity.class);
                    i.putExtra("EXTRA_PROMO", mList.get(getAdapterPosition()));
                    context.startActivity(i);
                }
            });
        }
    }
}