package com.caitlynwiley.openweathermapapi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.caitlynwiley.openweathermapapi.R;
import com.caitlynwiley.openweathermapapi.model.HourlyData;

import java.util.List;

public class HoursAdapter extends RecyclerView.Adapter<HoursAdapter.HoursViewHolder> {

    private List<HourlyData> hours;
    private int colLayout;
    private Context context;

    public HoursAdapter(List<HourlyData> hours, int colLayout, Context context) {
        this.setHours(hours);
        this.setColLayout(colLayout);
        this.setContext(context);
    }

    public List<HourlyData> getHours() {
        return hours;
    }

    public void setHours(List<HourlyData> hours) {
        this.hours = hours;
    }

    public int getColLayout() {
        return colLayout;
    }

    public void setColLayout(int colLayout) {
        this.colLayout = colLayout;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public HoursViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout and return a new view holder
        View view = LayoutInflater.from(parent.getContext())
                .inflate(colLayout, parent, false);
        return new HoursViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoursViewHolder holder, int position) {
        //binds view holder to data at position, update fields
        holder.time.setText(hours.get(position).getTime());
        holder.icon.setImageBitmap(hours.get(position).getImage());
        holder.temp.setText(hours.get(position).getTemp() + getContext().getString(R.string.DEGREES_F)); //need to append degrees F here too
    }

    @Override
    public int getItemCount() {
        return hours.size();
    }

    public static class HoursViewHolder extends RecyclerView.ViewHolder {

        // define views
        TextView time;
        ImageView icon;
        TextView temp;

        public HoursViewHolder(View v) {
            super(v);
            // set views
            time = v.findViewById(R.id.timeTextView);
            icon = v.findViewById(R.id.weatherIcon);
            temp = v.findViewById(R.id.tempTextView);
        }
    }
}
