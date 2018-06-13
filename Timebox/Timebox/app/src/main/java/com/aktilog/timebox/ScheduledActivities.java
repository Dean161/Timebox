package com.aktilog.timebox;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "ScheduledActivites", foreignKeys = @ForeignKey(entity = Category.class, parentColumns = "cid", childColumns = "cid_fk"))
public class ScheduledActivities {
    @PrimaryKey(autoGenerate = true)
    private int saID;

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

    @ColumnInfo(name = "target_duration")
    private String targetDuration;

    //TODO add restriction for max. number of digits
    @ColumnInfo(name = "notes")
    private String notes;
}
