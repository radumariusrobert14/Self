package com.example.application.self.ui;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.self.R;
import com.example.application.self.model.Journal;

import java.util.ArrayList;

public class JournalRecyclerAdapter extends RecyclerView.Adapter<JournalRecyclerAdapter.JournalViewHolder> {

    private Context context;
    private ArrayList<Journal> journalList;

    public JournalRecyclerAdapter(Context context, ArrayList<Journal> journalList) {
        this.journalList = journalList;
        this.context = context;
    }

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.journal_row, parent, false);
        return new JournalViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return journalList.size();
    }

    class JournalViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTV;
        private TextView descriptionTV;
        private TextView timestampTV;

        public JournalViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            titleTV = itemView.findViewById(R.id.journal_title_list);
            descriptionTV = itemView.findViewById(R.id.journal_description_list);
            timestampTV = itemView.findViewById(R.id.journal_timestamp_list);
        }

        public void bind(int position) {
            Journal journal = journalList.get(position);
            titleTV.setText(journal.getTitle());
            descriptionTV.setText(journal.getDescription());
            timestampTV.setText
                    (DateUtils.getRelativeTimeSpanString(journal.getTimeAdded().getSeconds() * 1000));
        }

    }

}
