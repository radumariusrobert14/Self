package com.example.application.self;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.application.self.model.Journal;
import com.example.application.self.ui.JournalRecyclerAdapter;
import com.example.application.self.util.JournalApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;

public class JournalListActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;

    private ArrayList<Journal> journalList;
    private RecyclerView recyclerView;
    private JournalRecyclerAdapter adapter;

    private CollectionReference collectionReference = db.collection("Journal");
    private TextView noJournalEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_list);

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        noJournalEntry = findViewById(R.id.no_journal_text_view);

        journalList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedMenuItem = item.getItemId();
        switch (selectedMenuItem) {
            case R.id.action_add:
                // take users to add journal
                if (user!=null && firebaseAuth!=null) {
                    startActivity(new Intent(JournalListActivity.this,
                            PostJournalActivity.class));
                    finish();
                }
                return true;
            case R.id.action_sign_out:
                // sign out
                if (user!=null && firebaseAuth!=null) {
                    firebaseAuth.signOut();
                    startActivity(new Intent(JournalListActivity.this,
                            MainActivity.class));
                    finish();
                }
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onStart() {
        // get all the journals from Firestore in the arrayList
        super.onStart();
        collectionReference.whereEqualTo("userId", JournalApi.getInstance().getUserId())
            .get()
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot journals: queryDocumentSnapshots) {
                            Journal journal = journals.toObject(Journal.class);
                            journalList.add(journal);
                        }
                        // invoke recyclerview
                        adapter = new JournalRecyclerAdapter(JournalListActivity.this, journalList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        noJournalEntry.setVisibility(View.VISIBLE);
                    }
                }
            })
            .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
