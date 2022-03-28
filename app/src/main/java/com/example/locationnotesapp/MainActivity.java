package com.example.locationnotesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locationnotesapp.Adapters.NotesListAdapter;
import com.example.locationnotesapp.DataBase.RoomDB;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import com.example.locationnotesapp.Activities.LoginScreen;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements PopupMenu.OnMenuItemClickListener{
    RecyclerView recyclerView;
    NotesListAdapter notesListAdapter;
    List <NoteScreen> notes= new ArrayList<>();
    RoomDB database;
    FloatingActionButton addNote;
    NoteScreen selectedNote;
    Button mLogout;
    TextView notHaveNoteYet;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);

        recyclerView = findViewById(R.id.recycleView);
        addNote = findViewById(R.id.addNote);
        mLogout = findViewById(R.id.main_Logout);


        database = RoomDB.getInstance(this);
        notes = database.database().getAll();

        updateRecycler(notes);


        if (notes == null) {
            notHaveNoteYet = (TextView) findViewById(R.id.notHaveNote);
            notHaveNoteYet.setVisibility(View.VISIBLE);

        } else {

            mLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logout(view);
                }
            });

            addNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openNoteApp();
                }
            });

        }
    }

        private void openNoteApp () {

            Intent intent = new Intent(MainActivity.this, NotesTakerActivity.class);

            startActivityForResult(intent, 101);
        }


        @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 101) {
                if (resultCode == Activity.RESULT_OK) {
                    NoteScreen new_notes = (NoteScreen) data.getSerializableExtra("note");
                    database.database().insert(new_notes);
                    notes.clear();
                    notes.addAll(database.database().getAll());
                    notesListAdapter.notifyDataSetChanged();
                }
            } else if (requestCode == 102) {
                if (resultCode == Activity.RESULT_OK) {
                    NoteScreen newNotes = (NoteScreen) data.getSerializableExtra("note");
                    database.database().update(newNotes.getID(), newNotes.getTitle(), newNotes.getNotes());
                    notes.clear();
                    notes.addAll(database.database().getAll());
                    notesListAdapter.notifyDataSetChanged();
                }

            }
        }

        private void updateRecycler (List < NoteScreen > notes) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
            notesListAdapter = new NotesListAdapter(MainActivity.this, notes, notesClickListener);
            recyclerView.setAdapter(notesListAdapter);
        }

        private final NotesClickListener notesClickListener = new NotesClickListener() {
            @Override
            public void onClick(NoteScreen notes) {
                Intent intent = new Intent(MainActivity.this, NotesTakerActivity.class);
                intent.putExtra("old_note", notes);
                startActivityForResult(intent,102);


            }

            @Override
            public void onLongClick(NoteScreen notes, CardView cardView) {
                selectedNote = new NoteScreen();
                selectedNote = notes;
                showPopup(cardView);

            }
        };

        private void showPopup (CardView cardView){
            PopupMenu popupMenu = new PopupMenu(this, cardView);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.show();

        }

        public void logout (View view){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginScreen.class));
            finish();
        }


        @Override
        public boolean onMenuItemClick (MenuItem item){
            database.database().delete(selectedNote);
            notes.remove(selectedNote);
            notesListAdapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Note Deleted!", Toast.LENGTH_SHORT).show();
            return true;
        }



}