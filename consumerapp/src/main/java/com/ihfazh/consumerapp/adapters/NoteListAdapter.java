package com.ihfazh.consumerapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ihfazh.consumerapp.R;
import com.ihfazh.consumerapp.models.NoteModel;

import java.util.ArrayList;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    ArrayList<NoteModel> notes = new ArrayList<>();
    private NoteListClicked callback;

    public void setNotes(ArrayList<NoteModel> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public void addNote(NoteModel note){
        notes.add(note);
        notifyItemInserted(notes.size() + 1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final NoteModel note = notes.get(position);
        holder.tvTitle.setText(note.getTitle());
        holder.tvDescription.setText(note.getDescription());
        holder.tvDate.setText(note.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onClick(note.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public ArrayList<NoteModel> getNotes() {
        return notes;
    }

    public void setCallback(NoteListClicked callback) {
        this.callback = callback;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvTitle, tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDate);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }

    public interface NoteListClicked{
        void onClick(int id);
    }
}

