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
    @Query("SELECT * FROM categories")
    LiveData<List<Category>> getAll();

    @Query("SELECT * FROM categories WHERE cat_name LIKE :catName")
    Category findByName(String catName);

    @Query("SELECT COUNT(*) FROM categories")
    int CountCats();

    @Insert
    void insertAll(Category category);

    @Query("UPDATE categories SET cat_name =:newName AND cat_color_hex =:newColor WHERE cat_name LIKE :oldName")
    void update(String newName, String newColor, String oldName);

    //maybe more required?

    @Query("SELECT cat_name FROM categories")
    List<String> getCatNames();

    @Delete
    void delete(Category category);
}
