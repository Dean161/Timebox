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
        CustomAdapterCheckScheduled.ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater_checkScheduled.inflate(R.layout.list_scheduled_text, null);
            holder = new CustomAdapterCheckScheduled.ViewHolder();

            holder.startDateAndTime = convertView.findViewById(R.id.text_list_startDateAndTime);
            holder.endDateAndTime = convertView.findViewById(R.id.text_list_endDateAndTime);
            holder.targetDuration = convertView.findViewById(R.id.text_list_schedule_duration);
            holder.activityName = convertView.findViewById(R.id.text_list_schedule_activity_name);
            holder.categoryColor = convertView.findViewById(R.id.text_list_schedule_category_color);


            convertView.setTag(holder);
        } else {
            holder = (CustomAdapterCheckScheduled.ViewHolder) convertView.getTag();
        }

        ScheduledActivities scheduledActivity = this.list_scheduled_activities.get(position);

        //holder.startDateAndTime.setText(scheduledActivity.getStartDateTime().split(" ")[0].split("-")[2]);
        //holder.monthView.setText(scheduledActivity.getStartDateTime().split(" ")[0].split("-")[1]);
        //holder.catView.setBackgroundColor(R.color.colorGrey);
        //color here
        holder.categoryColor.setBackgroundColor(scheduledActivity.get);
        holder.targetDuration.setText(scheduledActivity.getTargetDuration());
        holder.activityName.setText(scheduledActivity.getActivityName());
        holder.startDateAndTime.setText(scheduledActivity.getStartDateTime().split(" ")[1]);
        if (scheduledActivity.getEndDateTime() != null) {
            holder.endDateAndTime.setText(scheduledActivity.getEndDateTime().split(" ")[1]);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView startDateAndTime;
        TextView endDateAndTime;
        TextView targetDuration;
        TextView activityName;
        TextView categoryColor;
    }
}
