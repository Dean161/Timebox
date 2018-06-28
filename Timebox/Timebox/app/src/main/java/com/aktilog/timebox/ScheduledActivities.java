package com.aktilog.timebox;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "ScheduledActivities", foreignKeys = @ForeignKey(entity = Category.class, parentColumns = "cid", childColumns = "cid_fk"))
public class ScheduledActivities {
    @PrimaryKey(autoGenerate = true)
    private int saID;

    @ColumnInfo(name = "cid_fk")
    private int cid_fk;

    @ColumnInfo(name = "activity_name")
    private String activityName;

    @ColumnInfo(name = "start_date_time")
    private String startDateTime;

    @ColumnInfo(name = "end_date_time")
    private String endDateTime;

    @ColumnInfo(name = "target_duration_in_min")
    private int targetDurationInMin;

    //TODO add restriction for max. number of digits
    @ColumnInfo(name = "notes")
    private String notes;

    @ColumnInfo(name = "logged_hours")
    private int loggedHours;

    public void setSaID(int saID) {
        this.saID = saID;
    }

    public int getSaID() {return saID;}

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

    public void setLoggedHours(int loggedHours) {
        this.loggedHours = loggedHours;
    }

    public int getLoggedHours() {
        return loggedHours;
    }

    public void setTargetDurationInMin(int targetDurationInMin) {
        this.targetDurationInMin = targetDurationInMin;
    }

    public int getTargetDurationInMin() {
        return targetDurationInMin;
    }
}
