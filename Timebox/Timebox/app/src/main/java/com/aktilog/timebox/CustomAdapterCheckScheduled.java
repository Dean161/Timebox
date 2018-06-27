package com.aktilog.timebox;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomAdapterCheckScheduled extends BaseAdapter {
    private List<ScheduledActivities> list_scheduled_activities;
    private LayoutInflater layoutInflater_checkScheduled;
    private Context context;
    private List<Category> list_category_data;
    private String header = "##HEADER##";
    private String cat_color_schedule_text;
    private int cat_id;

    public CustomAdapterCheckScheduled(Context aContext,  List<ScheduledActivities> listData, List<Category> category_list) {
        this.context = aContext;
        layoutInflater_checkScheduled = LayoutInflater.from(aContext);
        this.list_scheduled_activities = listData;
        this.list_category_data = category_list;
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
        cat_id = scheduledActivity.getCid_fk();
        cat_color_schedule_text = getColorByID();

        holder.categoryColor.setBackgroundColor(Integer.parseInt(cat_color_schedule_text));
        holder.activityName.setText(scheduledActivity.getActivityName());
        SimpleDateFormat start_date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat end_date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date start_date = new Date();
        Date end_date = new Date();
        try{
            start_date = start_date_format.parse(scheduledActivity.getStartDateTime());
            end_date = end_date_format.parse(scheduledActivity.getEndDateTime());
        } catch (ParseException e){
            e.printStackTrace();
        }
        long duration = ((end_date.getTime() - start_date.getTime())/3600000);
        //holder.targetDuration.setText(String.valueOf(duration));

        holder.targetDuration.setText(scheduledActivity.getTargetDuration());
        //holder.activityName.setText(scheduledActivity.getActivityName());
        holder.startDateAndTime.setText(scheduledActivity.getStartDateTime());
        if (scheduledActivity.getEndDateTime() != null) {
            holder.endDateAndTime.setText(scheduledActivity.getEndDateTime());
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

    private String getColorByID(){
        for(int i=0;i<list_category_data.size();i++){
            if (list_category_data.get(i).getCid() == cat_id){
                return list_category_data.get(i).getHexCode();
            }
        }
        return "N/A";
    }
}
