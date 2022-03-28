package com.example.locationnotesapp;

import androidx.cardview.widget.CardView;

public interface NotesClickListener {
    void onClick(NoteScreen notes);
    void onLongClick(NoteScreen notes, CardView cardView);
}
