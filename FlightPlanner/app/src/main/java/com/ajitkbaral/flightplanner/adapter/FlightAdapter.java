package com.ajitkbaral.flightplanner.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajitkbaral.flightplanner.R;
import com.ajitkbaral.flightplanner.entity.Flight;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.ViewHolder>   {

    public interface OnItemClickListener {
        void onItemClick(Flight item);
    }


    private final List<Flight> items;
    private final OnItemClickListener listener;

    public FlightAdapter(List<Flight> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flight, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView fromTime, toTime, amount;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            fromTime = itemView.findViewById(R.id.fromTime);
            toTime = itemView.findViewById(R.id.toTime);
            amount = itemView.findViewById(R.id.amount);
            image = itemView.findViewById(R.id.image);
        }

        public void bind(final Flight item, final OnItemClickListener listener) {
            Context context = itemView.getContext();
            fromTime.setText(item.getFromTime());
            toTime.setText(item.getToTime());
            amount.setText("NRP "+item.getAmount()+"/-");
            if (item.getImage()!=null)
                Picasso.get().load(context.getResources().getIdentifier(item.getImage(), "drawable", context.getPackageName())).into(image);
//            else
//                Picasso.get().load(context.getResources().getIdentifier("avatar", "drawable", context.getPackageName())).into(image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
