package com.caitlynwiley.openweathermapapi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caitlynwiley.openweathermapapi.R;
import com.caitlynwiley.openweathermapapi.api.model.DailyData;
import com.caitlynwiley.openweathermapapi.api.model.HourlyData;

import java.util.ArrayList;
import java.util.List;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.DaysViewHolder> {

    private List<DailyData> days;
    private int rowLayout;
    private Context context;

    public DaysAdapter(List<DailyData> days, int rowLayout, Context context) {
        this.setDays(days);
        this.setRowLayout(rowLayout);
        this.setContext(context);
    }

    public List<DailyData> getDays() {
        return days;
    }

    public void setDays(List<DailyData> days) {
        this.days = days;
    }

    public int getRowLayout() {
        return rowLayout;
    }

    public void setRowLayout(int rowLayout) {
        this.rowLayout = rowLayout;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DaysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the layout and return a view holder
        View view = LayoutInflater.from(parent.getContext())
                .inflate(rowLayout, parent, false);
        return new DaysViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DaysViewHolder holder, int position) {
        //binds the view holder to data at position, update fields
        DailyData day = days.get(position);
        holder.date.setText(day.getDate());
        holder.hiLoTemp.setText(day.getHigh() + "/" + day.getLow());
        holder.setHourlyList(day.getHourlyDataList());
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    // defines the view holder to use in the recycler view
    class DaysViewHolder extends RecyclerView.ViewHolder {

        //define views
        TextView date;
        TextView hiLoTemp;
        RecyclerView.Adapter myHourlyAdapter;
        List<HourlyData> myHourlyDataSource = new ArrayList<>();
        RecyclerView mHourlyRecyclerView;

        public DaysViewHolder(View v) {
            super(v);
            //set fields
            date = v.findViewById(R.id.dateTextView);
            hiLoTemp = v.findViewById(R.id.highLowTextView);

            // Set up hourly Recycler View
            // maybe have days (List<DailyData>) hold a List<HourlyData>, can access that way?
            mHourlyRecyclerView = v.findViewById(R.id.hourly_recycler_view);
            mHourlyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            myHourlyAdapter = new HoursAdapter(myHourlyDataSource, R.layout.hourly_list_item, getContext());
            mHourlyRecyclerView.setAdapter(myHourlyAdapter);
        }

        public void setHourlyList(List<HourlyData> data) {
            myHourlyDataSource = data;
            myHourlyAdapter.notifyDataSetChanged();
        }
    }
}
