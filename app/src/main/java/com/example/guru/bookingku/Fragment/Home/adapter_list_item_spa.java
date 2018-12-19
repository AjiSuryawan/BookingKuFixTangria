package com.example.guru.bookingku.Fragment.Home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.guru.bookingku.Activity.Detail.DetailActivity;
import com.example.guru.bookingku.R;

import java.util.List;

public class adapter_list_item_spa extends RecyclerView.Adapter<adapter_list_item_spa.Holder> {

    private List<data_item_spa> arrayList;
    private Context context;

    public adapter_list_item_spa(Context context, List<data_item_spa> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_spa, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        final data_item_spa data_item = arrayList.get(position);
        holder.textview_item_spa.setText(data_item.getName());
        holder.textview_description_item.setText(data_item.getDescription());
        holder.textview_cost_item.setText(String.valueOf(data_item.getPrice()));
        Glide.with(context)
                .load(data_item.getImage())
                .into(holder.imageview_item_spa);
        holder.cardku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Integer id = data_item.getId();
                final String name = data_item.getName();
                final String image = data_item.getImage();
                final String description = data_item.getDescription();
                final Integer price = data_item.getPrice();
                final String available = data_item.getAvailable();
                Intent intent = new Intent(holder.itemView.getContext(),DetailActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("image",image);
                intent.putExtra("description",description);
                intent.putExtra("price",price);
                intent.putExtra("available",available);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private ImageView imageview_item_spa;
        private CardView cardku;
        private TextView textview_item_spa,textview_description_item,textview_cost_item;

        public Holder(View itemView) {
            super(itemView);
            imageview_item_spa = (ImageView) itemView.findViewById(R.id.imageview_item_spa);
            cardku = (CardView) itemView.findViewById(R.id.cardku);
            textview_item_spa = (TextView) itemView.findViewById(R.id.textview_name_item);
            textview_description_item = (TextView)itemView.findViewById(R.id.textview_description_item);
            textview_cost_item = (TextView)itemView.findViewById(R.id.textview_cost_item);
        }
    }
}
