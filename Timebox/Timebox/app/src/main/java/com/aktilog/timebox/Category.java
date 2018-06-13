package com.aktilog.timebox;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Categories")
public class Category {
    @PrimaryKey(autoGenerate=true)
    private int cid;

    @ColumnInfo(name="cat_name")
    private String catName;

    @ColumnInfo(name = "cat_color_hex")
    private String hexCode;

    //public Category(@NonNull String catName, @NonNull String hexCode) {
        //this.catName = catName;
        //this.hexCode = hexCode;
    //}

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getHexCode() {
        return hexCode;
    }

    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }
}
