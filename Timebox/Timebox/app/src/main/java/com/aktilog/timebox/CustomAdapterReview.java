package com.aktilog.timebox;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomAdapterReview  extends BaseAdapter {

    private List<LoggedActivities> list_data_review;
    private LayoutInflater layout_inflater_review;
    private Context context;
    private LoggedActivities loggedActivity;
    private String cat_color_review_text;
    private List<Category> category_list_review;
    private int cat_id;
    //private LoggedActivities header_logged_activities;

    public CustomAdapterReview(Context aContext,  List<LoggedActivities> listData, List<Category> categoryData) {
        this.context = aContext;
        layout_inflater_review = LayoutInflater.from(aContext);
        this.list_data_review = listData;
        this.category_list_review = categoryData;

    }

    @Override
    public int getCount() {
        return list_data_review.size();
    }

    @Override
    public Object getItem(int position) {
        return list_data_review.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        CustomAdapterReview.ViewHolder holder;
        if (convertView == null) {
            convertView = layout_inflater_review.inflate(R.layout.list_review_text, null);
            holder = new CustomAdapterReview.ViewHolder();
            holder.cat_color = convertView.findViewById(R.id.text_list_review_category_color);
            holder.activity_name = convertView.findViewById(R.id.text_list_review_activity_name);
            holder.duration = convertView.findViewById(R.id.text_list_review_duration);

            convertView.setTag(holder);
        } else {
            holder = (CustomAdapterReview.ViewHolder) convertView.getTag();
        }
        loggedActivity = this.list_data_review.get(position);
        cat_id = loggedActivity.getCid_fk();
        cat_color_review_text = getColorByID();
        holder.cat_color.setBackgroundColor(Integer.parseInt(cat_color_review_text));
        holder.activity_name.setText(loggedActivity.getActivityName());
        SimpleDateFormat start_date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat end_date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date start_date = new Date();
        Date end_date = new Date();
        try{
            start_date = start_date_format.parse(loggedActivity.getStartDateTime());
            end_date = end_date_format.parse(loggedActivity.getEndDateTime());
        } catch (ParseException e){
            e.printStackTrace();
        }
        int t = (int) ((end_date.getTime() - start_date.getTime())/60000);
        int hours = t/60;
        int minutes = t%60;
        String duration = String.valueOf(hours) + ":" + String.valueOf(minutes);

        holder.duration.setText(duration);

        return convertView;
    }

    static class ViewHolder {
        TextView cat_color;
        TextView activity_name;
        TextView duration;
    }

    private String getColorByID(){
        for(int i=0;i<category_list_review.size();i++){
            if (category_list_review.get(i).getCid() == cat_id){
                return category_list_review.get(i).getHexCode();
            }
        }
        return "N/A";
    }
}
