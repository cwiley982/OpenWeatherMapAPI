package com.caitlynwiley.openweathermapapi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.caitlynwiley.openweathermapapi.R;
import com.caitlynwiley.openweathermapapi.api.model.HourlyData;

import java.util.List;

public class HoursAdapter extends ArrayAdapter<HourlyData> {

    private List<HourlyData> hours;
    private Context context;

    public HoursAdapter(@NonNull Context context, int resource, @NonNull List<HourlyData> objects) {
        super(context, resource, objects);
        setHours(objects);
        //TODO: don't know what resource is
    }

    public List<HourlyData> getHours() {
        return hours;
    }

    public void setHours(List<HourlyData> hours) {
        this.hours = hours;
    }

    public Context getContext() {
        return super.getContext();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_list_item, parent, false);
        return new HoursViewHolder(view);
        //TODO
    }

    public static class HoursViewHolder extends ListView.ViewHolder {

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
