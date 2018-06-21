package com.aktilog.timebox;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapterReview  extends BaseAdapter {

    private List<LoggedActivities> list_data_review;
    private LayoutInflater layout_inflater_review;
    private Context context;
    private String header = "##HEADER##";

    public CustomAdapterReview(Context aContext,  List<LoggedActivities> listData) {
        this.context = aContext;
        layout_inflater_review = LayoutInflater.from(aContext);
        this.list_data_review = listData;

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

        LoggedActivities loggedactivity = this.list_data_review.get(position);

        holder.cat_color.setBackgroundColor(ContextCompat.getColor(context,R.color.colorDarkGrey));
        holder.activity_name.setText(loggedactivity.getActivityName());
        holder.duration.setText(loggedactivity.getEndTime());

        return convertView;
    }

    static class ViewHolder {
        TextView cat_color;
        TextView activity_name;
        TextView duration;
    }
}
