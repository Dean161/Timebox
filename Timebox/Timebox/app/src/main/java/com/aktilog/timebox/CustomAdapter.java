package com.aktilog.timebox;

import android.content.Context;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class CustomAdapter  extends BaseAdapter {

    private List<LoggedActivities> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    private String cat_color_review_text;
    private List<Category> category_list_review;
    private int cat_id;
    private String header = "##HEADER##";

    public CustomAdapter(Context aContext,  List<LoggedActivities> listData, List<Category> categoryData) {
        this.context = aContext;
        layoutInflater = LayoutInflater.from(aContext);

        this.listData = listData;
        this.category_list_review = categoryData;

        String headDate="";
        for (int i=0;i<listData.size();i++) {
            LoggedActivities iter = listData.get(i);
            if (!iter.getStartDateTime().split(" ")[0].equals(headDate)) {
                headDate = iter.getStartDateTime().split(" ")[0];
                if (!iter.getActivityName().contains(header)) {
                    LoggedActivities newLog = new LoggedActivities();
                    newLog.setActivityName(header);
                    newLog.setCid_fk(1);
                    newLog.setStartDateTime(headDate+" 00:00");
                    newLog.setEndDateTime(headDate+" 00:00");
                    this.listData.add(i,newLog);
                }
            }

        }
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.dateView = (TextView) convertView.findViewById(R.id.start_date);
            holder.monthView = (TextView) convertView.findViewById(R.id.start_month);
            holder.catView = (TextView) convertView.findViewById(R.id.activity_color);
            holder.nameView = (TextView) convertView.findViewById(R.id.activity_note);
            holder.startView = (TextView) convertView.findViewById(R.id.activity_start);
            holder.endView = (TextView) convertView.findViewById(R.id.activity_end);
            holder.overView = (TextView) convertView.findViewById(R.id.start_line);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        LoggedActivities loggedactivity = this.listData.get(position);
        cat_id = loggedactivity.getCid_fk();
        cat_color_review_text = getColorByID();

        if (loggedactivity.getActivityName().contains(header)) {
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
        holder.dateView.setText(loggedactivity.getStartDateTime().split(" ")[0].split("-")[2]);
        holder.monthView.setText(loggedactivity.getStartDateTime().split(" ")[0].split("-")[1]);
        holder.catView.setBackgroundColor(Integer.parseInt(cat_color_review_text));
        holder.nameView.setText(loggedactivity.getActivityName());
        holder.startView.setText(loggedactivity.getStartDateTime().split(" ")[1]);
        holder.endView.setText(loggedactivity.getEndDateTime().split(" ")[1]);
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

    private String getColorByID(){
        for(int i=0;i<category_list_review.size();i++){
            if (category_list_review.get(i).getCid() == cat_id){
                return category_list_review.get(i).getHexCode();
            }
        }
        return "N/A";
    }
}