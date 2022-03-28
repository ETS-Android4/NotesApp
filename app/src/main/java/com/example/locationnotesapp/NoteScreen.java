package com.example.locationnotesapp;

import android.widget.Button;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notes")
public class NoteScreen implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int ID=0;

    @ColumnInfo(name="title")
    String title ="";

    @ColumnInfo(name="notes")
    String notes ="";

    @ColumnInfo(name="date")
    String date ="";


    @ColumnInfo(name="pinned")
    boolean pinned= false;


    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getNotes() {
        return notes;
    }

    public String getDate() {
        return date;
    }


    public boolean isPinned() {
        return pinned;
    }
}
