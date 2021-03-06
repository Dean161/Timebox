package com.aktilog.timebox;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.icu.text.Replaceable;

import java.util.List;

@Dao
public interface CatDao {
    //queries for add/mod category
    @Query("SELECT * FROM Category")
    LiveData<List<Category>> getAll();

    @Query("SELECT * FROM Category")
    List<Category> getAllCat();

    @Query("SELECT * FROM Category WHERE cat_name LIKE :catName")
    Category findByName(String catName);

    @Query("SELECT COUNT(*) FROM Category")
    int CountCats();

    @Insert
    void insertAll(Category category);

    @Query("SELECT COUNT(*) FROM LoggedActivities")
    int CountActs();

    @Query("UPDATE Category SET cat_name =:newName, cat_color_hex =:newColor, parent_cat_id =:newParentID WHERE cat_name = :oldName")
    void update(String newName, String newColor, int newParentID, String oldName);

    //maybe more required?

    @Query("SELECT cat_name FROM Category")
    List<String> getCatNames();

    @Query("SELECT cat_name FROM Category WHERE parent_cat_id = 0")
    List<String> getAllParentCat();

    @Query("SELECT cat_color_hex FROM Category WHERE cat_name LIKE :givenCat")
    String getCatColor(String givenCat);

    @Query("SELECT cat_name FROM Category WHERE cid = (SELECT parent_cat_id FROM Category WHERE cat_name = :givenCat)")
    String getParentCatName(String givenCat);

    @Delete
    void delete(Category category);

    //queries for loggedActivities
    @Query("SELECT cid FROM Category WHERE cat_name LIKE :givenCat")
    int getCidActivities(String givenCat);

    //insert new Activity
    @Insert
    void insertActivity(LoggedActivities newAct);

    @Query("SELECT * FROM LoggedActivities ORDER BY start_date_time DESC LIMIT 10")
    List<LoggedActivities> getRecentLoggedActivities();

    @Query("SELECT * FROM LoggedActivities")
    List<LoggedActivities> getAllLoggedActivites();

    @Query("SELECT * FROM LoggedActivities WHERE start_date_time >= :startDateTime AND end_date_time <= :endDateTime")
    List<LoggedActivities> getLoggedActivitiesWithDates(String startDateTime, String endDateTime);

    @Query("SELECT LA.* FROM LoggedActivities LA,Category C WHERE  C.cat_name IN (:categoryName) AND LA.cid_fk = C.cid")
    List<LoggedActivities> getLoggedActivitiesWithCategories(String[] categoryName);

    @Query("SELECT LA.* FROM LoggedActivities LA,Category C WHERE  C.cat_name IN (:categoryName) AND start_date_time >= :startDateTime AND end_date_time <= :endDateTime AND LA.cid_fk = C.cid")
    List<LoggedActivities> getLoggedActivitiesWithAllFilters(String[] categoryName, String startDateTime, String endDateTime);

    //insert scheduled activity
    @Insert
    void insertScheduledActivity(ScheduledActivities newAct);

    //get data from scheduledActivities table
    @Query("SELECT * FROM ScheduledActivities WHERE logged_hours < target_duration_in_min;")
    List<ScheduledActivities> getScheduledActivities();

    @Query("SELECT * FROM LoggedActivities WHERE :selectedDate >= Date(start_date_time) AND :selectedDate<= Date(end_date_time);")
    List<LoggedActivities> getLoggedActivitiesCalendar(String selectedDate);

    @Query("SELECT * FROM ScheduledActivities WHERE activity_name IN (:activityname);")
    List<ScheduledActivities> getChosenScheduled(String activityname);

    @Update
    void updateLoggedHours(ScheduledActivities updatedScheduledActivity);

    @Update
    void updateLoggedActivity(LoggedActivities loggedActivities);

    @Query("SELECT * FROM LoggedActivities WHERE start_date_time >= :startDateTime AND end_date_time <= :endDateTime ORDER BY cid_fk ASC")
    List<LoggedActivities> getLoggedActivitiesWithDatesOrderCat(String startDateTime, String endDateTime);

    @Query("SELECT LA.* FROM LoggedActivities LA,Category C WHERE  C.cat_name IN (:categoryName) AND LA.cid_fk = C.cid ORDER BY cid_fk ASC")
    List<LoggedActivities> getLoggedActivitiesWithCategoriesOrderCat(String[] categoryName);

    @Query("SELECT LA.* FROM LoggedActivities LA,Category C WHERE  C.cat_name IN (:categoryName) AND start_date_time >= :startDateTime AND end_date_time <= :endDateTime AND LA.cid_fk = C.cid ORDER BY cid_fk ASC")
    List<LoggedActivities> getLoggedActivitiesWithAllFiltersOrderCat(String[] categoryName, String startDateTime, String endDateTime);


}
