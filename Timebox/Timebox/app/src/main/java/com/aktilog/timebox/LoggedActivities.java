package com.aktilog.timebox;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "LoggedActivites", foreignKeys = @ForeignKey(entity = Category.class, parentColumns = "cid", childColumns = "cid_fk"))
public class LoggedActivities{
    @PrimaryKey(autoGenerate = true)
    private int laID;

    @ColumnInfo(name = "cid_fk")
    private int cid_fk;

    @ColumnInfo(name = "activity_name")
    private String activityName;

    @ColumnInfo(name = "start_date")
    private String startDate;

    @ColumnInfo(name = "end_date")
    private String endDate;

    @ColumnInfo(name = "start_time")
    private String startTime;

    @ColumnInfo(name = "end_time")
    private String endTime;

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

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }
}
