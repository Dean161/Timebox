package com.aktilog.timebox;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapterCheckScheduled extends BaseAdapter {
    private List<ScheduledActivities> list_scheduled_activities;
    private LayoutInflater layoutInflater_checkScheduled;
    private Context context;
    private String header = "##HEADER##";

    public CustomAdapterCheckScheduled(Context aContext,  List<ScheduledActivities> listData) {
        this.context = aContext;
        layoutInflater_checkScheduled = LayoutInflater.from(aContext);

        this.list_scheduled_activities = listData;

        String headDate="";
        for (int i=0;i<listData.size();i++) {
            ScheduledActivities iter = listData.get(i);
            if (!iter.getStartDateTime().split(" ")[0].equals(headDate)) {
                headDate = iter.getStartDateTime().split(" ")[0];
                if (!iter.getActivityName().contains(header)) {
                    ScheduledActivities newLog = new ScheduledActivities();
                    newLog.setActivityName(header);
                    newLog.setStartDateTime(headDate+" 00:00");
                    this.list_scheduled_activities.add(i,newLog);
                }
            }

        }
    }

    @Override
    public int getCount() {
        return list_scheduled_activities.size();
    }

    @Override
    public Object getItem(int position) {
        return list_scheduled_activities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        CustomAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater_checkScheduled.inflate(R.layout.list_item, null);
            holder = new CustomAdapter.ViewHolder();
            holder.dateView = (TextView) convertView.findViewById(R.id.start_date);
            holder.monthView = (TextView) convertView.findViewById(R.id.start_month);
            holder.catView = (TextView) convertView.findViewById(R.id.activity_color);
            holder.nameView = (TextView) convertView.findViewById(R.id.activity_note);
            holder.startView = (TextView) convertView.findViewById(R.id.activity_start);
            holder.endView = (TextView) convertView.findViewById(R.id.activity_end);
            holder.overView = (TextView) convertView.findViewById(R.id.start_line);

            convertView.setTag(holder);
        } else {
            holder = (CustomAdapter.ViewHolder) convertView.getTag();
        }

        ScheduledActivities scheduledActivity = this.list_scheduled_activities.get(position);

        if (scheduledActivity.getActivityName().contains(header)) {
            holder.dateView.setVisibility(View.VISIBLE);
            holder.monthView.setVisibility(View.VISIBLE);
            holder.overView.setVisibility(View.VISIBLE);

            holder.nameView.setVisibility(View.GONE);
            holder.startView.setVisibility(View.GONE);
            holder.endView.setVisibility(View.GONE);
            holder.catView.setVisibility(View.INVISIBLE);
        }
        else {
            holder.dateView.setVisibility(View.GONE);
            holder.monthView.setVisibility(View.GONE);
            holder.overView.setVisibility(View.GONE);

            holder.nameView.setVisibility(View.VISIBLE);
            holder.startView.setVisibility(View.VISIBLE);
            holder.endView.setVisibility(View.VISIBLE);
            holder.catView.setVisibility(View.VISIBLE);
        }
        holder.dateView.setText(scheduledActivity.getStartDateTime().split(" ")[0].split("-")[2]);
        holder.monthView.setText(scheduledActivity.getStartDateTime().split(" ")[0].split("-")[1]);
        //holder.catView.setBackgroundColor(R.color.colorGrey);
        holder.nameView.setText(scheduledActivity.getActivityName());
        holder.startView.setText(scheduledActivity.getStartDateTime().split(" ")[1]);
        if (scheduledActivity.getEndDateTime() != null) {
            holder.endView.setText(scheduledActivity.getEndDateTime().split(" ")[1]);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView dateView;
        TextView monthView;
        TextView catView;
        TextView nameView;
        TextView startView;
        TextView endView;
        TextView overView;
    }
}
