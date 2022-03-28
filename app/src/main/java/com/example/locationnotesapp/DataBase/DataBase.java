package com.example.locationnotesapp.DataBase;

import static androidx.room.OnConflictStrategy.REPLACE;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.locationnotesapp.NoteScreen;

import java.util.List;

@Dao
public interface DataBase {
    @Insert(onConflict = REPLACE)
    void insert(NoteScreen notes);

    @Query("SELECT*FROM notes ORDER BY date DESC")
    List<NoteScreen> getAll();

    @Query("UPDATE notes SET title= :title, notes= :notes WHERE ID= :id")
    void update (int id, String title,String notes);

    @Delete
    void delete(NoteScreen notes);

    

}
