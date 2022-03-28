package com.example.locationnotesapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationnotesapp.NoteScreen;
import com.example.locationnotesapp.NotesClickListener;
import com.example.locationnotesapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesListAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    Context context;
    List<NoteScreen>list;
    NotesClickListener listener;

    public NotesListAdapter(Context context, List<NoteScreen> list, NotesClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_notes,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.textView_title.setText(list.get(position).getTitle());
        holder.textView_title.setSelected(true);

        holder.textView_note.setText(list.get(position).getNotes());

        holder.textView_date.setText(list.get(position).getDate());
        holder.textView_date.setSelected(true);

        int colorCode= getRandomColor();
        holder.notesContainer.setCardBackgroundColor(holder.itemView.getResources().getColor(colorCode,null));

        holder.notesContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });

        holder.notesContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(list.get(holder.getAdapterPosition()), holder.notesContainer);

                return true;
            }
        });


    }
    private int getRandomColor(){
        List <Integer> colorCode = new ArrayList<>();

        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color5);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class NotesViewHolder extends RecyclerView.ViewHolder {

    CardView notesContainer;
    TextView textView_title,textView_note,textView_date;
    ImageView imagePin;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        findByView();
    }


    private void findByView(){

        notesContainer= itemView.findViewById(R.id.notesContainer);
        textView_title= itemView.findViewById(R.id.textView_title);
        textView_note= itemView.findViewById(R.id.textView_note);
        textView_date= itemView.findViewById(R.id.textView_date);

    }
}