package com.aktilog.timebox;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CatDao {
    @Query("SELECT * FROM categories")
    List<Category> getAll();

    @Query("SELECT * FROM categories WHERE cat_name LIKE :catName")
    Category findByName(String catName);

    @Query("SELECT COUNT(*) FROM categories")
    int CountCats();

    //what should that do?
    @Insert
    void insertAll(Category... categories);

    @Query("UPDATE categories SET cat_name =:newName AND cat_color_hex =:newColor WHERE cat_name LIKE :oldName")
    void update(String newName, String newColor, String oldName);

    //maybe more required?

    @Query("SELECT cat_name FROM categories")
    List<String> getCatNames();

    @Delete
    void delete(Category category);
}
