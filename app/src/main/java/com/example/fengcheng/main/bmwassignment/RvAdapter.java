package com.example.fengcheng.main.bmwassignment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * data list adapter to display address data
 */

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.mViewHolder> {
    Context context;
    List<LocationBean> locationList;

    public RvAdapter(Context context, List<LocationBean> locationList) {
        this.context = context;
        this.locationList = locationList;
    }

    @Override
    public RvAdapter.mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_recyclerview,parent,false);
        return new mViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RvAdapter.mViewHolder holder, int position) {
        holder.idTv.setText(locationList.get(position).getId());
        holder.nameTV.setText(locationList.get(position).getName());
        holder.latitudeTv.setText(locationList.get(position).getLatitude());
        holder.longtitudeTv.setText(locationList.get(position).getLongitude());
        holder.addressTv.setText(locationList.get(position).getAddress());
        holder.arriveTv.setText(locationList.get(position).getArrivalTime());
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    class mViewHolder extends RecyclerView.ViewHolder{
        TextView idTv, nameTV, addressTv, arriveTv, latitudeTv, longtitudeTv;

        public mViewHolder(View itemView) {
            super(itemView);
            idTv = itemView.findViewById(R.id.id);
            nameTV = itemView.findViewById(R.id.name);
            addressTv = itemView.findViewById(R.id.address);
            arriveTv = itemView.findViewById(R.id.arrive_time);
            latitudeTv = itemView.findViewById(R.id.latitude);
            longtitudeTv = itemView.findViewById(R.id.longitude);

            //OPEN GOOGLE MAP here
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MapsActivity.class);
                    intent.putExtra("lati", latitudeTv.getText().toString());
                    intent.putExtra("longti", longtitudeTv.getText().toString());
                    intent.putExtra("id", idTv.getText().toString());
                    intent.putExtra("name", nameTV.getText().toString());
                    intent.putExtra("address", addressTv.getText().toString());
                    intent.putExtra("arrivetime", arriveTv.getText().toString());
                    context.startActivity(intent);
                }
            });

        }
    }


}
