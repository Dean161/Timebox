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

    @Query("UPDATE Category SET cat_name =:newName, cat_color_hex =:newColor WHERE cat_name = :oldName")
    void update(String newName, String newColor, String oldName);

    //maybe more required?

    @Query("SELECT cat_name FROM Category")
    List<String> getCatNames();

    @Query("SELECT cat_color_hex FROM Category WHERE cat_name LIKE :givenCat")
    String getCatColor(String givenCat);

    @Delete
    void delete(Category category);

    //queries for loggedActivites
    @Query("SELECT cid FROM Category WHERE cat_name LIKE :givenCat")
    int getCidActivites(String givenCat);

    //insert new Activity
    @Insert
    void insertActivity(LoggedActivities newAct);

    @Query("SELECT * FROM LoggedActivities WHERE start_date_time >= :startDateTime AND end_date_time <= :endDateTime")
    List<LoggedActivities> getLoggedActivitiesWithDates(String startDateTime, String endDateTime);

    @Query("SELECT LA.* FROM LoggedActivities LA,Category C WHERE  C.cat_name IN (:categoryName) AND LA.cid_fk = C.cid")
    List<LoggedActivities> getLoggedActivitiesWithCategories(String[] categoryName);

    @Query("SELECT LA.* FROM LoggedActivities LA,Category C WHERE  C.cat_name IN (:categoryName) AND start_date_time >= :startDateTime AND end_date_time <= :endDateTime AND LA.cid_fk = C.cid")
    List<LoggedActivities> getLoggedActivitiesWithAllFilters(String[] categoryName, String startDateTime, String endDateTime);

    //insert scheduled activity
    @Insert
    void insertScheduledActivity(ScheduledActivities newAct);

    //get data from scheduledActivites table
    @Query("SELECT * FROM ScheduledActivities;")
    List<ScheduledActivities> getScheduledActivities();

    @Query("SELECT * FROM LoggedActivities WHERE :selectedDate >= Date(start_date_time) AND :selectedDate<= Date(end_date_time);")
    List<LoggedActivities> getLoggedActiviesCalendar(String selectedDate);
}
