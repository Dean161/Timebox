package com.aktilog.timebox;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "LoggedActivities", foreignKeys = @ForeignKey(entity = Category.class, parentColumns = "cid", childColumns = "cid_fk"))
public class LoggedActivities{
    @PrimaryKey(autoGenerate = true)
    private int laID;

    @ColumnInfo(name = "cid_fk")
    private int cid_fk;

    @ColumnInfo(name = "activity_name")
    private String activityName;

    @ColumnInfo(name = "start_date_time")
    private String startDateTime;

    @ColumnInfo(name = "end_date_time")
    private String endDateTime;

    //TODO add restriction for max. number of digits
    @ColumnInfo(name = "notes")
    private String notes;

    public void setLaID(int laID) {
        this.laID = laID;
    }

    public int getLaID() {
        return laID;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setCid_fk(int cid_fk) {
        this.cid_fk = cid_fk;
    }

    public int getCid_fk() {
        return cid_fk;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }
}
